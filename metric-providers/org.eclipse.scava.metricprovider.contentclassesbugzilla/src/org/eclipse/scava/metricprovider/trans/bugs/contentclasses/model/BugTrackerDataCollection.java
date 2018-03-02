/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerDataCollection extends PongoCollection<BugTrackerData> {
	
	public BugTrackerDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerData> findById(String id) {
		return new IteratorIterable<BugTrackerData>(new PongoCursorIterator<BugTrackerData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerData> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerData>(new PongoCursorIterator<BugTrackerData>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerData findOneByBugTrackerId(String q) {
		BugTrackerData bugTrackerData = (BugTrackerData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerData != null) {
			bugTrackerData.setPongoCollection(this);
		}
		return bugTrackerData;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerData> iterator() {
		return new PongoCursorIterator<BugTrackerData>(this, dbCollection.find());
	}
	
	public void add(BugTrackerData bugTrackerData) {
		super.add(bugTrackerData);
	}
	
	public void remove(BugTrackerData bugTrackerData) {
		super.remove(bugTrackerData);
	}
	
}
