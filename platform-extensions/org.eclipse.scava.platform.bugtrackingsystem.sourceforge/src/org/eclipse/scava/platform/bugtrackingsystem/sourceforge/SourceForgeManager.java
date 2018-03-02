/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.sourceforge;

import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.cache.Cache;
import org.eclipse.scava.platform.bugtrackingsystem.cache.Caches;
import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.DateRangeCacheProvider;
import org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api.SourceForgeComment;
import org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api.SourceForgeSearchResult;
import org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api.SourceForgeTicket;
import org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api.SourceForgeTrackerRestClient;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem;
import org.joda.time.DateTime;

import com.mongodb.DB;

public class SourceForgeManager implements
		IBugTrackingSystemManager<SourceForgeBugTrackingSystem> {

	private Caches<SourceForgeTicket, String> ticketCaches = new Caches<SourceForgeTicket, String>(
			new TicketCacheProvider());

	private static class TicketCacheProvider extends
			DateRangeCacheProvider<SourceForgeTicket, String> {

		@Override
		public Iterator<SourceForgeTicket> getItems(java.util.Date after,
				java.util.Date before, BugTrackingSystem bugTracker)
				throws Exception {
			SourceForgeBugTrackingSystem sfBugTracker = (SourceForgeBugTrackingSystem) bugTracker;
			SourceForgeTrackerRestClient sourceforge = getSourceForge(sfBugTracker);
			return sourceforge.getTickets(after, before);
		}

		@Override
		public String getKey(SourceForgeTicket item) {
			return item.getBugId();
		}

		@Override
		public boolean changedOnDate(SourceForgeTicket item,
				java.util.Date date, BugTrackingSystem bugTracker) {
			return findMatchOnDate(date, item.getCreationTime(),
					item.getUpdateDate());
		}

		@Override
		public boolean changedSinceDate(SourceForgeTicket item,
				java.util.Date date, BugTrackingSystem bugTracker) {
			return findMatchSinceDate(date, item.getCreationTime(),
					item.getUpdateDate());
		}

		@Override
		public void process(SourceForgeTicket item, BugTrackingSystem bugTracker) {
			item.setBugTrackingSystem(bugTracker); // Is this needed?
			item.setDescription(null); // remove content field
			for (BugTrackingSystemComment comment : item.getComments()) {
				comment.setBugTrackingSystem(bugTracker); // Is this needed?
				comment.setText(null); // remove content field
			}
		}
	}

	@Override
	public boolean appliesTo(BugTrackingSystem bugTrackingSystem) {
		return (bugTrackingSystem instanceof SourceForgeBugTrackingSystem);
	}

	@Override
	public BugTrackingSystemDelta getDelta(DB db, SourceForgeBugTrackingSystem bugTracker, Date date) throws Exception {

		java.util.Date day = date.toJavaDate();

		Cache<SourceForgeTicket, String> ticketCache = ticketCaches
				.getCache(bugTracker, true);
		Iterable<SourceForgeTicket> tickets = ticketCache
				.getItemsAfterDate(day);

		SourceForgeTrackingSystemDelta delta = new SourceForgeTrackingSystemDelta();
		for (SourceForgeTicket ticket : tickets) {

			if (DateUtils.isSameDay(ticket.getUpdateDate(), day)) {
				delta.getUpdatedBugs().add(ticket);
			} else if (DateUtils.isSameDay(ticket.getCreationTime(), day)) {
				delta.getNewBugs().add(ticket);
			}

			// Store updated comments in delta
			for (BugTrackingSystemComment comment : ticket.getComments()) {
				SourceForgeComment sfComment = (SourceForgeComment) comment;

				java.util.Date updated = sfComment.getUpdateDate();
				java.util.Date created = sfComment.getCreationTime();

				if (DateUtils.isSameDay(created, day)) {
					delta.getComments().add(comment);
				} else if (updated != null && DateUtils.isSameDay(updated, day)) {
					delta.getComments().add(comment);
				}
			}
		}

		return delta;
	}

	@Override
	public Date getFirstDate(DB db, SourceForgeBugTrackingSystem bugTracker) throws Exception {
		SourceForgeTrackerRestClient sourceforge = getSourceForge(bugTracker);
		SourceForgeSearchResult result = sourceforge.search("*:*", 0, 1);
		List<Integer> ids = result.getTicketIds();
		if (!ids.isEmpty()) {
			SourceForgeTicket ticket = sourceforge.getTicket(ids.get(0));
			return new Date(ticket.getCreationTime());
		}
		return null;
	}

	@Override
	public String getContents(DB db, SourceForgeBugTrackingSystem bugTracker, BugTrackingSystemBug bug) throws Exception {
		SourceForgeTrackerRestClient sourceforge = getSourceForge(bugTracker);
		int ticketId = Integer.parseInt(bug.getBugId());
		SourceForgeTicket ticket = sourceforge.getTicket(ticketId);
		if (null != ticket) {
			return ticket.getDescription();
		}
		return null;
	}

	@Override
	public String getContents(DB db, SourceForgeBugTrackingSystem bugTracker, BugTrackingSystemComment comment) throws Exception {
		SourceForgeTrackerRestClient sourceforge = getSourceForge(bugTracker);
		int ticketId = Integer.parseInt(comment.getBugId());
		SourceForgeComment sfComment = sourceforge.getComment(ticketId,
				comment.getCommentId());
		if (null != sfComment) {
			return sfComment.getText();
		}
		return null;
	}

	private static SourceForgeTrackerRestClient getSourceForge(
			SourceForgeBugTrackingSystem bugTracker) throws URISyntaxException {
		SourceForgeTrackerRestClient client =  new SourceForgeTrackerRestClient(bugTracker.getUrl());
		return client;
	}

	public static void main(String[] args) throws Exception {
		SourceForgeManager manager = new SourceForgeManager();

		SourceForgeBugTrackingSystem bts = new SourceForgeBugTrackingSystem();
		bts.setUrl("http://sourceforge.net/rest/p/soapui/bugs");

		/*
		SourceForgeTrackingSystemDelta delta = (SourceForgeTrackingSystemDelta) manager
				.getDelta(bts,
						new Date(new DateTime(2014, 5, 3, 0, 0).toDate()));
		System.out.println(delta.getNewBugs().size());
		for (BugTrackingSystemBug bug : delta.getNewBugs()) {
			System.out.println(bug.getBugId());
		}
		*/

		System.out.println();

		SourceForgeTrackingSystemDelta delta = (SourceForgeTrackingSystemDelta) manager.getDelta(null, bts,
				new Date(new DateTime(2014, 7, 14, 0, 0).toDate()));
		System.out.println(delta.getUpdatedBugs().size());
		for (BugTrackingSystemBug bug : delta.getUpdatedBugs()) {
			System.out.println(bug.getBugId());
		}

	}
}
