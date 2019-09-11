package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyNewsgroupsMigrationData extends Pongo {
	
	protected List<String> changes = null;
	
	
	public DailyNewsgroupsMigrationData() { 
		super();
		dbObject.put("changes", new BasicDBList());
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData");
		THREADID.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData");
		NUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData");
		CUMULATIVENUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData");
		SOFTWARE.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer THREADID = new NumericalQueryProducer("threadId");
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer CUMULATIVENUMBEROFISSUES = new NumericalQueryProducer("cumulativeNumberOfIssues");
	public static StringQueryProducer SOFTWARE = new StringQueryProducer("software"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public DailyNewsgroupsMigrationData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getThreadId() {
		return parseInteger(dbObject.get("threadId")+"", 0);
	}
	
	public DailyNewsgroupsMigrationData setThreadId(int threadId) {
		dbObject.put("threadId", threadId);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public DailyNewsgroupsMigrationData setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfIssues() {
		return parseInteger(dbObject.get("cumulativeNumberOfIssues")+"", 0);
	}
	
	public DailyNewsgroupsMigrationData setCumulativeNumberOfIssues(int cumulativeNumberOfIssues) {
		dbObject.put("cumulativeNumberOfIssues", cumulativeNumberOfIssues);
		notifyChanged();
		return this;
	}
	public String getSoftware() {
		return parseString(dbObject.get("software")+"", "");
	}
	
	public DailyNewsgroupsMigrationData setSoftware(String software) {
		dbObject.put("software", software);
		notifyChanged();
		return this;
	}
	
	public List<String> getChanges() {
		if (changes == null) {
			changes = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("changes"));
		}
		return changes;
	}
	
	
	
}