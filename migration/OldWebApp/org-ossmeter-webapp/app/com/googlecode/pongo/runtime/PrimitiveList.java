package com.googlecode.pongo.runtime;

import java.util.Iterator;

import com.mongodb.BasicDBList;

public class PrimitiveList<T> extends BasicDBListWrapper<T>{
	
	protected Pongo container = null;
	
	public PrimitiveList(Pongo container, BasicDBList dbList) {
		super(dbList);
		this.container = container;
	}

	@Override
	public Iterator<T> iterator() {
		return new PrimitiveListIterator<T>(dbList.iterator());
	}

	@Override
	protected T wrap(Object o) {
		return (T) o;
	}

	@Override
	protected Object unwrap(Object o) {
		return o;
	}

	@Override
	protected void added(T t) {
		super.added(t);
		container.notifyChanged();
	}
	
	@Override
	protected void removed(Object o) {
		super.removed(o);
		container.notifyChanged();
	}
	
}
