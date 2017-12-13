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
package org.eclipse.crossmeter.metricprovider.trans.bugs.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class HourCommentsCollection extends PongoCollection<HourComments> {
	
	public HourCommentsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("hour");
	}
	
	public Iterable<HourComments> findById(String id) {
		return new IteratorIterable<HourComments>(new PongoCursorIterator<HourComments>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<HourComments> findByHour(String q) {
		return new IteratorIterable<HourComments>(new PongoCursorIterator<HourComments>(this, dbCollection.find(new BasicDBObject("hour", q + ""))));
	}
	
	public HourComments findOneByHour(String q) {
		HourComments hourComments = (HourComments) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("hour", q + "")));
		if (hourComments != null) {
			hourComments.setPongoCollection(this);
		}
		return hourComments;
	}
	

	public long countByHour(String q) {
		return dbCollection.count(new BasicDBObject("hour", q + ""));
	}
	
	@Override
	public Iterator<HourComments> iterator() {
		return new PongoCursorIterator<HourComments>(this, dbCollection.find());
	}
	
	public void add(HourComments hourComments) {
		super.add(hourComments);
	}
	
	public void remove(HourComments hourComments) {
		super.remove(hourComments);
	}
	
}