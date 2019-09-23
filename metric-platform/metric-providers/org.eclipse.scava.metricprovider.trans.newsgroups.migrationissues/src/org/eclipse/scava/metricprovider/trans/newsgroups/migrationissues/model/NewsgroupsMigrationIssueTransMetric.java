package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsMigrationIssueTransMetric extends PongoDB {
	
	public NewsgroupsMigrationIssueTransMetric() {}
	
	public NewsgroupsMigrationIssueTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupsMigrationIssueCollection newsgroupsMigrationIssues = null;
	
	
	
	public NewsgroupsMigrationIssueCollection getNewsgroupsMigrationIssues() {
		return newsgroupsMigrationIssues;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroupsMigrationIssues = new NewsgroupsMigrationIssueCollection(db.getCollection("NewsgroupsMigrationIssueTransMetric.newsgroupsMigrationIssues"));
		pongoCollections.add(newsgroupsMigrationIssues);
	}
}