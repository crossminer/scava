/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
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

public class NewsgroupArticlesSentimentClassificationCollection extends PongoCollection<NewsgroupArticlesSentimentClassification> {
	
	public NewsgroupArticlesSentimentClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsGroupName");
	}
	
	public Iterable<NewsgroupArticlesSentimentClassification> findById(String id) {
		return new IteratorIterable<NewsgroupArticlesSentimentClassification>(new PongoCursorIterator<NewsgroupArticlesSentimentClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlesSentimentClassification> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticlesSentimentClassification>(new PongoCursorIterator<NewsgroupArticlesSentimentClassification>(this, dbCollection.find(new BasicDBObject("newsGroupName", q + ""))));
	}
	
	public NewsgroupArticlesSentimentClassification findOneByNewsGroupName(String q) {
		NewsgroupArticlesSentimentClassification newsgroupArticlesSentimentClassification = (NewsgroupArticlesSentimentClassification) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsGroupName", q + "")));
		if (newsgroupArticlesSentimentClassification != null) {
			newsgroupArticlesSentimentClassification.setPongoCollection(this);
		}
		return newsgroupArticlesSentimentClassification;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlesSentimentClassification> iterator() {
		return new PongoCursorIterator<NewsgroupArticlesSentimentClassification>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlesSentimentClassification newsgroupArticlesSentimentClassification) {
		super.add(newsgroupArticlesSentimentClassification);
	}
	
	public void remove(NewsgroupArticlesSentimentClassification newsgroupArticlesSentimentClassification) {
		super.remove(newsgroupArticlesSentimentClassification);
	}
	
}