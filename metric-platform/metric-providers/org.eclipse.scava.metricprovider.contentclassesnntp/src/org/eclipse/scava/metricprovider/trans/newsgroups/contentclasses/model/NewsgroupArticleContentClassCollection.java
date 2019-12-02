package org.eclipse.scava.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticleContentClassCollection extends PongoCollection<NewsgroupArticleContentClass> {
	
	public NewsgroupArticleContentClassCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupArticleContentClass> findById(String id) {
		return new IteratorIterable<NewsgroupArticleContentClass>(new PongoCursorIterator<NewsgroupArticleContentClass>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticleContentClass> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupArticleContentClass>(new PongoCursorIterator<NewsgroupArticleContentClass>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupArticleContentClass findOneByNewsgroupName(String q) {
		NewsgroupArticleContentClass newsgroupArticleContentClass = (NewsgroupArticleContentClass) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupArticleContentClass != null) {
			newsgroupArticleContentClass.setPongoCollection(this);
		}
		return newsgroupArticleContentClass;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticleContentClass> iterator() {
		return new PongoCursorIterator<NewsgroupArticleContentClass>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticleContentClass newsgroupArticleContentClass) {
		super.add(newsgroupArticleContentClass);
	}
	
	public void remove(NewsgroupArticleContentClass newsgroupArticleContentClass) {
		super.remove(newsgroupArticleContentClass);
	}
	
}