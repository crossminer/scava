/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerCommentsEmotionClassificationCollection extends PongoCollection<BugTrackerCommentsEmotionClassification> {
	
	public BugTrackerCommentsEmotionClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerCommentsEmotionClassification> findById(String id) {
		return new IteratorIterable<BugTrackerCommentsEmotionClassification>(new PongoCursorIterator<BugTrackerCommentsEmotionClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerCommentsEmotionClassification> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerCommentsEmotionClassification>(new PongoCursorIterator<BugTrackerCommentsEmotionClassification>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerCommentsEmotionClassification findOneByBugTrackerId(String q) {
		BugTrackerCommentsEmotionClassification bugTrackerCommentsEmotionClassification = (BugTrackerCommentsEmotionClassification) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerCommentsEmotionClassification != null) {
			bugTrackerCommentsEmotionClassification.setPongoCollection(this);
		}
		return bugTrackerCommentsEmotionClassification;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerCommentsEmotionClassification> iterator() {
		return new PongoCursorIterator<BugTrackerCommentsEmotionClassification>(this, dbCollection.find());
	}
	
	public void add(BugTrackerCommentsEmotionClassification bugTrackerCommentsEmotionClassification) {
		super.add(bugTrackerCommentsEmotionClassification);
	}
	
	public void remove(BugTrackerCommentsEmotionClassification bugTrackerCommentsEmotionClassification) {
		super.remove(bugTrackerCommentsEmotionClassification);
	}
	
}