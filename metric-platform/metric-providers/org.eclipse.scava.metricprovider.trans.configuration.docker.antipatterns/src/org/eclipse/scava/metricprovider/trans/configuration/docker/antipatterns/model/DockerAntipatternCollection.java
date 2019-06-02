package org.eclipse.scava.metricprovider.trans.configuration.docker.antipatterns.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DockerAntipatternCollection extends PongoCollection<DockerAntipattern> {
	
	public DockerAntipatternCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<DockerAntipattern> findById(String id) {
		return new IteratorIterable<DockerAntipattern>(new PongoCursorIterator<DockerAntipattern>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DockerAntipattern> findBySmellName(String q) {
		return new IteratorIterable<DockerAntipattern>(new PongoCursorIterator<DockerAntipattern>(this, dbCollection.find(new BasicDBObject("smellName", q + ""))));
	}
	
	public DockerAntipattern findOneBySmellName(String q) {
		DockerAntipattern smell = (DockerAntipattern) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("smellName", q + "")));
		if (smell != null) {
			smell.setPongoCollection(this);
		}
		return smell;
	}
	
	
	@Override
	public Iterator<DockerAntipattern> iterator() {
		return new PongoCursorIterator<DockerAntipattern>(this, dbCollection.find());
	}
	
	public void add(DockerAntipattern smells) {
		super.add(smells);
	}
	
	public void remove(DockerAntipattern smells) {
		super.remove(smells);
	}

}
