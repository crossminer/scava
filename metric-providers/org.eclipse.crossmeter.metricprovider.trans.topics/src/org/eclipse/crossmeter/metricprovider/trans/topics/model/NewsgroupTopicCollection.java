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

public class NewsgroupTopicCollection extends PongoCollection<NewsgroupTopic> {
	
	public NewsgroupTopicCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupTopic> findById(String id) {
		return new IteratorIterable<NewsgroupTopic>(new PongoCursorIterator<NewsgroupTopic>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupTopic> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupTopic>(new PongoCursorIterator<NewsgroupTopic>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupTopic findOneByNewsgroupName(String q) {
		NewsgroupTopic newsgroupTopic = (NewsgroupTopic) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupTopic != null) {
			newsgroupTopic.setPongoCollection(this);
		}
		return newsgroupTopic;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupTopic> iterator() {
		return new PongoCursorIterator<NewsgroupTopic>(this, dbCollection.find());
	}
	
	public void add(NewsgroupTopic newsgroupTopic) {
		super.add(newsgroupTopic);
	}
	
	public void remove(NewsgroupTopic newsgroupTopic) {
		super.remove(newsgroupTopic);
	}
	
}