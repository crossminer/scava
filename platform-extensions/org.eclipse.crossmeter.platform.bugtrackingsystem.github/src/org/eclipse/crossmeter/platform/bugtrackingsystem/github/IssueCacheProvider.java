/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.github;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.crossmeter.platform.bugtrackingsystem.cache.provider.DateRangeCacheProvider;
import org.eclipse.crossmeter.platform.bugtrackingsystem.github.api.GitHubIssueQuery;
import org.eclipse.crossmeter.platform.bugtrackingsystem.github.api.GitHubSession;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;
import org.eclipse.crossmeter.repository.model.github.GitHubBugTracker;
import org.eclipse.egit.github.core.Issue;
import org.joda.time.DateTime;

class IssueCacheProvider extends
		DateRangeCacheProvider<GitHubIssue, String> {

	private class ItemIterator implements Iterator<GitHubIssue> {

		private Iterator<Issue> issues;
		// private Date after;
		private Date before;

		private Issue nextIssue;

		ItemIterator(GitHubBugTracker bugTracker, Date after, Date before)
				throws IOException {
			GitHubIssueQuery query = new GitHubIssueQuery(bugTracker.getUser(),
					bugTracker.getRepository());
			query = query.setAllState().setSince(new DateTime(after))
					.sortByUpdated().setAscendingDirection();

			GitHubSession github = GitHubManager.getSession(bugTracker);
			issues = new SimpleIterator<Issue>(github.getIssues(query));

			// this.after = after;
			this.before = before;
		}

		@Override
		public boolean hasNext() {
			while (issues.hasNext()) {
				Issue issue = issues.next();
				Date updated = issue.getUpdatedAt();

				if (null != before && updated.after(before)) {
					break;
				}

				nextIssue = issue;
				return true;
			}

			return false;
		}

		@Override
		public GitHubIssue next() {
			return processor.process(nextIssue);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final GitHubEntityManager processor;

	public IssueCacheProvider(GitHubEntityManager processor) {
		this.processor = processor;
	}

	@Override
	public Iterator<GitHubIssue> getItems(java.util.Date after,
			java.util.Date before, BugTrackingSystem bugTracker)
			throws Exception {

		return new ItemIterator((GitHubBugTracker) bugTracker, after, before);
	}

	@Override
	public void process(GitHubIssue item, BugTrackingSystem bugTracker) {
		item.setBugTrackingSystem(bugTracker); // Is this needed?

	}

	@Override
	public boolean changedOnDate(GitHubIssue item, java.util.Date date,
			BugTrackingSystem bugTracker) {
		return findMatchOnDate(date, item.getCreationTime(),
				item.getUpdatedTime());
	}

	@Override
	public boolean changedSinceDate(GitHubIssue item, java.util.Date date,
			BugTrackingSystem bugTracker) {
		return findMatchSinceDate(date, item.getCreationTime(),
				item.getUpdatedTime());
	}

	@Override
	public String getKey(GitHubIssue item) {
		return item.getBugId();
	}
}
