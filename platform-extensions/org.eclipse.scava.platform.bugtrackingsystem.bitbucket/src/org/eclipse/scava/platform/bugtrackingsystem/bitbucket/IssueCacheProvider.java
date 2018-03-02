/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket;

import java.util.Date;
import java.util.Iterator;

import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api.BitbucketIssue;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api.BitbucketIssueQuery;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api.BitbucketRestClient;
import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.DateRangeCacheProvider;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem;

class IssueCacheProvider extends
		DateRangeCacheProvider<BitbucketIssue, String> {

	private static class ItemIterator implements Iterator<BitbucketIssue> {

		private Iterator<BitbucketIssue> issues;
		private Date after;
		private Date before;

		private BitbucketIssue nextIssue;

		ItemIterator(BitbucketBugTrackingSystem bts, Date after, Date before) {

			// Due to the lack of filtering issues by date within the Bitbucket
			// API, we have this
			// simplistic mechanism.
			BitbucketRestClient bitbucket = BitbucketManager
					.getBitbucketRestClient(bts);
			BitbucketIssueQuery query = new BitbucketIssueQuery(bts.getUser(),
					bts.getRepository());
			query.setSort("-utc_last_updated");

			issues = bitbucket.getIssues(query, true);

			this.after = after;
			this.before = before;
		}

		@Override
		public boolean hasNext() {
			while (issues.hasNext()) {
				BitbucketIssue issue = issues.next();
				Date updated = issue.getUpdateDate();

				// Remember issues come in latest first order.
				if (null != after && updated.before(after)) {
					break;
				} else if (null == before || updated.before(before)) {
					nextIssue = issue;
					return true;
				}
			}

			return false;
		}

		@Override
		public BitbucketIssue next() {
			return nextIssue;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}

	@Override
	public Iterator<BitbucketIssue> getItems(java.util.Date after,
			java.util.Date before, BugTrackingSystem bugTracker)
			throws Exception {

		return new ItemIterator((BitbucketBugTrackingSystem) bugTracker, after,
				before);
	}

	@Override
	public void process(BitbucketIssue item, BugTrackingSystem bugTracker) {
		item.setBugTrackingSystem(bugTracker); // Is this needed?
		item.setContent(null); // remove content field
		for (BugTrackingSystemComment comment : item.getComments()) {
			comment.setBugTrackingSystem(bugTracker); // Is this needed?
			comment.setText(null); // remove content field
		}
	}

	@Override
	public boolean changedOnDate(BitbucketIssue item, java.util.Date date,
			BugTrackingSystem bugTracker) {
		return findMatchOnDate(date, item.getCreationTime(),
				item.getUpdateDate());
	}

	@Override
	public boolean changedSinceDate(BitbucketIssue item, java.util.Date date,
			BugTrackingSystem bugTracker) {
		return findMatchSinceDate(date, item.getCreationTime(),
				item.getUpdateDate());
	}

	@Override
	public String getKey(BitbucketIssue item) {
		return item.getBugId();
	}
}
