package org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugTrackerMigrationIssueHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackerMigrationData> dailyBugTrackerMigrationData = null;
	
	
	public BugTrackerMigrationIssueHistoricMetric() { 
		super();
		dbObject.put("dailyBugTrackerMigrationData", new BasicDBList());
	}
	
	
	
	
	
	public List<DailyBugTrackerMigrationData> getDailyBugTrackerMigrationData() {
		if (dailyBugTrackerMigrationData == null) {
			dailyBugTrackerMigrationData = new PongoList<DailyBugTrackerMigrationData>(this, "dailyBugTrackerMigrationData", true);
		}
		return dailyBugTrackerMigrationData;
	}
	
	
}