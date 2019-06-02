package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationantipatterns.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ImplementationAntipatternCollection extends PongoCollection<ImplementationAntipattern> {
	
	public ImplementationAntipatternCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ImplementationAntipattern> findById(String id) {
		return new IteratorIterable<ImplementationAntipattern>(new PongoCursorIterator<ImplementationAntipattern>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	@Override
	public Iterator<ImplementationAntipattern> iterator() {
		return new PongoCursorIterator<ImplementationAntipattern>(this, dbCollection.find());
	}
	
	public void add(ImplementationAntipattern implementationAntipattern) {
		super.add(implementationAntipattern);
	}
	
	public void remove(ImplementationAntipattern implementationAntipattern) {
		super.remove(implementationAntipattern);
	}

}
