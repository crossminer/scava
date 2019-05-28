package org.eclipse.scava.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class TopicsTransMetric extends PongoDB {
	
	public TopicsTransMetric() {}
	
	public TopicsTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentsDataCollection bugTrackerComments = null;
	protected BugTrackerTopicCollection bugTrackerTopics = null;
	protected NewsgroupArticlesDataCollection newsgroupArticles = null;
	protected NewsgroupTopicCollection newsgroupTopics = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentsDataCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public BugTrackerTopicCollection getBugTrackerTopics() {
		return bugTrackerTopics;
	}
	
	public NewsgroupArticlesDataCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public NewsgroupTopicCollection getNewsgroupTopics() {
		return newsgroupTopics;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentsDataCollection(db.getCollection("TopicsTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		bugTrackerTopics = new BugTrackerTopicCollection(db.getCollection("TopicsTransMetric.bugTrackerTopics"));
		pongoCollections.add(bugTrackerTopics);
		newsgroupArticles = new NewsgroupArticlesDataCollection(db.getCollection("TopicsTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		newsgroupTopics = new NewsgroupTopicCollection(db.getCollection("TopicsTransMetric.newsgroupTopics"));
		pongoCollections.add(newsgroupTopics);
	}
}