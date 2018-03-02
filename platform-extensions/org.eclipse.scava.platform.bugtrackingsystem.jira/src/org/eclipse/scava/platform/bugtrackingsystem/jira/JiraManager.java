/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.jira;

import java.util.Iterator;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.bugtrackingsystem.cache.Cache;
import org.eclipse.scava.platform.bugtrackingsystem.cache.Caches;
import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.DateRangeCacheProvider;
import org.eclipse.scava.platform.bugtrackingsystem.jira.api.JiraComment;
import org.eclipse.scava.platform.bugtrackingsystem.jira.api.JiraIssue;
import org.eclipse.scava.platform.bugtrackingsystem.jira.api.JiraRestClient;
import org.eclipse.scava.platform.bugtrackingsystem.jira.api.JiraSearchResult;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.IBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem;
import org.joda.time.DateTime;

import com.mongodb.DB;

public class JiraManager implements
		IBugTrackingSystemManager<JiraBugTrackingSystem> {

	private Caches<JiraIssue, String> issueCaches = new Caches<JiraIssue, String>(
			new IssueCacheProvider());

	private static class IssueCacheProvider extends
			DateRangeCacheProvider<JiraIssue, String> {

		@Override
		public Iterator<JiraIssue> getItems(java.util.Date after,
				java.util.Date before, BugTrackingSystem bugTracker)
				throws Exception {
			JiraBugTrackingSystem bts = (JiraBugTrackingSystem) bugTracker;
			JiraRestClient jira = getJiraRestClient(bts);
			return jira.getIssues(bts.getProject(), after, before);
		}

		@Override
		public boolean changedOnDate(JiraIssue item, java.util.Date date,
				BugTrackingSystem bugTracker) {
			return findMatchOnDate(date, item.getCreationTime(),
					item.getUpdateDate());
		}

		@Override
		public boolean changedSinceDate(JiraIssue item, java.util.Date date,
				BugTrackingSystem bugTracker) {
			return findMatchSinceDate(date, item.getCreationTime(),
					item.getUpdateDate());
		}

		@Override
		public String getKey(JiraIssue item) {
			return item.getBugId();
		}

		@Override
		public void process(JiraIssue item, BugTrackingSystem bugTracker) {
			item.setBugTrackingSystem(bugTracker); // Is this needed?
			item.setDescription(null); // remove content field
			for (BugTrackingSystemComment comment : item.getComments()) {
				comment.setBugTrackingSystem(bugTracker); // Is this needed?
				comment.setText(null); // remove content field
			}

		}

	}

	@Override
	public boolean appliesTo(BugTrackingSystem bts) {
		return bts instanceof JiraBugTrackingSystem;
	}

	@Override
	public BugTrackingSystemDelta getDelta(DB db, JiraBugTrackingSystem bugTracker, Date date) throws Exception {

		java.util.Date day = date.toJavaDate();

		Cache<JiraIssue, String> issueCache = issueCaches
				.getCache(bugTracker, true);
		Iterable<JiraIssue> issues = issueCache.getItemsAfterDate(day);

		JiraBugTrackingSystemDelta delta = new JiraBugTrackingSystemDelta();
		for (JiraIssue issue : issues) {

			if (DateUtils.isSameDay(issue.getUpdateDate(), day)) {
				delta.getUpdatedBugs().add(issue);
			} else if (DateUtils.isSameDay(issue.getCreationTime(), day)) {
				delta.getNewBugs().add(issue);
			}

			// Store updated comments in delta
			for (BugTrackingSystemComment comment : issue.getComments()) {
				JiraComment jiraComment = (JiraComment) comment;

				java.util.Date updated = jiraComment.getUpdateDate();
				java.util.Date created = jiraComment.getCreationTime();

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
	public Date getFirstDate(DB db, JiraBugTrackingSystem bts) throws Exception {
		JiraRestClient jira = getJiraRestClient(bts);

		// Only interested in created field
		JiraSearchResult result = jira.search("project = \"" + bts.getProject()
				+ "\" order by created asc", 0, 1, "created");

		if (!result.getIssues().isEmpty()) {
			JiraIssue issue = result.getIssues().get(0);
			return new Date(issue.getCreationTime());
		}

		return null;
	}

	@Override
	public String getContents(DB db, JiraBugTrackingSystem bts, BugTrackingSystemBug bug) throws Exception {

		JiraRestClient jira = getJiraRestClient(bts);
		// Request only the description field in the rest response
		JiraIssue issue = jira.getIssue(bug.getBugId(), "description");
		if (null != issue) {
			return issue.getDescription();
		}

		return null;
	}

	@Override
	public String getContents(DB db, JiraBugTrackingSystem bts, BugTrackingSystemComment bugComment) throws Exception {

		JiraRestClient jira = getJiraRestClient(bts);
		JiraComment comment = jira.getComment(bugComment.getBugId(),
				bugComment.getCommentId());
		if (null != comment) {
			return comment.getText();
		}
		return null;
	}

	protected static JiraRestClient getJiraRestClient(JiraBugTrackingSystem bugTracker) {
		JiraRestClient client = new JiraRestClient(bugTracker.getUrl());
		String login = bugTracker.getLogin();
		if ( login != null && login.trim().length() > 0 && !"null".equals(login)) {
			client.setCredentials(login, bugTracker.getPassword());
		}
		return client;
	}

	public static void main(String[] args) throws Exception {

		JiraManager jm = new JiraManager();

		JiraBugTrackingSystem bts = new JiraBugTrackingSystem();
		bts.setUrl("http://jira.codehaus.org/");
		bts.setProject("MNG");

		JiraBugTrackingSystemDelta delta = (JiraBugTrackingSystemDelta) jm
				.getDelta(null, bts,
						new Date(new DateTime(2014, 6, 15, 0, 0).toDate()));

		System.out.println(delta.getNewBugs().size());
		System.out.println(delta.getUpdatedBugs().size());
		for (BugTrackingSystemBug bug : delta.getNewBugs()) {
			System.out.println(bug.getBugId() + " " + bug.getCreationTime());
		}
		System.out.println("===UPDATED===");
		for (BugTrackingSystemBug bug : delta.getUpdatedBugs()) {
			JiraIssue issue = (JiraIssue) bug;
			System.out.println(bug.getBugId() + " " + issue.getUpdateDate());
		}
		System.out.println("===COMMENTS===");
		for (BugTrackingSystemComment comment : delta.getComments()) {
			JiraComment jiraComment = (JiraComment) comment;
			System.out.println(comment.getCommentId() + " "
					+ jiraComment.getUpdateDate());
		}
	}
}
