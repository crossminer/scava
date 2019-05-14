package org.eclipse.scava.metricprovider.trans.documentation.readability.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntryReadabilityCollection extends PongoCollection<DocumentationEntryReadability> {
	
	public DocumentationEntryReadabilityCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntryReadability> findById(String id) {
		return new IteratorIterable<DocumentationEntryReadability>(new PongoCursorIterator<DocumentationEntryReadability>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntryReadability> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntryReadability>(new PongoCursorIterator<DocumentationEntryReadability>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntryReadability findOneByDocumentationId(String q) {
		DocumentationEntryReadability documentationEntryReadability = (DocumentationEntryReadability) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntryReadability != null) {
			documentationEntryReadability.setPongoCollection(this);
		}
		return documentationEntryReadability;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntryReadability> iterator() {
		return new PongoCursorIterator<DocumentationEntryReadability>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntryReadability documentationEntryReadability) {
		super.add(documentationEntryReadability);
	}
	
	public void remove(DocumentationEntryReadability documentationEntryReadability) {
		super.remove(documentationEntryReadability);
	}
	
}