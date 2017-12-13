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
package org.eclipse.crossmeter.platform.bugtrackingsystem.jira.api;

import java.util.Iterator;

// TODO convert to PagedIterator
public class JiraIssueIterator implements Iterator<JiraIssue> {
	private int total;
	private int retrieved = 0;
	private Iterator<JiraIssue> iterator;
	private String fields;

	private JiraRestClient jira;
	private String jql;
	
	public JiraIssueIterator(JiraRestClient jira, String jql) {
		this(jira, jql ,null);
	}

	public JiraIssueIterator(JiraRestClient jira, String jql, String fields) {
		this.jira = jira;
		this.jql = jql;
		this.fields = fields;
	}

	@Override
	public boolean hasNext() {
		if (null == iterator) {
			return getNextPage();
		} else if (iterator.hasNext()) {
			return true;
		} else if (retrieved >= total) {
			return false;
		} else {
			return getNextPage();
		}
	}

	private boolean getNextPage() {
		JiraSearchResult result;
		try {
			result = jira.search(jql, retrieved, fields);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		total = result.getTotal();
		retrieved += result.getIssues().size();
		iterator = result.getIssues().iterator();
		return iterator.hasNext();
	}

	@Override
	public JiraIssue next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
