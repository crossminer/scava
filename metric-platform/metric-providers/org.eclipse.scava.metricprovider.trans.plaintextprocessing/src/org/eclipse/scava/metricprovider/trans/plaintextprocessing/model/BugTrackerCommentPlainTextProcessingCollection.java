/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerCommentPlainTextProcessingCollection extends PongoCollection<BugTrackerCommentPlainTextProcessing> {
	
	public BugTrackerCommentPlainTextProcessingCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerCommentPlainTextProcessing> findById(String id) {
		return new IteratorIterable<BugTrackerCommentPlainTextProcessing>(new PongoCursorIterator<BugTrackerCommentPlainTextProcessing>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerCommentPlainTextProcessing> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerCommentPlainTextProcessing>(new PongoCursorIterator<BugTrackerCommentPlainTextProcessing>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerCommentPlainTextProcessing findOneByBugTrackerId(String q) {
		BugTrackerCommentPlainTextProcessing bugTrackerCommentPlainTextProcessing = (BugTrackerCommentPlainTextProcessing) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerCommentPlainTextProcessing != null) {
			bugTrackerCommentPlainTextProcessing.setPongoCollection(this);
		}
		return bugTrackerCommentPlainTextProcessing;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerCommentPlainTextProcessing> iterator() {
		return new PongoCursorIterator<BugTrackerCommentPlainTextProcessing>(this, dbCollection.find());
	}
	
	public void add(BugTrackerCommentPlainTextProcessing bugTrackerCommentPlainTextProcessing) {
		super.add(bugTrackerCommentPlainTextProcessing);
	}
	
	public void remove(BugTrackerCommentPlainTextProcessing bugTrackerCommentPlainTextProcessing) {
		super.remove(bugTrackerCommentPlainTextProcessing);
	}
	
}