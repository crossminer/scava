package org.eclipse.scava.metricprovider.trans.topics.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlesDataCollection extends PongoCollection<NewsgroupArticlesData> {
	
	public NewsgroupArticlesDataCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupArticlesData> findById(String id) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlesData> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupArticlesData>(new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupArticlesData findOneByNewsgroupName(String q) {
		NewsgroupArticlesData newsgroupArticlesData = (NewsgroupArticlesData) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupArticlesData != null) {
			newsgroupArticlesData.setPongoCollection(this);
		}
		return newsgroupArticlesData;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlesData> iterator() {
		return new PongoCursorIterator<NewsgroupArticlesData>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlesData newsgroupArticlesData) {
		super.add(newsgroupArticlesData);
	}
	
	public void remove(NewsgroupArticlesData newsgroupArticlesData) {
		super.remove(newsgroupArticlesData);
	}
	
}