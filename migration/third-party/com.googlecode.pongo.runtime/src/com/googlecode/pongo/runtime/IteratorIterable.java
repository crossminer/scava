package com.googlecode.pongo.runtime;

import java.util.Iterator;

public class IteratorIterable<T> implements Iterable<T> {
	
	protected Iterator<T> iterator;
	
	public IteratorIterable(Iterator<T> iterator) {
		this.iterator = iterator;
	}
	
	@Override
	public Iterator<T> iterator() {
		return iterator;
	}

}
