package org.eclipse.scava.metricprovider.trans.topics.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class NewsgroupTopicCollection extends PongoCollection<NewsgroupTopic> {
	
	public NewsgroupTopicCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsgroupName");
	}
	
	public Iterable<NewsgroupTopic> findById(String id) {
		return new IteratorIterable<NewsgroupTopic>(new PongoCursorIterator<NewsgroupTopic>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupTopic> findByNewsgroupName(String q) {
		return new IteratorIterable<NewsgroupTopic>(new PongoCursorIterator<NewsgroupTopic>(this, dbCollection.find(new BasicDBObject("newsgroupName", q + ""))));
	}
	
	public NewsgroupTopic findOneByNewsgroupName(String q) {
		NewsgroupTopic newsgroupTopic = (NewsgroupTopic) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsgroupName", q + "")));
		if (newsgroupTopic != null) {
			newsgroupTopic.setPongoCollection(this);
		}
		return newsgroupTopic;
	}
	

	public long countByNewsgroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsgroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupTopic> iterator() {
		return new PongoCursorIterator<NewsgroupTopic>(this, dbCollection.find());
	}
	
	public void add(NewsgroupTopic newsgroupTopic) {
		super.add(newsgroupTopic);
	}
	
	public void remove(NewsgroupTopic newsgroupTopic) {
		super.remove(newsgroupTopic);
	}
	
}