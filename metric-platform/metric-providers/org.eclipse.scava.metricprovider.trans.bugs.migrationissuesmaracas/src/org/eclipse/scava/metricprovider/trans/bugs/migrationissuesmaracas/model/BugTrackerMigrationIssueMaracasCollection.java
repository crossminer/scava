package org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class BugTrackerMigrationIssueMaracasCollection extends PongoCollection<BugTrackerMigrationIssueMaracas> {
	
	public BugTrackerMigrationIssueMaracasCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("bugTrackerId");
	}
	
	public Iterable<BugTrackerMigrationIssueMaracas> findById(String id) {
		return new IteratorIterable<BugTrackerMigrationIssueMaracas>(new PongoCursorIterator<BugTrackerMigrationIssueMaracas>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<BugTrackerMigrationIssueMaracas> findByBugTrackerId(String q) {
		return new IteratorIterable<BugTrackerMigrationIssueMaracas>(new PongoCursorIterator<BugTrackerMigrationIssueMaracas>(this, dbCollection.find(new BasicDBObject("bugTrackerId", q + ""))));
	}
	
	public BugTrackerMigrationIssueMaracas findOneByBugTrackerId(String q) {
		BugTrackerMigrationIssueMaracas bugTrackerMigrationIssueMaracas = (BugTrackerMigrationIssueMaracas) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("bugTrackerId", q + "")));
		if (bugTrackerMigrationIssueMaracas != null) {
			bugTrackerMigrationIssueMaracas.setPongoCollection(this);
		}
		return bugTrackerMigrationIssueMaracas;
	}
	

	public long countByBugTrackerId(String q) {
		return dbCollection.count(new BasicDBObject("bugTrackerId", q + ""));
	}
	
	@Override
	public Iterator<BugTrackerMigrationIssueMaracas> iterator() {
		return new PongoCursorIterator<BugTrackerMigrationIssueMaracas>(this, dbCollection.find());
	}
	
	public void add(BugTrackerMigrationIssueMaracas bugTrackerMigrationIssueMaracas) {
		super.add(bugTrackerMigrationIssueMaracas);
	}
	
	public void remove(BugTrackerMigrationIssueMaracas bugTrackerMigrationIssueMaracas) {
		super.remove(bugTrackerMigrationIssueMaracas);
	}
	
}