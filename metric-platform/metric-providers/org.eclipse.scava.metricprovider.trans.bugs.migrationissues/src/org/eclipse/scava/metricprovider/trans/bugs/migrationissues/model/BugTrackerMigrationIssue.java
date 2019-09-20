package org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerMigrationIssue extends Pongo {
	
	
	
	public BugTrackerMigrationIssue() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
		SUMMARY.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer SUMMARY = new StringQueryProducer("summary"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerMigrationIssue setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerMigrationIssue setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getSummary() {
		return parseString(dbObject.get("summary")+"", "");
	}
	
	public BugTrackerMigrationIssue setSummary(String summary) {
		dbObject.put("summary", summary);
		notifyChanged();
		return this;
	}
	
	
	
	
}