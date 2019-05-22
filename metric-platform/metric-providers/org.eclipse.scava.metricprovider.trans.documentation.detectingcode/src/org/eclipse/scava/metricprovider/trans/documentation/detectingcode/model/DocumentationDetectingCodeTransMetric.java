package org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationDetectingCodeTransMetric extends PongoDB {
	
	public DocumentationDetectingCodeTransMetric() {}
	
	public DocumentationDetectingCodeTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntryDetectingCodeCollection documentationEntriesDetectingCode = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntryDetectingCodeCollection getDocumentationEntriesDetectingCode() {
		return documentationEntriesDetectingCode;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntriesDetectingCode = new DocumentationEntryDetectingCodeCollection(db.getCollection("DocumentationDetectingCodeTransMetric.documentationEntriesDetectingCode"));
		pongoCollections.add(documentationEntriesDetectingCode);
	}
}