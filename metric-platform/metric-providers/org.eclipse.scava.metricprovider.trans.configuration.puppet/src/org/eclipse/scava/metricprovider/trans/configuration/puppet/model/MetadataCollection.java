package org.eclipse.scava.metricprovider.trans.configuration.puppet.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MetadataCollection extends PongoCollection<Metadata> {
	
	public MetadataCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<Metadata> findById(String id) {
		return new IteratorIterable<Metadata>(new PongoCursorIterator<Metadata>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Metadata> findBySmellName(String q) {
		return new IteratorIterable<Metadata>(new PongoCursorIterator<Metadata>(this, dbCollection.find(new BasicDBObject("smellName", q + ""))));
	}
	
	public Metadata findOneBySmellName(String q) {
		Metadata smell = (Metadata) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("smellName", q + "")));
		if (smell != null) {
			smell.setPongoCollection(this);
		}
		return smell;
	}
	
	
	@Override
	public Iterator<Metadata> iterator() {
		return new PongoCursorIterator<Metadata>(this, dbCollection.find());
	}
	
	public void add(Metadata smells) {
		super.add(smells);
	}
	
	public void remove(Metadata smells) {
		super.remove(smells);
	}

}
