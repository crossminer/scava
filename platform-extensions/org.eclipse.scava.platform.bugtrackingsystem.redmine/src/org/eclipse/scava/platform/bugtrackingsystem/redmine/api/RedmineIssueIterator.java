/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.redmine.api;

import java.util.Iterator;

public class RedmineIssueIterator implements Iterator<RedmineIssue> {
	private final RedmineRestClient redmine;
	private Iterator<Integer> issueIds;

	public RedmineIssueIterator(RedmineRestClient redmine,
			Iterator<Integer> issueIds) {
		this.redmine = redmine;
		this.issueIds = issueIds;
	}

	@Override
	public boolean hasNext() {
		return issueIds.hasNext();
	}

	@Override
	public RedmineIssue next() {
		try {
			return redmine.getIssue(issueIds.next());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
