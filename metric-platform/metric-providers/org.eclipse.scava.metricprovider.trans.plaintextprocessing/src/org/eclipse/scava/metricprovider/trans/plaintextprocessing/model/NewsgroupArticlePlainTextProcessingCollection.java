package org.eclipse.scava.metricprovider.trans.plaintextprocessing.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class NewsgroupArticlePlainTextProcessingCollection extends PongoCollection<NewsgroupArticlePlainTextProcessing> {
	
	public NewsgroupArticlePlainTextProcessingCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("newsGroupName");
	}
	
	public Iterable<NewsgroupArticlePlainTextProcessing> findById(String id) {
		return new IteratorIterable<NewsgroupArticlePlainTextProcessing>(new PongoCursorIterator<NewsgroupArticlePlainTextProcessing>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<NewsgroupArticlePlainTextProcessing> findByNewsGroupName(String q) {
		return new IteratorIterable<NewsgroupArticlePlainTextProcessing>(new PongoCursorIterator<NewsgroupArticlePlainTextProcessing>(this, dbCollection.find(new BasicDBObject("newsGroupName", q + ""))));
	}
	
	public NewsgroupArticlePlainTextProcessing findOneByNewsGroupName(String q) {
		NewsgroupArticlePlainTextProcessing newsgroupArticlePlainTextProcessing = (NewsgroupArticlePlainTextProcessing) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("newsGroupName", q + "")));
		if (newsgroupArticlePlainTextProcessing != null) {
			newsgroupArticlePlainTextProcessing.setPongoCollection(this);
		}
		return newsgroupArticlePlainTextProcessing;
	}
	

	public long countByNewsGroupName(String q) {
		return dbCollection.count(new BasicDBObject("newsGroupName", q + ""));
	}
	
	@Override
	public Iterator<NewsgroupArticlePlainTextProcessing> iterator() {
		return new PongoCursorIterator<NewsgroupArticlePlainTextProcessing>(this, dbCollection.find());
	}
	
	public void add(NewsgroupArticlePlainTextProcessing newsgroupArticlePlainTextProcessing) {
		super.add(newsgroupArticlePlainTextProcessing);
	}
	
	public void remove(NewsgroupArticlePlainTextProcessing newsgroupArticlePlainTextProcessing) {
		super.remove(newsgroupArticlePlainTextProcessing);
	}
	
}