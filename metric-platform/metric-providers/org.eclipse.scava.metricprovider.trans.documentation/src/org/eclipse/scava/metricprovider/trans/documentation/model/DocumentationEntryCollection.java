package org.eclipse.scava.metricprovider.trans.documentation.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntryCollection extends PongoCollection<DocumentationEntry> {
	
	public DocumentationEntryCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntry> findById(String id) {
		return new IteratorIterable<DocumentationEntry>(new PongoCursorIterator<DocumentationEntry>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntry> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntry>(new PongoCursorIterator<DocumentationEntry>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntry findOneByDocumentationId(String q) {
		DocumentationEntry documentationEntry = (DocumentationEntry) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntry != null) {
			documentationEntry.setPongoCollection(this);
		}
		return documentationEntry;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntry> iterator() {
		return new PongoCursorIterator<DocumentationEntry>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntry documentationEntry) {
		super.add(documentationEntry);
	}
	
	public void remove(DocumentationEntry documentationEntry) {
		super.remove(documentationEntry);
	}
	
}