package org.eclipse.scava.metricprovider.trans.newversion.docker.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewVersionCollection extends PongoCollection<NewDockerVersion> {
	
	public NewVersionCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<NewDockerVersion> findById(String id) {
		return new IteratorIterable<NewDockerVersion>(new PongoCursorIterator<NewDockerVersion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewDockerVersion> findByPackageName(String q) {
		return new IteratorIterable<NewDockerVersion>(new PongoCursorIterator<NewDockerVersion>(this, dbCollection.find(new BasicDBObject("packageName", q + ""))));
	}
	
	public NewDockerVersion findOneByPackageName(String q) {
		NewDockerVersion newVersion = (NewDockerVersion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("packageName", q + "")));
		if (newVersion != null) {
			newVersion.setPongoCollection(this);
		}
		return newVersion;
	}
	
	
	@Override
	public Iterator<NewDockerVersion> iterator() {
		return new PongoCursorIterator<NewDockerVersion>(this, dbCollection.find());
	}
	
	public void add(NewDockerVersion newVersions) {
		super.add(newVersions);
	}
	
	public void remove(NewDockerVersion newVersions) {
		super.remove(newVersions);
	}
}
