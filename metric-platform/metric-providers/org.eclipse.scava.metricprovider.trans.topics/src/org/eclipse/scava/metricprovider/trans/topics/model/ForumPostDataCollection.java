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

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostDataCollection extends PongoCollection<ForumPostData> {
	
	public ForumPostDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumPostData> findById(String id) {
		return new IteratorIterable<ForumPostData>(new PongoCursorIterator<ForumPostData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumPostData> findByForumId(String q) {
		return new IteratorIterable<ForumPostData>(new PongoCursorIterator<ForumPostData>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumPostData findOneByForumId(String q) {
		ForumPostData forumPostData = (ForumPostData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumPostData != null) {
			forumPostData.setPongoCollection(this);
		}
		return forumPostData;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumPostData> iterator() {
		return new PongoCursorIterator<ForumPostData>(this, dbCollection.find());
	}
	
	public void add(ForumPostData forumPostData) {
		super.add(forumPostData);
	}
	
	public void remove(ForumPostData forumPostData) {
		super.remove(forumPostData);
	}
	
}