/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

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
