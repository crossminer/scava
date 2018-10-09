package org.eclipse.scava.platform.analysis.data.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class WorkerCollection extends PongoCollection<Worker> {
	
	public WorkerCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("workerId");
	}
	
	public Iterable<Worker> findById(String id) {
		return new IteratorIterable<Worker>(new PongoCursorIterator<Worker>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Worker> findByWorkerId(String q) {
		return new IteratorIterable<Worker>(new PongoCursorIterator<Worker>(this, dbCollection.find(new BasicDBObject("workerId", q + ""))));
	}
	
	public Worker findOneByWorkerId(String q) {
		Worker worker = (Worker) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("workerId", q + "")));
		if (worker != null) {
			worker.setPongoCollection(this);
		}
		return worker;
	}
	

	public long countByWorkerId(String q) {
		return dbCollection.count(new BasicDBObject("workerId", q + ""));
	}
	
	@Override
	public Iterator<Worker> iterator() {
		return new PongoCursorIterator<Worker>(this, dbCollection.find());
	}
	
	public void add(Worker worker) {
		super.add(worker);
	}
	
	public void remove(Worker worker) {
		super.remove(worker);
	}
	
}