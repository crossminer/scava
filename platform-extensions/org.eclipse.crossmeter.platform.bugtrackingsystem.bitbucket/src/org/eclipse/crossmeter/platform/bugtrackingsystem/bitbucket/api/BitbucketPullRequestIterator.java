/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.bitbucket.api;

import org.eclipse.crossmeter.platform.bugtrackingsystem.PagedIterator;

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
