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

public class BitbucketIssueIterator extends PagedIterator<BitbucketIssue> {

	private BitbucketRestClient bitbucket;
	private BitbucketIssueQuery query;
	private boolean retrieveComments;

	public BitbucketIssueIterator(BitbucketRestClient bitbucket,
			BitbucketIssueQuery query, boolean retrieveComments) {
		this.bitbucket = bitbucket;
		this.query = query;
		this.retrieveComments = retrieveComments;
	}

	protected Page<BitbucketIssue> getNextPage() {
		BitbucketSearchResult result;
		try {
			result = bitbucket.search(query, retrieveComments, getRetrieved());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return new Page<BitbucketIssue>(result.getIssues(),
				result.getCount());
	}

}
