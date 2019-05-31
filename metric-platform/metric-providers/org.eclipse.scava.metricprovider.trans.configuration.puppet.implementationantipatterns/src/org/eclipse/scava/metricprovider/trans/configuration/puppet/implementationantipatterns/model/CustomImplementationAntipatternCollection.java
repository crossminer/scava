package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationantipatterns.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class CustomImplementationAntipatternCollection extends PongoCollection<CustomImplementationAntipattern> {
	
	public CustomImplementationAntipatternCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<CustomImplementationAntipattern> findById(String id) {
		return new IteratorIterable<CustomImplementationAntipattern>(new PongoCursorIterator<CustomImplementationAntipattern>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	@Override
	public Iterator<CustomImplementationAntipattern> iterator() {
		return new PongoCursorIterator<CustomImplementationAntipattern>(this, dbCollection.find());
	}
	
	public void add(CustomImplementationAntipattern implementationCustomAntipattern) {
		super.add(implementationCustomAntipattern);
	}
	
	public void remove(CustomImplementationAntipattern implementationCustomAntipattern) {
		super.remove(implementationCustomAntipattern);
	}

}
