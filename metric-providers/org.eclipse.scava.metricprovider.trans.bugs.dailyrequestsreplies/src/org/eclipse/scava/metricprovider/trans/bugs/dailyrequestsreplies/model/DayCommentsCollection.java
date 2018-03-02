/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DayCommentsCollection extends PongoCollection<DayComments> {
	
	public DayCommentsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<DayComments> findById(String id) {
		return new IteratorIterable<DayComments>(new PongoCursorIterator<DayComments>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DayComments> findByName(String q) {
		return new IteratorIterable<DayComments>(new PongoCursorIterator<DayComments>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public DayComments findOneByName(String q) {
		DayComments dayComments = (DayComments) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (dayComments != null) {
			dayComments.setPongoCollection(this);
		}
		return dayComments;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<DayComments> iterator() {
		return new PongoCursorIterator<DayComments>(this, dbCollection.find());
	}
	
	public void add(DayComments dayComments) {
		super.add(dayComments);
	}
	
	public void remove(DayComments dayComments) {
		super.remove(dayComments);
	}
	
}
