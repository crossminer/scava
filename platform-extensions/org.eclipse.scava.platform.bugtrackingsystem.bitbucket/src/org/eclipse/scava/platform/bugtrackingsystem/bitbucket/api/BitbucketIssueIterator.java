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
