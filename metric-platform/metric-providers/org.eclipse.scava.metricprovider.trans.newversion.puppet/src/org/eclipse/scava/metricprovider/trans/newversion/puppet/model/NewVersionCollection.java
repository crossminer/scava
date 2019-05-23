package org.eclipse.scava.metricprovider.trans.newversion.puppet.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewVersionCollection extends PongoCollection<NewPuppetVersion> {
	
	public NewVersionCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<NewPuppetVersion> findById(String id) {
		return new IteratorIterable<NewPuppetVersion>(new PongoCursorIterator<NewPuppetVersion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewPuppetVersion> findByPackageName(String q) {
		return new IteratorIterable<NewPuppetVersion>(new PongoCursorIterator<NewPuppetVersion>(this, dbCollection.find(new BasicDBObject("packageName", q + ""))));
	}
	
	public NewPuppetVersion findOneByPackageName(String q) {
		NewPuppetVersion newVersion = (NewPuppetVersion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("packageName", q + "")));
		if (newVersion != null) {
			newVersion.setPongoCollection(this);
		}
		return newVersion;
	}
	
	
	@Override
	public Iterator<NewPuppetVersion> iterator() {
		return new PongoCursorIterator<NewPuppetVersion>(this, dbCollection.find());
	}
	
	public void add(NewPuppetVersion newVersions) {
		super.add(newVersions);
	}
	
	public void remove(NewPuppetVersion newVersions) {
		super.remove(newVersions);
	}
}
