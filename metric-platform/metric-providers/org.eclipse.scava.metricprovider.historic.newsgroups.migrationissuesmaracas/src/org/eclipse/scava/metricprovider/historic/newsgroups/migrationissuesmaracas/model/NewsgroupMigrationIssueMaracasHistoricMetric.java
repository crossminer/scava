package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupMigrationIssueMaracasHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupMigrationMaracasData> dailyNewsgroupMigrationMaracasData = null;
	protected List<NewsgroupMigrationMaracasData> newsgroupMigrationMaracasData = null;
	
	
	public NewsgroupMigrationIssueMaracasHistoricMetric() { 
		super();
		dbObject.put("dailyNewsgroupMigrationMaracasData", new BasicDBList());
		dbObject.put("newsgroupMigrationMaracasData", new BasicDBList());
	}
	
	
	
	
	
	public List<DailyNewsgroupMigrationMaracasData> getDailyNewsgroupMigrationMaracasData() {
		if (dailyNewsgroupMigrationMaracasData == null) {
			dailyNewsgroupMigrationMaracasData = new PongoList<DailyNewsgroupMigrationMaracasData>(this, "dailyNewsgroupMigrationMaracasData", true);
		}
		return dailyNewsgroupMigrationMaracasData;
	}
	public List<NewsgroupMigrationMaracasData> getNewsgroupMigrationMaracasData() {
		if (newsgroupMigrationMaracasData == null) {
			newsgroupMigrationMaracasData = new PongoList<NewsgroupMigrationMaracasData>(this, "newsgroupMigrationMaracasData", true);
		}
		return newsgroupMigrationMaracasData;
	}
	
	
}