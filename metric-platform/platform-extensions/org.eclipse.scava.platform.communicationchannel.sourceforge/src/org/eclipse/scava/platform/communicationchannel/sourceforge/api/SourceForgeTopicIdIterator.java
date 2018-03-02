/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.sourceforge.api;

import java.util.Iterator;


//TODO convert to PagedIterator
public class SourceForgeTopicIdIterator implements Iterator<String> {

	private final SourceForgeDiscussionRestClient sourceforge;
	private final int forumId;

	public SourceForgeTopicIdIterator(
			int forumId, SourceForgeDiscussionRestClient sourceforge) {
		this.sourceforge = sourceforge;
		this.forumId = forumId;
	}

	private Iterator<String> iterator;
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
		SourceForgeTopicSearch result;
		try {
			result = sourceforge.getTopics(forumId, page,
					SourceForgeConstants.DEFAULT_PAGE_SIZE);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		total = result.getCount();
		iterator = result.getTopicIds().iterator();
		page++;
		return iterator.hasNext();
	}

	@Override
	public  String next() {
		return iterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
}
