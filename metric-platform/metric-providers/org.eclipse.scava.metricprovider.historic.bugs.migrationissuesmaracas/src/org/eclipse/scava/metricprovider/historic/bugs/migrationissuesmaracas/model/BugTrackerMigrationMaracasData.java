package org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class BugTrackerMigrationMaracasData extends Pongo {
	
	protected List<String> changesAndMatchingPercentage = null;
	
	
	public BugTrackerMigrationMaracasData() { 
		super();
		dbObject.put("changesAndMatchingPercentage", new BasicDBList());
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.BugTrackerMigrationMaracasData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.BugTrackerMigrationMaracasData");
		CHANGESANDMATCHINGPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.BugTrackerMigrationMaracasData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static ArrayQueryProducer CHANGESANDMATCHINGPERCENTAGE = new ArrayQueryProducer("changesAndMatchingPercentage");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerMigrationMaracasData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugTrackerMigrationMaracasData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
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