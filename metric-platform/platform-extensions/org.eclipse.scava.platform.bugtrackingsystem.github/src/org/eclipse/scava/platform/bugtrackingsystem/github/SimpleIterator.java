/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github;

import java.util.Iterator;

import org.eclipse.egit.github.core.client.PageIterator;

public class SimpleIterator<T> implements Iterator<T> {
	
	private PageIterator<T> pageIterator;
	private Iterator<T> itemIterator;
	
	public SimpleIterator(PageIterator<T> pageIterator) {
		this.pageIterator = pageIterator;
	}

	@Override
	public boolean hasNext() {
		if ( itemIterator != null && itemIterator.hasNext() ) {
			return itemIterator.hasNext();
		} else if ( pageIterator.hasNext() ) {
			try {
				itemIterator =  pageIterator.next().iterator();
				return itemIterator.hasNext();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public T next() {
		return itemIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
		
	}

}
