package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticleDataCollection extends PongoCollection<NewsgroupArticleData> {
	
	public NewsgroupArticleDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("NewsGroupName");
	}
	
	public Iterable<NewsgroupArticleData> findById(String id) {
		return new IteratorIterable<NewsgroupArticleData>(new PongoCursorIterator<NewsgroupArticleData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticleData> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticleData>(new PongoCursorIterator<NewsgroupArticleData>(this, dbCollection.find(new BasicDBObject("NewsGroupName", q + ""))));
	}
	
	public NewsgroupArticleData findOneByNewsGroupName(String q) {
		NewsgroupArticleData newsgroupArticleData = (NewsgroupArticleData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("NewsGroupName", q + "")));
		if (newsgroupArticleData != null) {
			newsgroupArticleData.setPongoCollection(this);
		}
		return newsgroupArticleData;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("NewsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticleData> iterator() {
		return new PongoCursorIterator<NewsgroupArticleData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticleData newsgroupArticleData) {
		super.add(newsgroupArticleData);
	}
	
	public void remove(NewsgroupArticleData newsgroupArticleData) {
		super.remove(newsgroupArticleData);
	}
	
}