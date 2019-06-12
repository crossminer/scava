package org.eclipse.scava.metricprovider.trans.documentation.plaintext.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntryPlainTextCollection extends PongoCollection<DocumentationEntryPlainText> {
	
	public DocumentationEntryPlainTextCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntryPlainText> findById(String id) {
		return new IteratorIterable<DocumentationEntryPlainText>(new PongoCursorIterator<DocumentationEntryPlainText>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntryPlainText> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntryPlainText>(new PongoCursorIterator<DocumentationEntryPlainText>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntryPlainText findOneByDocumentationId(String q) {
		DocumentationEntryPlainText documentationEntryPlainText = (DocumentationEntryPlainText) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntryPlainText != null) {
			documentationEntryPlainText.setPongoCollection(this);
		}
		return documentationEntryPlainText;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntryPlainText> iterator() {
		return new PongoCursorIterator<DocumentationEntryPlainText>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntryPlainText documentationEntryPlainText) {
		super.add(documentationEntryPlainText);
	}
	
	public void remove(DocumentationEntryPlainText documentationEntryPlainText) {
		super.remove(documentationEntryPlainText);
	}
	
}