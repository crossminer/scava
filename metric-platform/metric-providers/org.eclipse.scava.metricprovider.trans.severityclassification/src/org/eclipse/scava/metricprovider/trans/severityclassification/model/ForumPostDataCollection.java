package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostDataCollection extends PongoCollection<ForumPostData> {
	
	public ForumPostDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumPostData> findById(String id) {
		return new IteratorIterable<ForumPostData>(new PongoCursorIterator<ForumPostData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumPostData> findByForumId(String q) {
		return new IteratorIterable<ForumPostData>(new PongoCursorIterator<ForumPostData>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumPostData findOneByForumId(String q) {
		ForumPostData forumPostData = (ForumPostData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumPostData != null) {
			forumPostData.setPongoCollection(this);
		}
		return forumPostData;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumPostData> iterator() {
		return new PongoCursorIterator<ForumPostData>(this, dbCollection.find());
	}
	
	public void add(ForumPostData forumPostData) {
		super.add(forumPostData);
	}
	
	public void remove(ForumPostData forumPostData) {
		super.remove(forumPostData);
	}
	
}