package com.googlecode.pongo.runtime;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.googlecode.pongo.runtime.querying.QueryProducer;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public abstract class PongoCollection<T extends Pongo> implements Iterable<T> {
	
	protected DBCollection dbCollection;
	protected Set<Pongo> toSave = new HashSet<Pongo>();
	protected Set<Pongo> toDelete = new HashSet<Pongo>();
	
	public DBCollection getDbCollection() {
		return dbCollection;
	}
	
	public PongoCollection(DBCollection dbCollection) {
		this.dbCollection = dbCollection;
	}
	
	public void createIndex(String field) {
		dbCollection.ensureIndex(new BasicDBObject(field, 1), new BasicDBObject("background", true));
	}
	
	public String getName() {
		return dbCollection.getName();
	}
	
	public Iterable<T> find(QueryProducer query) {
		return new IteratorIterable<T>(new PongoCursorIterator<T>(this, dbCollection.find(query.getDBObject())));
	}
	
	public Iterable<T> find(QueryProducer... queries) {
		BasicDBObject obj = new BasicDBObject();
		BasicDBList list = new BasicDBList();
		
		for (int i = 0; i < queries.length; i++) {
			list.add(queries[i].getDBObject());
		}
		obj.append("$and", list);
//		System.out.println(obj.toString());
		return new IteratorIterable<T>(new PongoCursorIterator<T>(this, dbCollection.find(obj)));
	}
	
	public T findOne(QueryProducer query) {
		T t = (T) PongoFactory.getInstance().createPongo(dbCollection.findOne(query.getDBObject()));
		if (t != null) {
			t.setPongoCollection(this);
		}
		return t;
	}
	
	protected void add(Pongo pongo) {
		pongo.setPongoCollection(this);
		toSave.add(pongo);
	}
	
	protected void remove(Pongo pongo) {
		if (toSave.contains(pongo)) {
			toSave.remove(pongo);
		}
		toDelete.add(pongo);
	}
	
	public T first() {
		return iterator().next();
	}
	
	public T second() {
		Iterator<T> iterator = iterator();
		iterator.next();
		return iterator.next();
	}
	
	public void sync() {
		for (Pongo pongo : toSave) {
			pongo.preSave();
			dbCollection.save(pongo.getDbObject());
		}
		toSave.clear();
		
		for (Pongo pongo : toDelete) {
			pongo.preDelete();
			dbCollection.remove(new BasicDBObject("_id", pongo.getId()));
		}
		toDelete.clear();
	}
	
	public Set<Pongo> getToSave() {
		return toSave;
	}
	
	public Set<Pongo> getToDelete() {
		return toDelete;
	}
	
	public long size() {
		return dbCollection.count();
	}
	
}
