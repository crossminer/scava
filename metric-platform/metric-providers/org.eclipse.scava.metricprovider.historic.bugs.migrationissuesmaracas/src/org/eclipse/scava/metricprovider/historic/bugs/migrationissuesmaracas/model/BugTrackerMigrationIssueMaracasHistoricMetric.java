package org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugTrackerMigrationIssueMaracasHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackerMigrationMaracasData> dailyBugTrackerMigrationMaracasData = null;
	protected List<BugTrackerMigrationMaracasData> bugTrackerMigrationMaracasData = null;
	
	
	public BugTrackerMigrationIssueMaracasHistoricMetric() { 
		super();
		dbObject.put("dailyBugTrackerMigrationMaracasData", new BasicDBList());
		dbObject.put("bugTrackerMigrationMaracasData", new BasicDBList());
	}
	
	
	
	
	
	public List<DailyBugTrackerMigrationMaracasData> getDailyBugTrackerMigrationMaracasData() {
		if (dailyBugTrackerMigrationMaracasData == null) {
			dailyBugTrackerMigrationMaracasData = new PongoList<DailyBugTrackerMigrationMaracasData>(this, "dailyBugTrackerMigrationMaracasData", true);
		}
		return dailyBugTrackerMigrationMaracasData;
	}
	public List<BugTrackerMigrationMaracasData> getBugTrackerMigrationMaracasData() {
		if (bugTrackerMigrationMaracasData == null) {
			bugTrackerMigrationMaracasData = new PongoList<BugTrackerMigrationMaracasData>(this, "bugTrackerMigrationMaracasData", true);
		}
		return bugTrackerMigrationMaracasData;
	}
	
	
}