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
package org.eclipse.crossmeter.platform.bugtrackingsystem.github;

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
