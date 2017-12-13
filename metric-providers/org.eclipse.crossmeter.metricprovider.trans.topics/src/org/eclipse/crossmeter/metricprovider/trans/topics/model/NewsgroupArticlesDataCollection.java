/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesDataCollection extends PongoCollection<NewsgroupArticlesData> {
	
	public NewsgroupArticlesDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupArticlesData> findById(String id) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlesData> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupArticlesData findOneByNewsgroupName(String q) {
		NewsgroupArticlesData newsgroupArticlesData = (NewsgroupArticlesData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupArticlesData != null) {
			newsgroupArticlesData.setPongoCollection(this);
		}
		return newsgroupArticlesData;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlesData> iterator() {
		return new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlesData newsgroupArticlesData) {
		super.add(newsgroupArticlesData);
	}
	
	public void remove(NewsgroupArticlesData newsgroupArticlesData) {
		super.remove(newsgroupArticlesData);
	}
	
}