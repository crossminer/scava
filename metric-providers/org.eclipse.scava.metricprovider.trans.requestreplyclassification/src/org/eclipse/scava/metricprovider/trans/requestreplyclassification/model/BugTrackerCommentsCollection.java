/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

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
