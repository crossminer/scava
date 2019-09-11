package org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DailyBugTrackerMigrationData extends Pongo {
	
	protected List<String> changes = null;
	
	
	public DailyBugTrackerMigrationData() { 
		super();
		dbObject.put("changes", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData");
		NUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData");
		CUMULATIVENUMBEROFISSUES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData");
		SOFTWARE.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static NumericalQueryProducer NUMBEROFISSUES = new NumericalQueryProducer("numberOfIssues");
	public static NumericalQueryProducer CUMULATIVENUMBEROFISSUES = new NumericalQueryProducer("cumulativeNumberOfIssues");
	public static StringQueryProducer SOFTWARE = new StringQueryProducer("software"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugTrackerMigrationData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public DailyBugTrackerMigrationData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public int getNumberOfIssues() {
		return parseInteger(dbObject.get("numberOfIssues")+"", 0);
	}
	
	public DailyBugTrackerMigrationData setNumberOfIssues(int numberOfIssues) {
		dbObject.put("numberOfIssues", numberOfIssues);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfIssues() {
		return parseInteger(dbObject.get("cumulativeNumberOfIssues")+"", 0);
	}
	
	public DailyBugTrackerMigrationData setCumulativeNumberOfIssues(int cumulativeNumberOfIssues) {
		dbObject.put("cumulativeNumberOfIssues", cumulativeNumberOfIssues);
		notifyChanged();
		return this;
	}
	public String getSoftware() {
		return parseString(dbObject.get("software")+"", "");
	}
	
	public DailyBugTrackerMigrationData setSoftware(String software) {
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