package com.googlecode.pongo.runtime;

import java.util.Iterator;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

public class PongoListIterator<T extends Pongo> implements Iterator<T>{
	
	protected Iterator<Object> dbListIterator = null; 
	protected PongoList<T> pongoList = null;
	
	public PongoListIterator(PongoList<T> pongoList) {
		this.pongoList = pongoList;
		dbListIterator = pongoList.dbList.iterator();
	}
		
	@Override
	public boolean hasNext() {
		return dbListIterator.hasNext();
	}

	@Override
	public T next() {
		return pongoList.wrap(dbListIterator.next());
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}
	
}
