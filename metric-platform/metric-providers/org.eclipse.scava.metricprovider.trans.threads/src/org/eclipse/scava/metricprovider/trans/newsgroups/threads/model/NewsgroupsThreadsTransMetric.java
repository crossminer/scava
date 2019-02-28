package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class NewsgroupsThreadsTransMetric extends PongoDB {
	
	public NewsgroupsThreadsTransMetric() {}
	
	public NewsgroupsThreadsTransMetric(DB db) {
		setDb(db);
	}
	
	protected ThreadDataCollection threads = null;
	protected NewsgroupDataCollection newsgroups = null;
	protected CurrentDateCollection date = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public ThreadDataCollection getThreads() {
		return threads;
	}
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public CurrentDateCollection getDate() {
		return date;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		threads = new ThreadDataCollection(db.getCollection("NewsgroupsThreadsTransMetric.threads"));
		pongoCollections.add(threads);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsThreadsTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
		date = new CurrentDateCollection(db.getCollection("NewsgroupsThreadsTransMetric.date"));
		pongoCollections.add(date);
	}
}