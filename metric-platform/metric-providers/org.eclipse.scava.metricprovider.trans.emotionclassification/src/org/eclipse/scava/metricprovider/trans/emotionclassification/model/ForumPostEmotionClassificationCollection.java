package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ForumPostEmotionClassificationCollection extends PongoCollection<ForumPostEmotionClassification> {
	
	public ForumPostEmotionClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ForumPostEmotionClassification> findById(String id) {
		return new IteratorIterable<ForumPostEmotionClassification>(new PongoCursorIterator<ForumPostEmotionClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ForumPostEmotionClassification> iterator() {
		return new PongoCursorIterator<ForumPostEmotionClassification>(this, dbCollection.find());
	}
	
	public void add(ForumPostEmotionClassification forumPostEmotionClassification) {
		super.add(forumPostEmotionClassification);
	}
	
	public void remove(ForumPostEmotionClassification forumPostEmotionClassification) {
		super.remove(forumPostEmotionClassification);
	}
	
}