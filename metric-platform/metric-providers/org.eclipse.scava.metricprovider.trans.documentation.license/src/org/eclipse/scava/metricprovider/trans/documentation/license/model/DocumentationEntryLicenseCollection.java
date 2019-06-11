package org.eclipse.scava.metricprovider.trans.documentation.license.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class DocumentationEntryLicenseCollection extends PongoCollection<DocumentationEntryLicense> {
	
	public DocumentationEntryLicenseCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("documentationId");
	}
	
	public Iterable<DocumentationEntryLicense> findById(String id) {
		return new IteratorIterable<DocumentationEntryLicense>(new PongoCursorIterator<DocumentationEntryLicense>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<DocumentationEntryLicense> findByDocumentationId(String q) {
		return new IteratorIterable<DocumentationEntryLicense>(new PongoCursorIterator<DocumentationEntryLicense>(this, dbCollection.find(new BasicDBObject("documentationId", q + ""))));
	}
	
	public DocumentationEntryLicense findOneByDocumentationId(String q) {
		DocumentationEntryLicense documentationEntryLicense = (DocumentationEntryLicense) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("documentationId", q + "")));
		if (documentationEntryLicense != null) {
			documentationEntryLicense.setPongoCollection(this);
		}
		return documentationEntryLicense;
	}
	

	public long countByDocumentationId(String q) {
		return dbCollection.count(new BasicDBObject("documentationId", q + ""));
	}
	
	@Override
	public Iterator<DocumentationEntryLicense> iterator() {
		return new PongoCursorIterator<DocumentationEntryLicense>(this, dbCollection.find());
	}
	
	public void add(DocumentationEntryLicense documentationEntryLicense) {
		super.add(documentationEntryLicense);
	}
	
	public void remove(DocumentationEntryLicense documentationEntryLicense) {
		super.remove(documentationEntryLicense);
	}
	
}