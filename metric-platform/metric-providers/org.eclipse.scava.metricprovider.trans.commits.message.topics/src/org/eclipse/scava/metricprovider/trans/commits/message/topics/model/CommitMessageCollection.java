package org.eclipse.scava.metricprovider.trans.commits.message.topics.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class CommitMessageCollection extends PongoCollection<CommitMessage> {
	
	public CommitMessageCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("repository");
	}
	
	public Iterable<CommitMessage> findById(String id) {
		return new IteratorIterable<CommitMessage>(new PongoCursorIterator<CommitMessage>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<CommitMessage> findByRepository(String q) {
		return new IteratorIterable<CommitMessage>(new PongoCursorIterator<CommitMessage>(this, dbCollection.find(new BasicDBObject("repository", q + ""))));
	}
	
	public CommitMessage findOneByRepository(String q) {
		CommitMessage commitMessage = (CommitMessage) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("repository", q + "")));
		if (commitMessage != null) {
			commitMessage.setPongoCollection(this);
		}
		return commitMessage;
	}
	

	public long countByRepository(String q) {
		return dbCollection.count(new BasicDBObject("repository", q + ""));
	}
	
	@Override
	public Iterator<CommitMessage> iterator() {
		return new PongoCursorIterator<CommitMessage>(this, dbCollection.find());
	}
	
	public void add(CommitMessage commitMessage) {
		super.add(commitMessage);
	}
	
	public void remove(CommitMessage commitMessage) {
		super.remove(commitMessage);
	}
	
}