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
package org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommentDataCollection extends PongoCollection<CommentData> {
	
	public CommentDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<CommentData> findById(String id) {
		return new IteratorIterable<CommentData>(new PongoCursorIterator<CommentData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommentData> findByBugTrackerId(String q) {
		return new IteratorIterable<CommentData>(new PongoCursorIterator<CommentData>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public CommentData findOneByBugTrackerId(String q) {
		CommentData commentData = (CommentData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (commentData != null) {
			commentData.setPongoCollection(this);
		}
		return commentData;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<CommentData> iterator() {
		return new PongoCursorIterator<CommentData>(this, dbCollection.find());
	}
	
	public void add(CommentData commentData) {
		super.add(commentData);
	}
	
	public void remove(CommentData commentData) {
		super.remove(commentData);
	}
	
}