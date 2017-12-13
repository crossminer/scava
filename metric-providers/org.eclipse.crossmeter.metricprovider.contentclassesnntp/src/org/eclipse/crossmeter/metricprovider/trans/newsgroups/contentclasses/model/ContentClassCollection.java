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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ContentClassCollection extends PongoCollection<ContentClass> {
	
	public ContentClassCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<ContentClass> findById(String id) {
		return new IteratorIterable<ContentClass>(new PongoCursorIterator<ContentClass>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ContentClass> findByNewsgroupName(String q) {
		return new IteratorIterable<ContentClass>(new PongoCursorIterator<ContentClass>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public ContentClass findOneByNewsgroupName(String q) {
		ContentClass contentClass = (ContentClass) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (contentClass != null) {
			contentClass.setPongoCollection(this);
		}
		return contentClass;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
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