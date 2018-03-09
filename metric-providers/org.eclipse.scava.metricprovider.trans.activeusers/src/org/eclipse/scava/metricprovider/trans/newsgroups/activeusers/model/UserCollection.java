/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.activeusers.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class UserCollection extends PongoCollection<User> {
	
	public UserCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<User> findById(String id) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<User> findByNewsgroupName(String q) {
		return new IteratorIterable<User>(new PongoCursorIterator<User>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public User findOneByNewsgroupName(String q) {
		User user = (User) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (user != null) {
			user.setPongoCollection(this);
		}
		return user;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<User> iterator() {
		return new PongoCursorIterator<User>(this, dbCollection.find());
	}
	
	public void add(User user) {
		super.add(user);
	}
	
	public void remove(User user) {
		super.remove(user);
	}
	
}
