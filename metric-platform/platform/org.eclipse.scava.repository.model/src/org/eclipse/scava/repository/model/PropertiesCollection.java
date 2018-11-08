/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class PropertiesCollection extends PongoCollection<Properties> {
	
	public PropertiesCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("key");
	}
	
	public Iterable<Properties> findById(String id) {
		return new IteratorIterable<Properties>(new PongoCursorIterator<Properties>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Properties> findByName(String q) {
		return new IteratorIterable<Properties>(new PongoCursorIterator<Properties>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public Properties findOneByName(String q) {
		Properties properties = (Properties) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (properties != null) {
			properties.setPongoCollection(this);
		}
		return properties;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<Properties> findByKey(String q) {
		return new IteratorIterable<Properties>(new PongoCursorIterator<Properties>(this, dbCollection.find(new BasicDBObject("key", q + ""))));
	}
	
	public Properties findOneByKey(String q) {
		Properties properties = (Properties) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("key", q + "")));
		if (properties != null) {
			properties.setPongoCollection(this);
		}
		return properties;
	}
	

	public long countByKey(String q) {
		return dbCollection.count(new BasicDBObject("key", q + ""));
	}
	
	@Override
	public Iterator<Properties> iterator() {
		return new PongoCursorIterator<Properties>(this, dbCollection.find());
	}
	
	public void add(Properties properties) {
		super.add(properties);
	}
	
	public void remove(Properties properties) {
		super.remove(properties);
	}
	
}