/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesDataCollection extends PongoCollection<NewsgroupArticlesData> {
	
	public NewsgroupArticlesDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsGroupName");
	}
	
	public Iterable<NewsgroupArticlesData> findById(String id) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlesData> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("newsGroupName", q + ""))));
	}
	
	public NewsgroupArticlesData findOneByNewsGroupName(String q) {
		NewsgroupArticlesData newsgroupArticlesData = (NewsgroupArticlesData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsGroupName", q + "")));
		if (newsgroupArticlesData != null) {
			newsgroupArticlesData.setPongoCollection(this);
		}
		return newsgroupArticlesData;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlesData> iterator() {
		return new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlesData newsgroupArticlesData) {
		super.add(newsgroupArticlesData);
	}
	
	public void remove(NewsgroupArticlesData newsgroupArticlesData) {
		super.remove(newsgroupArticlesData);
	}
	
}
