/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesCollection extends PongoCollection<NewsgroupArticles> {
	
	public NewsgroupArticlesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupArticles> findById(String id) {
		return new IteratorIterable<NewsgroupArticles>(new PongoCursorIterator<NewsgroupArticles>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticles> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupArticles>(new PongoCursorIterator<NewsgroupArticles>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupArticles findOneByNewsgroupName(String q) {
		NewsgroupArticles newsgroupArticles = (NewsgroupArticles) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupArticles != null) {
			newsgroupArticles.setPongoCollection(this);
		}
		return newsgroupArticles;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticles> iterator() {
		return new PongoCursorIterator<NewsgroupArticles>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticles newsgroupArticles) {
		super.add(newsgroupArticles);
	}
	
	public void remove(NewsgroupArticles newsgroupArticles) {
		super.remove(newsgroupArticles);
	}
	
}
