package org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsRequestsRepliesTransMetric extends PongoDB {
	
	public BugsRequestsRepliesTransMetric() {}
	
	public BugsRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugStatisticsCollection bugs = null;
	
	
	
	public BugStatisticsCollection getBugs() {
		return bugs;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugs = new BugStatisticsCollection(db.getCollection("BugsRequestsRepliesTransMetric.bugs"));
		pongoCollections.add(bugs);
	}
}