/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.DateRangeCacheProvider;
import org.eclipse.scava.platform.bugtrackingsystem.github.api.GitHubSession;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;
import org.eclipse.egit.github.core.PullRequest;
import org.eclipse.egit.github.core.client.PageIterator;
import org.joda.time.DateTime;

public class PullRequestCacheProvider extends
		DateRangeCacheProvider<GitHubPullRequest, Integer> {

	private class ItemIterator implements Iterator<GitHubPullRequest> {

		private Iterator<PullRequest> pullRequests;
		// private Date after;
		private Date before;

		private PullRequest nextPullRequest;

		ItemIterator(GitHubBugTracker bugTracker, Date after, Date before)
				throws IOException {

			GitHubSession github = GitHubManager.getSession(bugTracker);
			PageIterator<PullRequest> pages = github.getPullRequests(
					bugTracker.getUser(), bugTracker.getRepository(),
					new DateTime(after), GitHubSession.SORT_UPDATED,
					GitHubSession.DIRECTION_ASCENDING);

			pullRequests = new SimpleIterator<PullRequest>(pages);

			// this.after = after;
			this.before = before;
		}

		@Override
		public boolean hasNext() {
			while (pullRequests.hasNext()) {
				PullRequest pullRequest = pullRequests.next();
				Date updated = pullRequest.getUpdatedAt();

				if (null != before && updated.after(before)) {
					break;
				}

				nextPullRequest = pullRequest;
				return true;
			}

			return false;
		}

		@Override
		public GitHubPullRequest next() {
			return processor.process(nextPullRequest);
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}

	private final GitHubEntityManager processor;

	public PullRequestCacheProvider(GitHubEntityManager processor) {
		this.processor = processor;
	}

	@Override
	public Iterator<GitHubPullRequest> getItems(Date after, Date before,
			BugTrackingSystem bugTracker) throws Exception {
		return new ItemIterator((GitHubBugTracker) bugTracker, after, before);
	}

	@Override
	public boolean changedOnDate(GitHubPullRequest item, Date date,
			BugTrackingSystem bugTracker) {
		return findMatchOnDate(date, item.getCreatedAt(), item.getUpdatedAt(),
				item.getClosedAt());
	}

	@Override
	public boolean changedSinceDate(GitHubPullRequest item, Date date,
			BugTrackingSystem bugTracker) {
		return findMatchSinceDate(date, item.getCreatedAt(),
				item.getUpdatedAt(), item.getClosedAt());
	}

	@Override
	public Integer getKey(GitHubPullRequest item) {
		return item.getNumber();
	}

	@Override
	public void process(GitHubPullRequest item, BugTrackingSystem bugTracker) {
		// Do nothing
	}
}
