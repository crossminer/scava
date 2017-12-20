package com.googlecode.pongo.runtime;

import java.util.Iterator;

public class PrimitiveListIterator<T> implements Iterator<T> {
	
	protected Iterator<?> iterator = null;
	
	public PrimitiveListIterator(Iterator<?> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public T next() {
		return (T) iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}

}
