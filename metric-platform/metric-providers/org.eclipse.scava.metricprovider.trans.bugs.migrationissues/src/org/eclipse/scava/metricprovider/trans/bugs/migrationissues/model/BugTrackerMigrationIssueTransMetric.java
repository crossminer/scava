package org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugTrackerMigrationIssueTransMetric extends PongoDB {
	
	public BugTrackerMigrationIssueTransMetric() {}
	
	public BugTrackerMigrationIssueTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerMigrationIssueCollection bugTrackerMigrationIssues = null;
	
	
	
	public BugTrackerMigrationIssueCollection getBugTrackerMigrationIssues() {
		return bugTrackerMigrationIssues;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerMigrationIssues = new BugTrackerMigrationIssueCollection(db.getCollection("BugTrackerMigrationIssueTransMetric.bugTrackerMigrationIssues"));
		pongoCollections.add(bugTrackerMigrationIssues);
	}
}