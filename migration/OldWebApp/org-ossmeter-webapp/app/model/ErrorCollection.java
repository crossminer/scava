package model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ErrorCollection extends PongoCollection<Error> {
	
	public ErrorCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Error> findById(String id) {
		return new IteratorIterable<Error>(new PongoCursorIterator<Error>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<Error> iterator() {
		return new PongoCursorIterator<Error>(this, dbCollection.find());
	}
	
	public void add(Error error) {
		super.add(error);
	}
	
	public void remove(Error error) {
		super.remove(error);
	}
	
}