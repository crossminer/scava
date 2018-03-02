/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerTopicCollection extends PongoCollection<BugTrackerTopic> {
	
	public BugTrackerTopicCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerTopic> findById(String id) {
		return new IteratorIterable<BugTrackerTopic>(new PongoCursorIterator<BugTrackerTopic>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerTopic> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerTopic>(new PongoCursorIterator<BugTrackerTopic>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerTopic findOneByBugTrackerId(String q) {
		BugTrackerTopic bugTrackerTopic = (BugTrackerTopic) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerTopic != null) {
			bugTrackerTopic.setPongoCollection(this);
		}
		return bugTrackerTopic;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerTopic> iterator() {
		return new PongoCursorIterator<BugTrackerTopic>(this, dbCollection.find());
	}
	
	public void add(BugTrackerTopic bugTrackerTopic) {
		super.add(bugTrackerTopic);
	}
	
	public void remove(BugTrackerTopic bugTrackerTopic) {
		super.remove(bugTrackerTopic);
	}
	
}
