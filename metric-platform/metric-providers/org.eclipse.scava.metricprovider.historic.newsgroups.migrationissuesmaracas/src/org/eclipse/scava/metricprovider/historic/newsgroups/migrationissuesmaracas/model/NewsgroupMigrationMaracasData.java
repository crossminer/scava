package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupMigrationMaracasData extends Pongo {
	
	protected List<String> changesAndMatchingPercentage = null;
	
	
	public NewsgroupMigrationMaracasData() { 
		super();
		dbObject.put("changesAndMatchingPercentage", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationMaracasData");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationMaracasData");
		CHANGESANDMATCHINGPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationMaracasData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer THREADID = new StringQueryProducer("threadId"); 
	public static ArrayQueryProducer CHANGESANDMATCHINGPERCENTAGE = new ArrayQueryProducer("changesAndMatchingPercentage");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupMigrationMaracasData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getThreadId() {
		return parseString(dbObject.get("threadId")+"", "");
	}
	
	public NewsgroupMigrationMaracasData setThreadId(String threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	
	public List<String> getChangesAndMatchingPercentage() {
		if (changesAndMatchingPercentage == null) {
			changesAndMatchingPercentage = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("changesAndMatchingPercentage"));
		}
		return changesAndMatchingPercentage;
	}
	
	
	
}