package org.eclipse.scava.metricprovider.trans.configuration.puppet.designsmells.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DesignSmellCollection extends PongoCollection<Smell> {
	
	public DesignSmellCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Smell> findById(String id) {
		return new IteratorIterable<Smell>(new PongoCursorIterator<Smell>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Smell> findBySmellName(String q) {
		return new IteratorIterable<Smell>(new PongoCursorIterator<Smell>(this, dbCollection.find(new BasicDBObject("smellName", q + ""))));
	}
	
	public Smell findOneBySmellName(String q) {
		Smell smell = (Smell) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("smellName", q + "")));
		if (smell != null) {
			smell.setPongoCollection(this);
		}
		return smell;
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
