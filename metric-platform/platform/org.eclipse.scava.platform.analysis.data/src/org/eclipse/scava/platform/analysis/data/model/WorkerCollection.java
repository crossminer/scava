/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
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