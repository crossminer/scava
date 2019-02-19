package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class PlainTextProcessingTransMetric extends PongoDB {
	
	public PlainTextProcessingTransMetric() {}
	
	public PlainTextProcessingTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentPlainTextProcessingCollection bugTrackerComments = null;
	protected NewsgroupArticlePlainTextProcessingCollection newsgroupArticles = null;
	protected ForumPostPlainTextProcessingCollection forumPosts = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentPlainTextProcessingCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticlePlainTextProcessingCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public ForumPostPlainTextProcessingCollection getForumPosts() {
		return forumPosts;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentPlainTextProcessingCollection(db.getCollection("PlainTextProcessingTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticlePlainTextProcessingCollection(db.getCollection("PlainTextProcessingTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		forumPosts = new ForumPostPlainTextProcessingCollection(db.getCollection("PlainTextProcessingTransMetric.forumPosts"));
		pongoCollections.add(forumPosts);
	}
}