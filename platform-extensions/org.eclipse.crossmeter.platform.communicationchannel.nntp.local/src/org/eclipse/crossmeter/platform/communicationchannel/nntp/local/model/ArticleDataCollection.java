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
package org.eclipse.crossmeter.platform.communicationchannel.nntp.local.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ArticleDataCollection extends PongoCollection<ArticleData> {
	
	public ArticleDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("url");
		createIndex("articleNumber");
	}
	
	public Iterable<ArticleData> findById(String id) {
		return new IteratorIterable<ArticleData>(new PongoCursorIterator<ArticleData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ArticleData> findByUrl(String q) {
		return new IteratorIterable<ArticleData>(new PongoCursorIterator<ArticleData>(this, dbCollection.find(new BasicDBObject("url", q + ""))));
	}
	
	public ArticleData findOneByUrl(String q) {
		ArticleData articleData = (ArticleData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("url", q + "")));
		if (articleData != null) {
			articleData.setPongoCollection(this);
		}
		return articleData;
	}
	

	public long countByUrl(String q) {
		return dbCollection.count(new BasicDBObject("url", q + ""));
	}
	public Iterable<ArticleData> findByArticleNumber(int q) {
		return new IteratorIterable<ArticleData>(new PongoCursorIterator<ArticleData>(this, dbCollection.find(new BasicDBObject("articleNumber", q + ""))));
	}
	
	public ArticleData findOneByArticleNumber(int q) {
		ArticleData articleData = (ArticleData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("articleNumber", q + "")));
		if (articleData != null) {
			articleData.setPongoCollection(this);
		}
		return articleData;
	}
	

	public long countByArticleNumber(int q) {
		return dbCollection.count(new BasicDBObject("articleNumber", q + ""));
	}
	
	@Override
	public Iterator<ArticleData> iterator() {
		return new PongoCursorIterator<ArticleData>(this, dbCollection.find());
	}
	
	public void add(ArticleData articleData) {
		super.add(articleData);
	}
	
	public void remove(ArticleData articleData) {
		super.remove(articleData);
	}
	
}