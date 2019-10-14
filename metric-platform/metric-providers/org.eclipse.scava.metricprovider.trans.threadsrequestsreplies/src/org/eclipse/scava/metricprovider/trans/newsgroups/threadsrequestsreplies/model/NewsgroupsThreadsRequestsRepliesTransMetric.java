package org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsThreadsRequestsRepliesTransMetric extends PongoDB {
	
	public NewsgroupsThreadsRequestsRepliesTransMetric() {}
	
	public NewsgroupsThreadsRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected ThreadStatisticsCollection threads = null;
	
	
	
	public ThreadStatisticsCollection getThreads() {
		return threads;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		threads = new ThreadStatisticsCollection(db.getCollection("NewsgroupsThreadsRequestsRepliesTransMetric.threads"));
		pongoCollections.add(threads);
	}
}