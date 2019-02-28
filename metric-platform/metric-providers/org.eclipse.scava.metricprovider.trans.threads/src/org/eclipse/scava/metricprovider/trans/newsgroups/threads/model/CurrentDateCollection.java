package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CurrentDateCollection extends PongoCollection<CurrentDate> {
	
	public CurrentDateCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<CurrentDate> findById(String id) {
		return new IteratorIterable<CurrentDate>(new PongoCursorIterator<CurrentDate>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<CurrentDate> iterator() {
		return new PongoCursorIterator<CurrentDate>(this, dbCollection.find());
	}
	
	public void add(CurrentDate currentDate) {
		super.add(currentDate);
	}
	
	public void remove(CurrentDate currentDate) {
		super.remove(currentDate);
	}
	
}