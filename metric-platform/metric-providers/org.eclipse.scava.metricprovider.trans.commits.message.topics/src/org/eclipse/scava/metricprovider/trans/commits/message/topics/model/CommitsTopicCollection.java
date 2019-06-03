package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommitsTopicCollection extends PongoCollection<CommitsTopic> {
	
	public CommitsTopicCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("repository");
	}
	
	public Iterable<CommitsTopic> findById(String id) {
		return new IteratorIterable<CommitsTopic>(new PongoCursorIterator<CommitsTopic>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommitsTopic> findByRepository(String q) {
		return new IteratorIterable<CommitsTopic>(new PongoCursorIterator<CommitsTopic>(this, dbCollection.find(new BasicDBObject("repository", q + ""))));
	}
	
	public CommitsTopic findOneByRepository(String q) {
		CommitsTopic commitsTopic = (CommitsTopic) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("repository", q + "")));
		if (commitsTopic != null) {
			commitsTopic.setPongoCollection(this);
		}
		return commitsTopic;
	}
	

	public long countByRepository(String q) {
		return dbCollection.count(new BasicDBObject("repository", q + ""));
	}
	
	@Override
	public Iterator<CommitsTopic> iterator() {
		return new PongoCursorIterator<CommitsTopic>(this, dbCollection.find());
	}
	
	public void add(CommitsTopic commitsTopic) {
		super.add(commitsTopic);
	}
	
	public void remove(CommitsTopic commitsTopic) {
		super.remove(commitsTopic);
	}
	
}