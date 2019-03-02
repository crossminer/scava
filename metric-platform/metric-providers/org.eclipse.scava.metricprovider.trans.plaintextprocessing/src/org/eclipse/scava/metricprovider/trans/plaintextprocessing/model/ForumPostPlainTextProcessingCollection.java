package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostPlainTextProcessingCollection extends PongoCollection<ForumPostPlainTextProcessing> {
	
	public ForumPostPlainTextProcessingCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumPostPlainTextProcessing> findById(String id) {
		return new IteratorIterable<ForumPostPlainTextProcessing>(new PongoCursorIterator<ForumPostPlainTextProcessing>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumPostPlainTextProcessing> findByForumId(String q) {
		return new IteratorIterable<ForumPostPlainTextProcessing>(new PongoCursorIterator<ForumPostPlainTextProcessing>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumPostPlainTextProcessing findOneByForumId(String q) {
		ForumPostPlainTextProcessing forumPostPlainTextProcessing = (ForumPostPlainTextProcessing) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumPostPlainTextProcessing != null) {
			forumPostPlainTextProcessing.setPongoCollection(this);
		}
		return forumPostPlainTextProcessing;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumPostPlainTextProcessing> iterator() {
		return new PongoCursorIterator<ForumPostPlainTextProcessing>(this, dbCollection.find());
	}
	
	public void add(ForumPostPlainTextProcessing forumPostPlainTextProcessing) {
		super.add(forumPostPlainTextProcessing);
	}
	
	public void remove(ForumPostPlainTextProcessing forumPostPlainTextProcessing) {
		super.remove(forumPostPlainTextProcessing);
	}
	
}