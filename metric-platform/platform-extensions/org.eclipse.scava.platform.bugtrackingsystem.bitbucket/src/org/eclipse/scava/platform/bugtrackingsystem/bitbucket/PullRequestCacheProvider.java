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

import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api.BitbucketPullRequest;
import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api.BitbucketRestClient;
import org.eclipse.scava.platform.bugtrackingsystem.cache.provider.BasicCacheProvider;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem;

public class PullRequestCacheProvider extends
		BasicCacheProvider<BitbucketPullRequest, Long> {

	@Override
	public Iterator<BitbucketPullRequest> getItems(BugTrackingSystem bugTracker)
			throws Exception {

		BitbucketBugTrackingSystem bts = (BitbucketBugTrackingSystem) bugTracker;
		BitbucketRestClient bitbucket = BitbucketManager
				.getBitbucketRestClient(bts);
		return bitbucket.getPullRequests(bts.getUser(), bts.getRepository());
	}

	@Override
	public boolean changedOnDate(BitbucketPullRequest item, Date date,
			BugTrackingSystem bugTracker) {
		return findMatchOnDate(date, item.getCreatedOn(), item.getUpdatedOn());
	}

	@Override
	public boolean changedSinceDate(BitbucketPullRequest item, Date date,
			BugTrackingSystem bugTracker) {
		return findMatchSinceDate(date, item.getCreatedOn(), item.getUpdatedOn());
	}

	@Override
	public Long getKey(BitbucketPullRequest item) {
		return item.getId();
	}

	@Override
	public void process(BitbucketPullRequest item, BugTrackingSystem bugTracker) {
		// Do nothing
	}

}
