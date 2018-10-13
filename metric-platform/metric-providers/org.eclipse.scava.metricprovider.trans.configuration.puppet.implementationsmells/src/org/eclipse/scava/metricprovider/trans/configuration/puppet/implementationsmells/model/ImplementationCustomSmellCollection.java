package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ImplementationCustomSmellCollection extends PongoCollection<CustomSmell> {
	
	public ImplementationCustomSmellCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<CustomSmell> findById(String id) {
		return new IteratorIterable<CustomSmell>(new PongoCursorIterator<CustomSmell>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	@Override
	public Iterator<CustomSmell> iterator() {
		return new PongoCursorIterator<CustomSmell>(this, dbCollection.find());
	}
	
	public void add(CustomSmell customSmells) {
		super.add(customSmells);
	}
	
	public void remove(CustomSmell customSmells) {
		super.remove(customSmells);
	}

}
