package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerBugsDataCollection extends PongoCollection<BugTrackerBugsData> {
	
	public BugTrackerBugsDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerBugsData> findById(String id) {
		return new IteratorIterable<BugTrackerBugsData>(new PongoCursorIterator<BugTrackerBugsData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerBugsData> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerBugsData>(new PongoCursorIterator<BugTrackerBugsData>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerBugsData findOneByBugTrackerId(String q) {
		BugTrackerBugsData bugTrackerBugsData = (BugTrackerBugsData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerBugsData != null) {
			bugTrackerBugsData.setPongoCollection(this);
		}
		return bugTrackerBugsData;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerBugsData> iterator() {
		return new PongoCursorIterator<BugTrackerBugsData>(this, dbCollection.find());
	}
	
	public void add(BugTrackerBugsData bugTrackerBugsData) {
		super.add(bugTrackerBugsData);
	}
	
	public void remove(BugTrackerBugsData bugTrackerBugsData) {
		super.remove(bugTrackerBugsData);
	}
	
}