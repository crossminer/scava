package org.eclipse.scava.metricprovider.trans.bugs.references.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsReferenceTransMetric extends PongoDB {
	
	public BugsReferenceTransMetric() {}
	
	public BugsReferenceTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugReferringToCollection bugsReferringTo = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugReferringToCollection getBugsReferringTo() {
		return bugsReferringTo;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugsReferringTo = new BugReferringToCollection(db.getCollection("BugsReferenceTransMetric.bugsReferringTo"));
		pongoCollections.add(bugsReferringTo);
	}
}