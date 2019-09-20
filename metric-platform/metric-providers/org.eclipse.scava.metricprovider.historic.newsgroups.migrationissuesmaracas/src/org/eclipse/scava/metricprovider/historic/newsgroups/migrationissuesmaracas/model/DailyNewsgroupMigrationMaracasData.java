package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyNewsgroupMigrationMaracasData extends Pongo {
	
	protected List<Integer> threadsId = null;
	
	
	public DailyNewsgroupMigrationMaracasData() { 
		super();
		dbObject.put("threadsId", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.DailyNewsgroupMigrationMaracasData");
		THREADSID.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.DailyNewsgroupMigrationMaracasData");
		NUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.DailyNewsgroupMigrationMaracasData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static ArrayQueryProducer THREADSID = new ArrayQueryProducer("threadsId");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public DailyNewsgroupMigrationMaracasData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public DailyNewsgroupMigrationMaracasData setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	
	public List<Integer> getThreadsId() {
		if (threadsId == null) {
			threadsId = new PrimitiveList<Integer>(this, (BasicDBList) dbObject.get("threadsId"));
		}
		return threadsId;
	}
	
	
	
}