package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class CommitsMessageTopicsTransMetric extends PongoDB {
	
	public CommitsMessageTopicsTransMetric() {}
	
	public CommitsMessageTopicsTransMetric(DB db) {
		setDb(db);
	}
	
	protected CommitMessageCollection commitsMessages = null;
	protected CommitsTopicCollection commitsTopics = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public CommitMessageCollection getCommitsMessages() {
		return commitsMessages;
	}
	
	public CommitsTopicCollection getCommitsTopics() {
		return commitsTopics;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		commitsMessages = new CommitMessageCollection(db.getCollection("CommitsMessageTopicsTransMetric.commitsMessages"));
		pongoCollections.add(commitsMessages);
		commitsTopics = new CommitsTopicCollection(db.getCollection("CommitsMessageTopicsTransMetric.commitsTopics"));
		pongoCollections.add(commitsTopics);
	}
}