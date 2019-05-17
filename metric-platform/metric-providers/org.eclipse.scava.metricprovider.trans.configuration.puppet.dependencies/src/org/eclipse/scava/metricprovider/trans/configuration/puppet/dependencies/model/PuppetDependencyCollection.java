package org.eclipse.scava.metricprovider.trans.configuration.puppet.dependencies.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class PuppetDependencyCollection extends PongoCollection<PuppetDependency> {
	
	public PuppetDependencyCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<PuppetDependency> findById(String id) {
		return new IteratorIterable<PuppetDependency>(new PongoCursorIterator<PuppetDependency>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<PuppetDependency> findByPuppetDependenciesName(String q) {
		return new IteratorIterable<PuppetDependency>(new PongoCursorIterator<PuppetDependency>(this, dbCollection.find(new BasicDBObject("dependencyName", q + ""))));
	}
	
	public PuppetDependency findOneByPuppetDependenciesName(String q) {
		PuppetDependency puppetDependency = (PuppetDependency) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("dependencyName", q + "")));
		if (puppetDependency != null) {
			puppetDependency.setPongoCollection(this);
		}
		return puppetDependency;
	}
	
	
	@Override
	public Iterator<PuppetDependency> iterator() {
		return new PongoCursorIterator<PuppetDependency>(this, dbCollection.find());
	}
	
	public void add(PuppetDependency puppetDependencies) {
		super.add(puppetDependencies);
	}
	
	public void remove(PuppetDependency puppetDependencies) {
		super.remove(puppetDependencies);
	}

}
