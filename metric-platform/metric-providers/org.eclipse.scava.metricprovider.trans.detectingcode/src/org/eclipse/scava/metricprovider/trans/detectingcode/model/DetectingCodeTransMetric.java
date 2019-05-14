package org.eclipse.scava.metricprovider.trans.detectingcode.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DetectingCodeTransMetric extends PongoDB {
	
	public DetectingCodeTransMetric() {}
	
	public DetectingCodeTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentDetectingCodeCollection bugTrackerComments = null;
	protected NewsgroupArticleDetectingCodeCollection newsgroupArticles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentDetectingCodeCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticleDetectingCodeCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentDetectingCodeCollection(db.getCollection("DetectingCodeTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticleDetectingCodeCollection(db.getCollection("DetectingCodeTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
	}
}