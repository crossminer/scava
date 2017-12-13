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
package org.eclipse.crossmeter.metricprovider.trans.bugs.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ContentClassCollection extends PongoCollection<ContentClass> {
	
	public ContentClassCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<ContentClass> findById(String id) {
		return new IteratorIterable<ContentClass>(new PongoCursorIterator<ContentClass>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ContentClass> findByBugTrackerId(String q) {
		return new IteratorIterable<ContentClass>(new PongoCursorIterator<ContentClass>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public ContentClass findOneByBugTrackerId(String q) {
		ContentClass contentClass = (ContentClass) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (contentClass != null) {
			contentClass.setPongoCollection(this);
		}
		return contentClass;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<ContentClass> iterator() {
		return new PongoCursorIterator<ContentClass>(this, dbCollection.find());
	}
	
	public void add(ContentClass contentClass) {
		super.add(contentClass);
	}
	
	public void remove(ContentClass contentClass) {
		super.remove(contentClass);
	}
	
}