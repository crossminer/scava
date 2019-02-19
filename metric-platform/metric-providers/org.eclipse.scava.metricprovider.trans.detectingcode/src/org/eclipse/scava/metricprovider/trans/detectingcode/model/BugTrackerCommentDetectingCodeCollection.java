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

public class BugTrackerCommentDetectingCodeCollection extends PongoCollection<BugTrackerCommentDetectingCode> {
	
	public BugTrackerCommentDetectingCodeCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerCommentDetectingCode> findById(String id) {
		return new IteratorIterable<BugTrackerCommentDetectingCode>(new PongoCursorIterator<BugTrackerCommentDetectingCode>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerCommentDetectingCode> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerCommentDetectingCode>(new PongoCursorIterator<BugTrackerCommentDetectingCode>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerCommentDetectingCode findOneByBugTrackerId(String q) {
		BugTrackerCommentDetectingCode bugTrackerCommentDetectingCode = (BugTrackerCommentDetectingCode) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerCommentDetectingCode != null) {
			bugTrackerCommentDetectingCode.setPongoCollection(this);
		}
		return bugTrackerCommentDetectingCode;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerCommentDetectingCode> iterator() {
		return new PongoCursorIterator<BugTrackerCommentDetectingCode>(this, dbCollection.find());
	}
	
	public void add(BugTrackerCommentDetectingCode bugTrackerCommentDetectingCode) {
		super.add(bugTrackerCommentDetectingCode);
	}
	
	public void remove(BugTrackerCommentDetectingCode bugTrackerCommentDetectingCode) {
		super.remove(bugTrackerCommentDetectingCode);
	}
	
}