package org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugTrackerMigrationIssueMaracas extends Pongo {
	
	protected List<String> changes = null;
	protected List<Double> matchingPercentage = null;
	
	
	public BugTrackerMigrationIssueMaracas() { 
		super();
		dbObject.put("changes", new BasicDBList());
		dbObject.put("matchingPercentage", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracas");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracas");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracas");
		MATCHINGPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracas");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	public static ArrayQueryProducer MATCHINGPERCENTAGE = new ArrayQueryProducer("matchingPercentage");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerMigrationIssueMaracas setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerMigrationIssueMaracas setBugId(String bugId) {
		dbObject.put("bugId", bugId);
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