package org.eclipse.scava.metricprovider.trans.documentation.classification.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntryClassificationCollection extends PongoCollection<DocumentationEntryClassification> {
	
	public DocumentationEntryClassificationCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntryClassification> findById(String id) {
		return new IteratorIterable<DocumentationEntryClassification>(new PongoCursorIterator<DocumentationEntryClassification>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntryClassification> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntryClassification>(new PongoCursorIterator<DocumentationEntryClassification>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntryClassification findOneByDocumentationId(String q) {
		DocumentationEntryClassification documentationEntryClassification = (DocumentationEntryClassification) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntryClassification != null) {
			documentationEntryClassification.setPongoCollection(this);
		}
		return documentationEntryClassification;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntryClassification> iterator() {
		return new PongoCursorIterator<DocumentationEntryClassification>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntryClassification documentationEntryClassification) {
		super.add(documentationEntryClassification);
	}
	
	public void remove(DocumentationEntryClassification documentationEntryClassification) {
		super.remove(documentationEntryClassification);
	}
	
}