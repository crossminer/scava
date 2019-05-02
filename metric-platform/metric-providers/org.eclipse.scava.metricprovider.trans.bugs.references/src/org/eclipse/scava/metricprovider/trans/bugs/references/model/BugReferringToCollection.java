package org.eclipse.scava.metricprovider.trans.bugs.references.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugReferringToCollection extends PongoCollection<BugReferringTo> {
	
	public BugReferringToCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugReferringTo> findById(String id) {
		return new IteratorIterable<BugReferringTo>(new PongoCursorIterator<BugReferringTo>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugReferringTo> findByBugTrackerId(String q) {
		return new IteratorIterable<BugReferringTo>(new PongoCursorIterator<BugReferringTo>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugReferringTo findOneByBugTrackerId(String q) {
		BugReferringTo bugReferringTo = (BugReferringTo) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugReferringTo != null) {
			bugReferringTo.setPongoCollection(this);
		}
		return bugReferringTo;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugReferringTo> iterator() {
		return new PongoCursorIterator<BugReferringTo>(this, dbCollection.find());
	}
	
	public void add(BugReferringTo bugReferringTo) {
		super.add(bugReferringTo);
	}
	
	public void remove(BugReferringTo bugReferringTo) {
		super.remove(bugReferringTo);
	}
	
}