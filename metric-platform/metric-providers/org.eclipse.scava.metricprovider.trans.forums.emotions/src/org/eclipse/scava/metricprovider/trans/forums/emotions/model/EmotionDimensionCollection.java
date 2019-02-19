package org.eclipse.scava.metricprovider.trans.forums.emotions.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class EmotionDimensionCollection extends PongoCollection<EmotionDimension> {
	
	public EmotionDimensionCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("forumId");
	}
	
	public Iterable<EmotionDimension> findById(String id) {
		return new IteratorIterable<EmotionDimension>(new PongoCursorIterator<EmotionDimension>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<EmotionDimension> findByForumId(String q) {
		return new IteratorIterable<EmotionDimension>(new PongoCursorIterator<EmotionDimension>(this, dbCollection.find(new BasicDBObject("forumId", q + ""))));
	}
	
	public EmotionDimension findOneByForumId(String q) {
		EmotionDimension emotionDimension = (EmotionDimension) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("forumId", q + "")));
		if (emotionDimension != null) {
			emotionDimension.setPongoCollection(this);
		}
		return emotionDimension;
	}
	

	public long countByForumId(String q) {
		return dbCollection.count(new BasicDBObject("forumId", q + ""));
	}
	
	@Override
	public Iterator<EmotionDimension> iterator() {
		return new PongoCursorIterator<EmotionDimension>(this, dbCollection.find());
	}
	
	public void add(EmotionDimension emotionDimension) {
		super.add(emotionDimension);
	}
	
	public void remove(EmotionDimension emotionDimension) {
		super.remove(emotionDimension);
	}
	
}