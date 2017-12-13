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
package org.eclipse.crossmeter.metricprovider.trans.dailycommits.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DayCollection extends PongoCollection<Day> {
	
	public DayCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
	}
	
	public Iterable<Day> findById(String id) {
		return new IteratorIterable<Day>(new PongoCursorIterator<Day>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Day> findByName(String q) {
		return new IteratorIterable<Day>(new PongoCursorIterator<Day>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Day findOneByName(String q) {
		Day day = (Day) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (day != null) {
			day.setPongoCollection(this);
		}
		return day;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	
	@Override
	public Iterator<Day> iterator() {
		return new PongoCursorIterator<Day>(this, dbCollection.find());
	}
	
	public void add(Day day) {
		super.add(day);
	}
	
	public void remove(Day day) {
		super.remove(day);
	}
	
}