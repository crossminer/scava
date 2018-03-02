/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api;

import org.eclipse.scava.platform.bugtrackingsystem.PagedIterator;

class BitbucketPullRequestIterator extends PagedIterator<BitbucketPullRequest> {
	private BitbucketRestClient bitbucket;
	private String user;
	private String repository;

	public BitbucketPullRequestIterator(BitbucketRestClient bitbucket,
			String user, String repository) {
		this.bitbucket = bitbucket;
		this.user = user;
		this.repository = repository;
	}
	
	@Override
	protected int getFirstPageNumber() {
		return 1;
	}

	protected Page<BitbucketPullRequest> getNextPage() {
		BitbucketPullRequestPage result;
		try {
			result = bitbucket.getPullRequests(user, repository, getNextPageNumber());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Page<BitbucketPullRequest>(result.getValues(), result.getSize());
	}

}
