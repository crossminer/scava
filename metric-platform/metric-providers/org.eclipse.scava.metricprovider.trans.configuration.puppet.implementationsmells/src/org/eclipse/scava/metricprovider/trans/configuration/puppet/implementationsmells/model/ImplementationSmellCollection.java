package org.eclipse.scava.metricprovider.trans.configuration.puppet.implementationsmells.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ImplementationSmellCollection extends PongoCollection<Smell> {
	
	public ImplementationSmellCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Smell> findById(String id) {
		return new IteratorIterable<Smell>(new PongoCursorIterator<Smell>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	@Override
	public Iterator<Smell> iterator() {
		return new PongoCursorIterator<Smell>(this, dbCollection.find());
	}
	
	public void add(Smell smells) {
		super.add(smells);
	}
	
	public void remove(Smell smells) {
		super.remove(smells);
	}

}
