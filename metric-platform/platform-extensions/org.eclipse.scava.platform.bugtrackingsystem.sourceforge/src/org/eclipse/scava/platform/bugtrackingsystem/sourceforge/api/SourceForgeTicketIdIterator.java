/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api;

import java.util.Iterator;

//TODO convert to PagedIterator
public class SourceForgeTicketIdIterator implements Iterator<Integer> {

	private final SourceForgeTrackerRestClient sourceforge;
	private final String query;

	public SourceForgeTicketIdIterator(
			SourceForgeTrackerRestClient sourceforge, String query) {
		this.sourceforge = sourceforge;
		this.query = query;
	}

	private Iterator<Integer> iterator;
	private int total;
	private int page = 0;

	@Override
	public boolean hasNext() {
		if (null == iterator) {
			return getNextPage();
		} else if (iterator.hasNext()) {
			return true;
		} else if (page * SourceForgeConstants.DEFAULT_PAGE_SIZE >= total) {
			return false;
		} else {
			return getNextPage();
		}
	}

	private boolean getNextPage() {
		SourceForgeSearchResult result;
		try {
			result = sourceforge.search(query, page,
					SourceForgeConstants.DEFAULT_PAGE_SIZE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		total = result.getCount();
		iterator = result.getTicketIds().iterator();
		page++;
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
