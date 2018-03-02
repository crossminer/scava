/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DayArticlesCollection extends PongoCollection<DayArticles> {
	
	public DayArticlesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<DayArticles> findById(String id) {
		return new IteratorIterable<DayArticles>(new PongoCursorIterator<DayArticles>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DayArticles> findByName(String q) {
		return new IteratorIterable<DayArticles>(new PongoCursorIterator<DayArticles>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public DayArticles findOneByName(String q) {
		DayArticles dayArticles = (DayArticles) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (dayArticles != null) {
			dayArticles.setPongoCollection(this);
		}
		return dayArticles;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<DayArticles> iterator() {
		return new PongoCursorIterator<DayArticles>(this, dbCollection.find());
	}
	
	public void add(DayArticles dayArticles) {
		super.add(dayArticles);
	}
	
	public void remove(DayArticles dayArticles) {
		super.remove(dayArticles);
	}
	
}
