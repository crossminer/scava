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
package org.eclipse.crossmeter.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerCommentsCollection extends PongoCollection<BugTrackerComments> {
	
	public BugTrackerCommentsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerComments> findById(String id) {
		return new IteratorIterable<BugTrackerComments>(new PongoCursorIterator<BugTrackerComments>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerComments> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerComments>(new PongoCursorIterator<BugTrackerComments>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerComments findOneByBugTrackerId(String q) {
		BugTrackerComments bugTrackerComments = (BugTrackerComments) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerComments != null) {
			bugTrackerComments.setPongoCollection(this);
		}
		return bugTrackerComments;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerComments> iterator() {
		return new PongoCursorIterator<BugTrackerComments>(this, dbCollection.find());
	}
	
	public void add(BugTrackerComments bugTrackerComments) {
		super.add(bugTrackerComments);
	}
	
	public void remove(BugTrackerComments bugTrackerComments) {
		super.remove(bugTrackerComments);
	}
	
}