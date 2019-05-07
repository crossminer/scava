package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class SeverityClassificationTransMetric extends PongoDB {
	
	public SeverityClassificationTransMetric() {}
	
	public SeverityClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerBugsDataCollection bugTrackerBugs = null;
	protected NewsgroupArticleDataCollection newsgroupArticles = null;
	protected NewsgroupThreadDataCollection newsgroupThreads = null;
	protected ForumPostDataCollection forumPosts = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerBugsDataCollection getBugTrackerBugs() {
		return bugTrackerBugs;
	}
	
	public NewsgroupArticleDataCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public NewsgroupThreadDataCollection getNewsgroupThreads() {
		return newsgroupThreads;
	}
	
	public ForumPostDataCollection getForumPosts() {
		return forumPosts;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerBugs = new BugTrackerBugsDataCollection(db.getCollection("SeverityClassificationTransMetric.bugTrackerBugs"));
		pongoCollections.add(bugTrackerBugs);
		newsgroupArticles = new NewsgroupArticleDataCollection(db.getCollection("SeverityClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		newsgroupThreads = new NewsgroupThreadDataCollection(db.getCollection("SeverityClassificationTransMetric.newsgroupThreads"));
		pongoCollections.add(newsgroupThreads);
		forumPosts = new ForumPostDataCollection(db.getCollection("SeverityClassificationTransMetric.forumPosts"));
		pongoCollections.add(forumPosts);
	}
}