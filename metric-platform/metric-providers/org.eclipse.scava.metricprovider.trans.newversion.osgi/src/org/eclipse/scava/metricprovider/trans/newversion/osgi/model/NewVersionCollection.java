package org.eclipse.scava.metricprovider.trans.newversion.osgi.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewVersionCollection extends PongoCollection<NewOsgiVersion> {
	
	public NewVersionCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<NewOsgiVersion> findById(String id) {
		return new IteratorIterable<NewOsgiVersion>(new PongoCursorIterator<NewOsgiVersion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewOsgiVersion> findByPackageName(String q) {
		return new IteratorIterable<NewOsgiVersion>(new PongoCursorIterator<NewOsgiVersion>(this, dbCollection.find(new BasicDBObject("packageName", q + ""))));
	}
	
	public NewOsgiVersion findOneByPackageName(String q) {
		NewOsgiVersion newVersion = (NewOsgiVersion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("packageName", q + "")));
		if (newVersion != null) {
			newVersion.setPongoCollection(this);
		}
		return newVersion;
	}
	
	
	@Override
	public Iterator<NewOsgiVersion> iterator() {
		return new PongoCursorIterator<NewOsgiVersion>(this, dbCollection.find());
	}
	
	public void add(NewOsgiVersion newVersions) {
		super.add(newVersions);
	}
	
	public void remove(NewOsgiVersion newVersions) {
		super.remove(newVersions);
	}
}
