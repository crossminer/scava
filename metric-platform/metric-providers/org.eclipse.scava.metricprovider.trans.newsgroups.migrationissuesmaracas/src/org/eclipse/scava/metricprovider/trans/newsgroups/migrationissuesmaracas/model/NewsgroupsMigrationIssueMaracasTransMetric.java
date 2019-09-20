package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model;

import com.googlecode.pongo.runtime.PongoDB;
// protected region custom-imports on begin
// protected region custom-imports end
import com.mongodb.DB;

public class NewsgroupsMigrationIssueMaracasTransMetric extends PongoDB {
	
	public NewsgroupsMigrationIssueMaracasTransMetric() {}
	
	public NewsgroupsMigrationIssueMaracasTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupMigrationIssueMaracasCollection newsgroupsMigrationIssuesMaracas = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public NewsgroupMigrationIssueMaracasCollection getNewsgroupsMigrationIssuesMaracas() {
		return newsgroupsMigrationIssuesMaracas;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroupsMigrationIssuesMaracas = new NewsgroupMigrationIssueMaracasCollection(db.getCollection("NewsgroupsMigrationIssueMaracasTransMetric.newsgroupsMigrationIssuesMaracas"));
		pongoCollections.add(newsgroupsMigrationIssuesMaracas);
	}
}