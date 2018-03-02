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
import java.util.Map;

//TODO convert to PagedIterator
public class RedmineIssueIds implements Iterable<Integer>{
	private final RedmineRestClient redmine;
	private Map<String, String> filter;

	public RedmineIssueIds(RedmineRestClient redmine, Map<String, String> filter) {
		this.redmine = redmine;
		this.filter = filter;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new RedmineIssueIdIterator();
	}

	private class RedmineIssueIdIterator implements Iterator<Integer> {
		private int total;
		private int retrieved = 0;
		private Iterator<Integer> iterator;

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
			RedmineBasicSearchResult result = null;
			try {
				result = redmine.basicSearch(filter, retrieved);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			total = result.getTotalCount();
			retrieved += result.getIssueIds().size();
			iterator = result.getIssueIds().iterator();
			return iterator.hasNext();
		}

		@Override
		public Integer next() {
			return iterator.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}

	}
}
