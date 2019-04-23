/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/

package org.eclipse.scava.plugin.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class AutoSortedArrayList<T extends Comparable<T>> extends ArrayList<T> {

	private static final long serialVersionUID = -445052455641400368L;

	@Override
	public boolean add(T mt) {
		int index = Collections.binarySearch(this, mt);
		if (index < 0)
			index = ~index;
		super.add(index, mt);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = super.addAll(c);

		if (result) {
			Collections.sort(this);
		}

		return result;
	}

	@Override
	public void add(int index, T element) {
		throw new UnsupportedOperationException("Cannot insert an element at a specific position into a sorted list.");
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		throw new UnsupportedOperationException(
				"Cannot insert a collection at a specific position into a sorted list.");
	}

}