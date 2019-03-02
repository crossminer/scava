package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostSentimentClassificationCollection extends PongoCollection<ForumPostSentimentClassification> {
	
	public ForumPostSentimentClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostSentimentClassification> findById(String id) {
		return new IteratorIterable<ForumPostSentimentClassification>(new PongoCursorIterator<ForumPostSentimentClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
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