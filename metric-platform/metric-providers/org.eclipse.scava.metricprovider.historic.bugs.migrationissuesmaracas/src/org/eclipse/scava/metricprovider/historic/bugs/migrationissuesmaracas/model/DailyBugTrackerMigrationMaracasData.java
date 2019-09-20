package org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class DailyBugTrackerMigrationMaracasData extends Pongo {
	
	protected List<String> bugsId = null;
	
	
	public DailyBugTrackerMigrationMaracasData() { 
		super();
		dbObject.put("bugsId", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.DailyBugTrackerMigrationMaracasData");
		BUGSID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.DailyBugTrackerMigrationMaracasData");
		NUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.DailyBugTrackerMigrationMaracasData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static ArrayQueryProducer BUGSID = new ArrayQueryProducer("bugsId");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugTrackerMigrationMaracasData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public DailyBugTrackerMigrationMaracasData setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	
	public List<String> getBugsId() {
		if (bugsId == null) {
			bugsId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("bugsId"));
		}
		return bugsId;
	}
	
	
	
}