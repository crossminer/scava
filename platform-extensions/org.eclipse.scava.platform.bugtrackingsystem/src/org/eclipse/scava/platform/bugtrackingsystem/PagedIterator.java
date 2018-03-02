/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem;

import java.util.Collection;
import java.util.Iterator;

public abstract class PagedIterator<T> implements Iterator<T> {

	public static class Page<T> {
		int total;
		Collection<T> values;

		public Page(Collection<T> values, Integer total) {
			this.values = values;
			this.total = total;
		}
	}

	private Integer total;
	private int retrieved = 0;
	private int nextPage = getFirstPageNumber();
	private Iterator<T> iterator;

	protected abstract Page<T> getNextPage();

	protected int getFirstPageNumber() {
		return 0;
	}

	@Override
	public boolean hasNext() {
		Page<T> page = null;
		if (null == iterator) {
			page = getNextPage();
		} else if (iterator.hasNext()) {
			return true;
		} else if (null != total && retrieved >= total) {
			return false;
		} else {
			page = getNextPage();
		}

		if (null == page) {
			return false;
		}

		nextPage++;
		iterator = page.values.iterator();
		retrieved += page.values.size();
		total = page.total;

		return (null != iterator && iterator.hasNext());
	}

	public int getNextPageNumber() {
		return nextPage;
	}

	public int getRetrieved() {
		return retrieved;
	}

	@Override
	public T next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
