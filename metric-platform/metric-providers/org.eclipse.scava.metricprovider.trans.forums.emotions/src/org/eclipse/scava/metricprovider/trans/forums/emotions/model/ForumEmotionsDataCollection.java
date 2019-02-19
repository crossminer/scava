package org.eclipse.scava.metricprovider.trans.forums.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumEmotionsDataCollection extends PongoCollection<ForumEmotionsData> {
	
	public ForumEmotionsDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumEmotionsData> findById(String id) {
		return new IteratorIterable<ForumEmotionsData>(new PongoCursorIterator<ForumEmotionsData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumEmotionsData> findByForumId(String q) {
		return new IteratorIterable<ForumEmotionsData>(new PongoCursorIterator<ForumEmotionsData>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumEmotionsData findOneByForumId(String q) {
		ForumEmotionsData forumEmotionsData = (ForumEmotionsData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumEmotionsData != null) {
			forumEmotionsData.setPongoCollection(this);
		}
		return forumEmotionsData;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumEmotionsData> iterator() {
		return new PongoCursorIterator<ForumEmotionsData>(this, dbCollection.find());
	}
	
	public void add(ForumEmotionsData forumEmotionsData) {
		super.add(forumEmotionsData);
	}
	
	public void remove(ForumEmotionsData forumEmotionsData) {
		super.remove(forumEmotionsData);
	}
	
}