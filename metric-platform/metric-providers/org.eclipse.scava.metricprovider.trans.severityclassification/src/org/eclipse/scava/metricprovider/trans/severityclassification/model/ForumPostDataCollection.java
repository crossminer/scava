package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostDataCollection extends PongoCollection<ForumPostData> {
	
	public ForumPostDataCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostData> findById(String id) {
		return new IteratorIterable<ForumPostData>(new PongoCursorIterator<ForumPostData>(this, dbCollection.find(new BasicDBObject("_id", id))));
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