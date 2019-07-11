package org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class BugTrackerMigrationIssueCollection extends PongoCollection<BugTrackerMigrationIssue> {
	
	public BugTrackerMigrationIssueCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerMigrationIssue> findById(String id) {
		return new IteratorIterable<BugTrackerMigrationIssue>(new PongoCursorIterator<BugTrackerMigrationIssue>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerMigrationIssue> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerMigrationIssue>(new PongoCursorIterator<BugTrackerMigrationIssue>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerMigrationIssue findOneByBugTrackerId(String q) {
		BugTrackerMigrationIssue bugTrackerMigrationIssue = (BugTrackerMigrationIssue) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerMigrationIssue != null) {
			bugTrackerMigrationIssue.setPongoCollection(this);
		}
		return bugTrackerMigrationIssue;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerMigrationIssue> iterator() {
		return new PongoCursorIterator<BugTrackerMigrationIssue>(this, dbCollection.find());
	}
	
	public void add(BugTrackerMigrationIssue bugTrackerMigrationIssue) {
		super.add(bugTrackerMigrationIssue);
	}
	
	public void remove(BugTrackerMigrationIssue bugTrackerMigrationIssue) {
		super.remove(bugTrackerMigrationIssue);
	}
	
}