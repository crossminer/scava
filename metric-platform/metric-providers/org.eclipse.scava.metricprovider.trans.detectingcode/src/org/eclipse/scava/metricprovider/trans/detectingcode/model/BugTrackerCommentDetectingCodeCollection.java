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