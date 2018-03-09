/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.committers.model;

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
