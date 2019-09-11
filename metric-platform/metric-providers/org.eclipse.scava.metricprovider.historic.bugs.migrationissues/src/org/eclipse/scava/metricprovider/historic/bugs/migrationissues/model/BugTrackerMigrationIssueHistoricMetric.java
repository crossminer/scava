package org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerMigrationIssueHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackerMigrationData> dailyBugTrackerMigrationData = null;
	
	
	public BugTrackerMigrationIssueHistoricMetric() { 
		super();
		dbObject.put("dailyBugTrackerMigrationData", new BasicDBList());
		NUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.BugTrackerMigrationIssueHistoricMetric");
		CUMULATIVENUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.BugTrackerMigrationIssueHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer CUMULATIVENUMBEROFISSUES = new NumericalQueryProducer("cumulativeNumberOfIssues");
	
	
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public BugTrackerMigrationIssueHistoricMetric setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfIssues() {
		return parseInteger(dbObject.get("cumulativeNumberOfIssues")+"", 0);
	}
	
	public BugTrackerMigrationIssueHistoricMetric setCumulativeNumberOfIssues(int cumulativeNumberOfIssues) {
		dbObject.put("cumulativeNumberOfIssues", cumulativeNumberOfIssues);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugTrackerMigrationData> getDailyBugTrackerMigrationData() {
		if (dailyBugTrackerMigrationData == null) {
			dailyBugTrackerMigrationData = new PongoList<DailyBugTrackerMigrationData>(this, "dailyBugTrackerMigrationData", true);
		}
		return dailyBugTrackerMigrationData;
	}
	
	
}