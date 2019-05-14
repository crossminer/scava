package org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntryDetectingCodeCollection extends PongoCollection<DocumentationEntryDetectingCode> {
	
	public DocumentationEntryDetectingCodeCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntryDetectingCode> findById(String id) {
		return new IteratorIterable<DocumentationEntryDetectingCode>(new PongoCursorIterator<DocumentationEntryDetectingCode>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntryDetectingCode> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntryDetectingCode>(new PongoCursorIterator<DocumentationEntryDetectingCode>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntryDetectingCode findOneByDocumentationId(String q) {
		DocumentationEntryDetectingCode documentationEntryDetectingCode = (DocumentationEntryDetectingCode) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntryDetectingCode != null) {
			documentationEntryDetectingCode.setPongoCollection(this);
		}
		return documentationEntryDetectingCode;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntryDetectingCode> iterator() {
		return new PongoCursorIterator<DocumentationEntryDetectingCode>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntryDetectingCode documentationEntryDetectingCode) {
		super.add(documentationEntryDetectingCode);
	}
	
	public void remove(DocumentationEntryDetectingCode documentationEntryDetectingCode) {
		super.remove(documentationEntryDetectingCode);
	}
	
}