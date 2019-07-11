package org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class BugTrackerMigrationIssue extends Pongo {
	
	protected List<String> changes = null;
	
	
	public BugTrackerMigrationIssue() { 
		super();
		dbObject.put("changes", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
		SOFTWARE.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
		CHANGES.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer SOFTWARE = new StringQueryProducer("software"); 
	public static ArrayQueryProducer CHANGES = new ArrayQueryProducer("changes");
	
	
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
	public String getSoftware() {
		return parseString(dbObject.get("software")+"", "");
	}
	
	public BugTrackerMigrationIssue setSoftware(String software) {
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