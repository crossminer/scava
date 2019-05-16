package org.eclipse.scava.metricprovider.trans.newversion.maven.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewVersionCollection extends PongoCollection<NewMavenVersion> {
	
	public NewVersionCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<NewMavenVersion> findById(String id) {
		return new IteratorIterable<NewMavenVersion>(new PongoCursorIterator<NewMavenVersion>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewMavenVersion> findByPackageName(String q) {
		return new IteratorIterable<NewMavenVersion>(new PongoCursorIterator<NewMavenVersion>(this, dbCollection.find(new BasicDBObject("packageName", q + ""))));
	}
	
	public NewMavenVersion findOneByPackageName(String q) {
		NewMavenVersion newVersion = (NewMavenVersion) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("packageName", q + "")));
		if (newVersion != null) {
			newVersion.setPongoCollection(this);
		}
		return newVersion;
	}
	
	
	@Override
	public Iterator<NewMavenVersion> iterator() {
		return new PongoCursorIterator<NewMavenVersion>(this, dbCollection.find());
	}
	
	public void add(NewMavenVersion newVersions) {
		super.add(newVersions);
	}
	
	public void remove(NewMavenVersion newVersions) {
		super.remove(newVersions);
	}
}
