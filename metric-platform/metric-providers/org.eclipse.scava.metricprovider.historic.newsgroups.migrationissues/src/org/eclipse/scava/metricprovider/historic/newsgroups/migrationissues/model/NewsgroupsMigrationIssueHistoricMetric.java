package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsMigrationIssueHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupsMigrationData> dailyNewsgroupsMigrationData = null;
	
	
	public NewsgroupsMigrationIssueHistoricMetric() { 
		super();
		dbObject.put("dailyNewsgroupsMigrationData", new BasicDBList());
		NUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.NewsgroupsMigrationIssueHistoricMetric");
		CUMULATIVENUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.NewsgroupsMigrationIssueHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer CUMULATIVENUMBEROFISSUES = new NumericalQueryProducer("cumulativeNumberOfIssues");
	
	
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public NewsgroupsMigrationIssueHistoricMetric setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfIssues() {
		return parseInteger(dbObject.get("cumulativeNumberOfIssues")+"", 0);
	}
	
	public NewsgroupsMigrationIssueHistoricMetric setCumulativeNumberOfIssues(int cumulativeNumberOfIssues) {
		dbObject.put("cumulativeNumberOfIssues", cumulativeNumberOfIssues);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyNewsgroupsMigrationData> getDailyNewsgroupsMigrationData() {
		if (dailyNewsgroupsMigrationData == null) {
			dailyNewsgroupsMigrationData = new PongoList<DailyNewsgroupsMigrationData>(this, "dailyNewsgroupsMigrationData", true);
		}
		return dailyNewsgroupsMigrationData;
	}
	
	
}