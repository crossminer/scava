package org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugTrackerMigrationIssueMaracasTransMetric extends PongoDB {
	
	public BugTrackerMigrationIssueMaracasTransMetric() {}
	
	public BugTrackerMigrationIssueMaracasTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerMigrationIssueMaracasCollection bugTrackerMigrationIssuesMaracas = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerMigrationIssueMaracasCollection getBugTrackerMigrationIssuesMaracas() {
		return bugTrackerMigrationIssuesMaracas;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerMigrationIssuesMaracas = new BugTrackerMigrationIssueMaracasCollection(db.getCollection("BugTrackerMigrationIssueMaracasTransMetric.bugTrackerMigrationIssuesMaracas"));
		pongoCollections.add(bugTrackerMigrationIssuesMaracas);
	}
}