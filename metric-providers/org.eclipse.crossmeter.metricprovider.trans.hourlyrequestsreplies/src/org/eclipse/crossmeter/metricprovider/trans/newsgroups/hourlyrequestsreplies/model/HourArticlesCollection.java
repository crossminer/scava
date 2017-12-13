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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class HourArticlesCollection extends PongoCollection<HourArticles> {
	
	public HourArticlesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("hour");
	}
	
	public Iterable<HourArticles> findById(String id) {
		return new IteratorIterable<HourArticles>(new PongoCursorIterator<HourArticles>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<HourArticles> findByHour(String q) {
		return new IteratorIterable<HourArticles>(new PongoCursorIterator<HourArticles>(this, dbCollection.find(new BasicDBObject("hour", q + ""))));
	}
	
	public HourArticles findOneByHour(String q) {
		HourArticles hourArticles = (HourArticles) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("hour", q + "")));
		if (hourArticles != null) {
			hourArticles.setPongoCollection(this);
		}
		return hourArticles;
	}
	

	public long countByHour(String q) {
		return dbCollection.count(new BasicDBObject("hour", q + ""));
	}
	
	@Override
	public Iterator<HourArticles> iterator() {
		return new PongoCursorIterator<HourArticles>(this, dbCollection.find());
	}
	
	public void add(HourArticles hourArticles) {
		super.add(hourArticles);
	}
	
	public void remove(HourArticles hourArticles) {
		super.remove(hourArticles);
	}
	
}