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
	protected ForumsPostsCollection forumPosts = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentsCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticlesCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public ForumsPostsCollection getForumPosts() {
		return forumPosts;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentsCollection(db.getCollection("RequestReplyClassificationTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticlesCollection(db.getCollection("RequestReplyClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		forumPosts = new ForumsPostsCollection(db.getCollection("RequestReplyClassificationTransMetric.forumPosts"));
		pongoCollections.add(forumPosts);
	}
}