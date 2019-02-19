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