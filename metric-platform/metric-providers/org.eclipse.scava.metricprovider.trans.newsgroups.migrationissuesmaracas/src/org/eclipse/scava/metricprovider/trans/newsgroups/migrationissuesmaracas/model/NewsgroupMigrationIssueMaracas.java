package org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


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
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
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
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public NewsgroupMigrationIssueMaracas setThreadId(int threadId) {
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