package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostSentimentClassificationCollection extends PongoCollection<ForumPostSentimentClassification> {
	
	public ForumPostSentimentClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumPostSentimentClassification> findById(String id) {
		return new IteratorIterable<ForumPostSentimentClassification>(new PongoCursorIterator<ForumPostSentimentClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumPostSentimentClassification> findByForumId(String q) {
		return new IteratorIterable<ForumPostSentimentClassification>(new PongoCursorIterator<ForumPostSentimentClassification>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumPostSentimentClassification findOneByForumId(String q) {
		ForumPostSentimentClassification forumPostSentimentClassification = (ForumPostSentimentClassification) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumPostSentimentClassification != null) {
			forumPostSentimentClassification.setPongoCollection(this);
		}
		return forumPostSentimentClassification;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumPostSentimentClassification> iterator() {
		return new PongoCursorIterator<ForumPostSentimentClassification>(this, dbCollection.find());
	}
	
	public void add(ForumPostSentimentClassification forumPostSentimentClassification) {
		super.add(forumPostSentimentClassification);
	}
	
	public void remove(ForumPostSentimentClassification forumPostSentimentClassification) {
		super.remove(forumPostSentimentClassification);
	}
	
}