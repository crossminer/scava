package com.googlecode.pongo.runtime;

import java.util.Iterator;

import com.mongodb.DBCursor;

public class PongoCursorIterator<T> implements Iterator<T> {
	
	protected DBCursor dbCursor;
	protected PongoCollection pongoCollection;
	
	public PongoCursorIterator(PongoCollection pongoCollection, DBCursor dbCursor) {
		this.dbCursor = dbCursor;
		this.pongoCollection = pongoCollection;
	}
	
	public void setDbCursor(DBCursor dbCursor) {
		this.dbCursor = dbCursor;
	}
	
	@Override
	public boolean hasNext() {
		return dbCursor.hasNext();
	}

	@Override
	public T next() {
		Pongo next = (Pongo) PongoFactory.getInstance().createPongo(dbCursor.next());
		next.setPongoCollection(pongoCollection);
		return (T) next;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	
}
