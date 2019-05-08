/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.topics.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ForumPostTopicCollection extends PongoCollection<ForumPostTopic> {
	
	public ForumPostTopicCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumPostTopic> findById(String id) {
		return new IteratorIterable<ForumPostTopic>(new PongoCursorIterator<ForumPostTopic>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumPostTopic> findByForumId(String q) {
		return new IteratorIterable<ForumPostTopic>(new PongoCursorIterator<ForumPostTopic>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumPostTopic findOneByForumId(String q) {
		ForumPostTopic forumPostTopic = (ForumPostTopic) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumPostTopic != null) {
			forumPostTopic.setPongoCollection(this);
		}
		return forumPostTopic;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumPostTopic> iterator() {
		return new PongoCursorIterator<ForumPostTopic>(this, dbCollection.find());
	}
	
	public void add(ForumPostTopic forumPostTopic) {
		super.add(forumPostTopic);
	}
	
	public void remove(ForumPostTopic forumPostTopic) {
		super.remove(forumPostTopic);
	}
	
}