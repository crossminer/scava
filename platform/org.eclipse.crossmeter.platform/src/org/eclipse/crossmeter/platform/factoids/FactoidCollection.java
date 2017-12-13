/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.factoids;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class FactoidCollection extends PongoCollection<Factoid> {
	
	public FactoidCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("metricId");
		createIndex("category");
	}
	
	public Iterable<Factoid> findById(String id) {
		return new IteratorIterable<Factoid>(new PongoCursorIterator<Factoid>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Factoid> findByMetricId(String q) {
		return new IteratorIterable<Factoid>(new PongoCursorIterator<Factoid>(this, dbCollection.find(new BasicDBObject("metricId", q + ""))));
	}
	
	public Factoid findOneByMetricId(String q) {
		Factoid factoid = (Factoid) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("metricId", q + "")));
		if (factoid != null) {
			factoid.setPongoCollection(this);
		}
		return factoid;
	}
	

	public long countByMetricId(String q) {
		return dbCollection.count(new BasicDBObject("metricId", q + ""));
	}
	public Iterable<Factoid> findByCategory(FactoidCategory q) {
		return new IteratorIterable<Factoid>(new PongoCursorIterator<Factoid>(this, dbCollection.find(new BasicDBObject("category", q + ""))));
	}
	
	public Factoid findOneByCategory(FactoidCategory q) {
		Factoid factoid = (Factoid) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("category", q + "")));
		if (factoid != null) {
			factoid.setPongoCollection(this);
		}
		return factoid;
	}
	

	public long countByCategory(FactoidCategory q) {
		return dbCollection.count(new BasicDBObject("category", q + ""));
	}
	
	@Override
	public Iterator<Factoid> iterator() {
		return new PongoCursorIterator<Factoid>(this, dbCollection.find());
	}
	
	public void add(Factoid factoid) {
		super.add(factoid);
	}
	
	public void remove(Factoid factoid) {
		super.remove(factoid);
	}
	
}