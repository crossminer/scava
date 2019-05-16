package org.eclipse.scava.metricprovider.trans.documentation.sentiment.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntrySentimentCollection extends PongoCollection<DocumentationEntrySentiment> {
	
	public DocumentationEntrySentimentCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntrySentiment> findById(String id) {
		return new IteratorIterable<DocumentationEntrySentiment>(new PongoCursorIterator<DocumentationEntrySentiment>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntrySentiment> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntrySentiment>(new PongoCursorIterator<DocumentationEntrySentiment>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntrySentiment findOneByDocumentationId(String q) {
		DocumentationEntrySentiment documentationEntrySentiment = (DocumentationEntrySentiment) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntrySentiment != null) {
			documentationEntrySentiment.setPongoCollection(this);
		}
		return documentationEntrySentiment;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntrySentiment> iterator() {
		return new PongoCursorIterator<DocumentationEntrySentiment>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntrySentiment documentationEntrySentiment) {
		super.add(documentationEntrySentiment);
	}
	
	public void remove(DocumentationEntrySentiment documentationEntrySentiment) {
		super.remove(documentationEntrySentiment);
	}
	
}