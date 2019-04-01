package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerCommentsSentimentClassificationCollection extends PongoCollection<BugTrackerCommentsSentimentClassification> {
	
	public BugTrackerCommentsSentimentClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerCommentsSentimentClassification> findById(String id) {
		return new IteratorIterable<BugTrackerCommentsSentimentClassification>(new PongoCursorIterator<BugTrackerCommentsSentimentClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerCommentsSentimentClassification> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerCommentsSentimentClassification>(new PongoCursorIterator<BugTrackerCommentsSentimentClassification>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerCommentsSentimentClassification findOneByBugTrackerId(String q) {
		BugTrackerCommentsSentimentClassification bugTrackerCommentsSentimentClassification = (BugTrackerCommentsSentimentClassification) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerCommentsSentimentClassification != null) {
			bugTrackerCommentsSentimentClassification.setPongoCollection(this);
		}
		return bugTrackerCommentsSentimentClassification;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerCommentsSentimentClassification> iterator() {
		return new PongoCursorIterator<BugTrackerCommentsSentimentClassification>(this, dbCollection.find());
	}
	
	public void add(BugTrackerCommentsSentimentClassification bugTrackerCommentsSentimentClassification) {
		super.add(bugTrackerCommentsSentimentClassification);
	}
	
	public void remove(BugTrackerCommentsSentimentClassification bugTrackerCommentsSentimentClassification) {
		super.remove(bugTrackerCommentsSentimentClassification);
	}
	
}