package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesEmotionClassificationCollection extends PongoCollection<NewsgroupArticlesEmotionClassification> {
	
	public NewsgroupArticlesEmotionClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsGroupName");
	}
	
	public Iterable<NewsgroupArticlesEmotionClassification> findById(String id) {
		return new IteratorIterable<NewsgroupArticlesEmotionClassification>(new PongoCursorIterator<NewsgroupArticlesEmotionClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlesEmotionClassification> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticlesEmotionClassification>(new PongoCursorIterator<NewsgroupArticlesEmotionClassification>(this, dbCollection.find(new BasicDBObject("newsGroupName", q + ""))));
	}
	
	public NewsgroupArticlesEmotionClassification findOneByNewsGroupName(String q) {
		NewsgroupArticlesEmotionClassification newsgroupArticlesEmotionClassification = (NewsgroupArticlesEmotionClassification) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsGroupName", q + "")));
		if (newsgroupArticlesEmotionClassification != null) {
			newsgroupArticlesEmotionClassification.setPongoCollection(this);
		}
		return newsgroupArticlesEmotionClassification;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlesEmotionClassification> iterator() {
		return new PongoCursorIterator<NewsgroupArticlesEmotionClassification>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlesEmotionClassification newsgroupArticlesEmotionClassification) {
		super.add(newsgroupArticlesEmotionClassification);
	}
	
	public void remove(NewsgroupArticlesEmotionClassification newsgroupArticlesEmotionClassification) {
		super.remove(newsgroupArticlesEmotionClassification);
	}
	
}