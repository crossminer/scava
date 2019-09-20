package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupsMigrationIssueHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupsMigrationData> dailyNewsgroupsMigrationData = null;
	
	
	public NewsgroupsMigrationIssueHistoricMetric() { 
		super();
		dbObject.put("dailyNewsgroupsMigrationData", new BasicDBList());
	}
	
	
	
	
	
	public List<DailyNewsgroupsMigrationData> getDailyNewsgroupsMigrationData() {
		if (dailyNewsgroupsMigrationData == null) {
			dailyNewsgroupsMigrationData = new PongoList<DailyNewsgroupsMigrationData>(this, "dailyNewsgroupsMigrationData", true);
		}
		return dailyNewsgroupsMigrationData;
	}
	
	
}