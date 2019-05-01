package org.eclipse.scava.platform.indexing;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class IndexCollection extends PongoCollection<Index> {
	
	public IndexCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("metricId");
	}
	
	public Iterable<Index> findById(String id) {
		return new IteratorIterable<Index>(new PongoCursorIterator<Index>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Index> findByMetricId(String q) {
		return new IteratorIterable<Index>(new PongoCursorIterator<Index>(this, dbCollection.find(new BasicDBObject("metricId", q + ""))));
	}
	
	public Index findOneByMetricId(String q) {
		Index index = (Index) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("metricId", q + "")));
		if (index != null) {
			index.setPongoCollection(this);
		}
		return index;
	}
	

	public long countByMetricId(String q) {
		return dbCollection.count(new BasicDBObject("metricId", q + ""));
	}
	
	@Override
	public Iterator<Index> iterator() {
		return new PongoCursorIterator<Index>(this, dbCollection.find());
	}
	
	public void add(Index index) {
		super.add(index);
	}
	
	public void remove(Index index) {
		super.remove(index);
	}
	
}