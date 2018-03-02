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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BitbucketIssueQuery {

	private String user;
	private String repository;
	private String search;
	private List<String> statuses = new ArrayList<String>();
	private List<String> priorities = new ArrayList<String>();
	private String sort;

	public BitbucketIssueQuery(String user, String repository) {
		this.user = user;
		this.repository = repository;
	}

	private BitbucketIssueQuery() {
	}

	public BitbucketIssueQuery shallowCopy() {
		BitbucketIssueQuery query = new BitbucketIssueQuery();
		query.user = user;
		query.repository = repository;
		query.search = search;
		query.statuses = statuses;
		query.priorities = priorities;
		query.sort = sort;
		return query;
	}

	public List<String> getPriorities() {
		return priorities;
	}

	/**
	 * 
	 * @param status
	 *            Add '!' to beginning of string for NOT query.
	 * @return
	 */
	public BitbucketIssueQuery addPriority(String priority) {
		priorities.add(priority);
		return this;
	}

	public Collection<String> getStatuses() {
		return statuses;
	}

	/**
	 * 
	 * @param status
	 *            Add '!' to beginning of string for NOT query.
	 * @return
	 */
	public BitbucketIssueQuery addStatus(String status) {
		statuses.add(status);
		return this;
	}

	public String getSearch() {
		return search;
	}

	public BitbucketIssueQuery setSearch(String search) {
		this.search = search;
		return this;
	}

	public String getUser() {
		return user;
	}

	public String getRepository() {
		return repository;
	}

	public BitbucketIssueQuery setSort(String sort) {
		this.sort = sort;
		return this;
	}

	public String getSort() {
		return sort;
	}
	
}
