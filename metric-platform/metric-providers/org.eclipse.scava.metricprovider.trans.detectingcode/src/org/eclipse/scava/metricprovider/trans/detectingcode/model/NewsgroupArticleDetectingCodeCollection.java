/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.detectingcode.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticleDetectingCodeCollection extends PongoCollection<NewsgroupArticleDetectingCode> {
	
	public NewsgroupArticleDetectingCodeCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsGroupName");
	}
	
	public Iterable<NewsgroupArticleDetectingCode> findById(String id) {
		return new IteratorIterable<NewsgroupArticleDetectingCode>(new PongoCursorIterator<NewsgroupArticleDetectingCode>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticleDetectingCode> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticleDetectingCode>(new PongoCursorIterator<NewsgroupArticleDetectingCode>(this, dbCollection.find(new BasicDBObject("newsGroupName", q + ""))));
	}
	
	public NewsgroupArticleDetectingCode findOneByNewsGroupName(String q) {
		NewsgroupArticleDetectingCode newsgroupArticleDetectingCode = (NewsgroupArticleDetectingCode) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsGroupName", q + "")));
		if (newsgroupArticleDetectingCode != null) {
			newsgroupArticleDetectingCode.setPongoCollection(this);
		}
		return newsgroupArticleDetectingCode;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticleDetectingCode> iterator() {
		return new PongoCursorIterator<NewsgroupArticleDetectingCode>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticleDetectingCode newsgroupArticleDetectingCode) {
		super.add(newsgroupArticleDetectingCode);
	}
	
	public void remove(NewsgroupArticleDetectingCode newsgroupArticleDetectingCode) {
		super.remove(newsgroupArticleDetectingCode);
	}
	
}