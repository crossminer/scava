package org.eclipse.scava.metricprovider.trans.documentation.model;

import java.util.Iterator;

import com.googlecode.pongo.runtime.IteratorIterable;
import com.googlecode.pongo.runtime.PongoCollection;
import com.googlecode.pongo.runtime.PongoCursorIterator;
import com.googlecode.pongo.runtime.PongoFactory;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class DocumentationCollection extends PongoCollection<Documentation> {
	
	public DocumentationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<Documentation> findById(String id) {
		return new IteratorIterable<Documentation>(new PongoCursorIterator<Documentation>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<Documentation> findByDocumentationId(String q) {
		return new IteratorIterable<Documentation>(new PongoCursorIterator<Documentation>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public Documentation findOneByDocumentationId(String q) {
		Documentation documentation = (Documentation) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentation != null) {
			documentation.setPongoCollection(this);
		}
		return documentation;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<Documentation> iterator() {
		return new PongoCursorIterator<Documentation>(this, dbCollection.find());
	}
	
	public void add(Documentation documentation) {
		super.add(documentation);
	}
	
	public void remove(Documentation documentation) {
		super.remove(documentation);
	}
	
}