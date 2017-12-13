/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.committers.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommitterCollection extends PongoCollection<Committer> {
	
	public CommitterCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<Committer> findById(String id) {
		return new IteratorIterable<Committer>(new PongoCursorIterator<Committer>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Committer> findByName(String q) {
		return new IteratorIterable<Committer>(new PongoCursorIterator<Committer>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Committer findOneByName(String q) {
		Committer committer = (Committer) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (committer != null) {
			committer.setPongoCollection(this);
		}
		return committer;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Committer> iterator() {
		return new PongoCursorIterator<Committer>(this, dbCollection.find());
	}
	
	public void add(Committer committer) {
		super.add(committer);
	}
	
	public void remove(Committer committer) {
		super.remove(committer);
	}
	
}