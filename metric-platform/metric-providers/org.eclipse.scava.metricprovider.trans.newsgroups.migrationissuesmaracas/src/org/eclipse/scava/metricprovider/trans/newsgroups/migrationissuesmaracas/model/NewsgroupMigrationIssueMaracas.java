package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupMigrationIssueMaracas extends Pongo {
	
	protected List<String> changes = null;
	protected List<Double> matchingPercentage = null;
	
	
	public NewsgroupMigrationIssueMaracas() { 
		super();
		dbObject.put("changes", new BasicDBList());
		dbObject.put("matchingPercentage", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracas");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracas");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracas");
		MATCHINGPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracas");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer THREADID = new StringQueryProducer("threadId"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	public static ArrayQueryProducer MATCHINGPERCENTAGE = new ArrayQueryProducer("matchingPercentage");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupMigrationIssueMaracas setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getThreadId() {
		return parseString(dbObject.get("threadId")+"", "");
	}
	
	public NewsgroupMigrationIssueMaracas setThreadId(String threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	
	public List<String> getChanges() {
		if (changes == null) {
			changes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("changes"));
		}
		return changes;
	}
	public List<Double> getMatchingPercentage() {
		if (matchingPercentage == null) {
			matchingPercentage = new PrimitiveList<Double>(this, (BasicDBList) dbObject.get("matchingPercentage"));
		}
		return matchingPercentage;
	}
	
	
	
}