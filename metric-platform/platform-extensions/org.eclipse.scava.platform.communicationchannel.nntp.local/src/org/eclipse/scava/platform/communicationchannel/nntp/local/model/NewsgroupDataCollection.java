/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.nntp.local.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupDataCollection extends PongoCollection<NewsgroupData> {
	
	public NewsgroupDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("name");
		createIndex("url");
	}
	
	public Iterable<NewsgroupData> findById(String id) {
		return new IteratorIterable<NewsgroupData>(new PongoCursorIterator<NewsgroupData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupData> findByName(String q) {
		return new IteratorIterable<NewsgroupData>(new PongoCursorIterator<NewsgroupData>(this, dbCollection.find(new BasicDBObject("name", q + ""))));
	}
	
	public NewsgroupData findOneByName(String q) {
		NewsgroupData newsgroupData = (NewsgroupData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("name", q + "")));
		if (newsgroupData != null) {
			newsgroupData.setPongoCollection(this);
		}
		return newsgroupData;
	}
	

	public long countByName(String q) {
		return dbCollection.count(new BasicDBObject("name", q + ""));
	}
	public Iterable<NewsgroupData> findByUrl(String q) {
		return new IteratorIterable<NewsgroupData>(new PongoCursorIterator<NewsgroupData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public NewsgroupData findOneByUrl(String q) {
		NewsgroupData newsgroupData = (NewsgroupData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (newsgroupData != null) {
			newsgroupData.setPongoCollection(this);
		}
		return newsgroupData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupData> iterator() {
		return new PongoCursorIterator<NewsgroupData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupData newsgroupData) {
		super.add(newsgroupData);
	}
	
	public void remove(NewsgroupData newsgroupData) {
		super.remove(newsgroupData);
	}
	
}
