/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class ForumsPostsCollection extends PongoCollection<ForumsPosts> {
	
	public ForumsPostsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumsPosts> findById(String id) {
		return new IteratorIterable<ForumsPosts>(new PongoCursorIterator<ForumsPosts>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumsPosts> findByForumId(String q) {
		return new IteratorIterable<ForumsPosts>(new PongoCursorIterator<ForumsPosts>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumsPosts findOneByForumId(String q) {
		ForumsPosts forumsPosts = (ForumsPosts) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumsPosts != null) {
			forumsPosts.setPongoCollection(this);
		}
		return forumsPosts;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumsPosts> iterator() {
		return new PongoCursorIterator<ForumsPosts>(this, dbCollection.find());
	}
	
	public void add(ForumsPosts forumsPosts) {
		super.add(forumsPosts);
	}
	
	public void remove(ForumsPosts forumsPosts) {
		super.remove(forumsPosts);
	}
	
}