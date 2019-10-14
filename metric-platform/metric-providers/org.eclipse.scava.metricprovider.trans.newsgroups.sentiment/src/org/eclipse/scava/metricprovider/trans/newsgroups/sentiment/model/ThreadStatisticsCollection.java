package org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ThreadStatisticsCollection extends PongoCollection<ThreadStatistics> {
	
	public ThreadStatisticsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
		createIndex("threadId");
	}
	
	public Iterable<ThreadStatistics> findById(String id) {
		return new IteratorIterable<ThreadStatistics>(new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ThreadStatistics> findByNewsgroupName(String q) {
		return new IteratorIterable<ThreadStatistics>(new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public ThreadStatistics findOneByNewsgroupName(String q) {
		ThreadStatistics threadStatistics = (ThreadStatistics) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (threadStatistics != null) {
			threadStatistics.setPongoCollection(this);
		}
		return threadStatistics;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	public Iterable<ThreadStatistics> findByThreadId(String q) {
		return new IteratorIterable<ThreadStatistics>(new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find(new BasicDBObject("threadId", q + ""))));
	}
	
	public ThreadStatistics findOneByThreadId(String q) {
		ThreadStatistics threadStatistics = (ThreadStatistics) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("threadId", q + "")));
		if (threadStatistics != null) {
			threadStatistics.setPongoCollection(this);
		}
		return threadStatistics;
	}
	

	public long countByThreadId(String q) {
		return dbCollection.count(new BasicDBObject("threadId", q + ""));
	}
	
	@Override
	public Iterator<ThreadStatistics> iterator() {
		return new PongoCursorIterator<ThreadStatistics>(this, dbCollection.find());
	}
	
	public void add(ThreadStatistics threadStatistics) {
		super.add(threadStatistics);
	}
	
	public void remove(ThreadStatistics threadStatistics) {
		super.remove(threadStatistics);
	}
	
}