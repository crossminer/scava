package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumsPostsCollection extends PongoCollection<ForumsPosts> {
	
	public ForumsPostsCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<ForumsPosts> findById(String id) {
		return new IteratorIterable<ForumsPosts>(new PongoCursorIterator<ForumsPosts>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ForumsPosts> findByForumId(String q) {
		return new IteratorIterable<ForumsPosts>(new PongoCursorIterator<ForumsPosts>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public ForumsPosts findOneByForumId(String q) {
		ForumsPosts forumsPosts = (ForumsPosts) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (forumsPosts != null) {
			forumsPosts.setPongoCollection(this);
		}
		return forumsPosts;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<ForumsPosts> iterator() {
		return new PongoCursorIterator<ForumsPosts>(this, dbCollection.find());
	}
	
	public void add(ForumsPosts forumsPosts) {
		super.add(forumsPosts);
	}
	
	public void remove(ForumsPosts forumsPosts) {
		super.remove(forumsPosts);
	}
	
}