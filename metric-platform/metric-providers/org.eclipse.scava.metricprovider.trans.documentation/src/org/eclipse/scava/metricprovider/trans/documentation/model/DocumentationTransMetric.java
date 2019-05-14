package org.eclipse.scava.metricprovider.trans.documentation.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationTransMetric extends PongoDB {
	
	public DocumentationTransMetric() {}
	
	public DocumentationTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntryCollection documentationEntries = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntryCollection getDocumentationEntries() {
		return documentationEntries;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntries = new DocumentationEntryCollection(db.getCollection("DocumentationTransMetric.documentationEntries"));
		pongoCollections.add(documentationEntries);
	}
}