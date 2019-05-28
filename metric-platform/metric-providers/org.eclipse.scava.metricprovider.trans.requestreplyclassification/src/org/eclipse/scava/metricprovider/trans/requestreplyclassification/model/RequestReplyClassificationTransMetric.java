package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class RequestReplyClassificationTransMetric extends PongoDB {
	
	public RequestReplyClassificationTransMetric() {}
	
	public RequestReplyClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentsCollection bugTrackerComments = null;
	protected NewsgroupArticlesCollection newsgroupArticles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentsCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticlesCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentsCollection(db.getCollection("RequestReplyClassificationTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticlesCollection(db.getCollection("RequestReplyClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
	}
}