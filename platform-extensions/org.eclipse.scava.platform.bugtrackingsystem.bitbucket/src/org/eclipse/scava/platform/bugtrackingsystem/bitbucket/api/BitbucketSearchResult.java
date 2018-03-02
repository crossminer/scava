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

import java.util.Iterator;
import java.util.List;


public class BitbucketSearchResult implements Iterable<BitbucketIssue> {
	int count;

	Object filter;

	String search;

	List<BitbucketIssue> issues;

	public BitbucketSearchResult() {

	}

	public BitbucketSearchResult(Object filter, String search,
			List<BitbucketIssue> issues) {
		this.filter = filter;
		this.search = search;
		this.issues = issues;
	}

	public Iterator<BitbucketIssue> iterator() {
		return issues.iterator();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Issues ");
		sb.append("\n");
		for (BitbucketIssue issue : issues) {
			sb.append(issue);
			sb.append("\n");
		}
		return sb.toString();
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Object getFilter() {
		return filter;
	}

	public String getSearch() {
		return search;
	}

	public List<BitbucketIssue> getIssues() {
		return issues;
	}

}
