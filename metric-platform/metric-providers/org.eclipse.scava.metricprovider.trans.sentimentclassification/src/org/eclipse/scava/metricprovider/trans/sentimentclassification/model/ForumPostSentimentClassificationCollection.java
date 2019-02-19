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

public class ForumPostSentimentClassificationCollection extends PongoCollection<ForumPostSentimentClassification> {
	
	public ForumPostSentimentClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostSentimentClassification> findById(String id) {
		return new IteratorIterable<ForumPostSentimentClassification>(new PongoCursorIterator<ForumPostSentimentClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ForumPostSentimentClassification> iterator() {
		return new PongoCursorIterator<ForumPostSentimentClassification>(this, dbCollection.find());
	}
	
	public void add(ForumPostSentimentClassification forumPostSentimentClassification) {
		super.add(forumPostSentimentClassification);
	}
	
	public void remove(ForumPostSentimentClassification forumPostSentimentClassification) {
		super.remove(forumPostSentimentClassification);
	}
	
}