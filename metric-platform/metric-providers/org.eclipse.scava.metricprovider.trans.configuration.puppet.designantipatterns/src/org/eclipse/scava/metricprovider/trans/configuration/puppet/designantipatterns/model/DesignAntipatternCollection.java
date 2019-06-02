package org.eclipse.scava.metricprovider.trans.configuration.puppet.designantipatterns.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DesignAntipatternCollection extends PongoCollection<DesignAntipattern> {
	
	public DesignAntipatternCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<DesignAntipattern> findById(String id) {
		return new IteratorIterable<DesignAntipattern>(new PongoCursorIterator<DesignAntipattern>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DesignAntipattern> findBySmellName(String q) {
		return new IteratorIterable<DesignAntipattern>(new PongoCursorIterator<DesignAntipattern>(this, dbCollection.find(new BasicDBObject("smellName", q + ""))));
	}
	
	public DesignAntipattern findOneBySmellName(String q) {
		DesignAntipattern smell = (DesignAntipattern) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("smellName", q + "")));
		if (smell != null) {
			smell.setPongoCollection(this);
		}
		return smell;
	}
	
	
	@Override
	public Iterator<DesignAntipattern> iterator() {
		return new PongoCursorIterator<DesignAntipattern>(this, dbCollection.find());
	}
	
	public void add(DesignAntipattern smells) {
		super.add(smells);
	}
	
	public void remove(DesignAntipattern smells) {
		super.remove(smells);
	}

}
