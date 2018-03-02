/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.activeusers.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugDataCollection extends PongoCollection<BugData> {
	
	public BugDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugData> findById(String id) {
		return new IteratorIterable<BugData>(new PongoCursorIterator<BugData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugData> findByBugTrackerId(String q) {
		return new IteratorIterable<BugData>(new PongoCursorIterator<BugData>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugData findOneByBugTrackerId(String q) {
		BugData bugData = (BugData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugData != null) {
			bugData.setPongoCollection(this);
		}
		return bugData;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugData> iterator() {
		return new PongoCursorIterator<BugData>(this, dbCollection.find());
	}
	
	public void add(BugData bugData) {
		super.add(bugData);
	}
	
	public void remove(BugData bugData) {
		super.remove(bugData);
	}
	
}
