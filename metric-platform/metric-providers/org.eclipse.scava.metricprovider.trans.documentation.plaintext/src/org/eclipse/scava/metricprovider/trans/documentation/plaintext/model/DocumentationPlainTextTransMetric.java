package org.eclipse.scava.metricprovider.trans.documentation.plaintext.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationPlainTextTransMetric extends PongoDB {
	
	public DocumentationPlainTextTransMetric() {}
	
	public DocumentationPlainTextTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntryPlainTextCollection documentationEntriesPlainText = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntryPlainTextCollection getDocumentationEntriesPlainText() {
		return documentationEntriesPlainText;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntriesPlainText = new DocumentationEntryPlainTextCollection(db.getCollection("DocumentationPlainTextTransMetric.documentationEntriesPlainText"));
		pongoCollections.add(documentationEntriesPlainText);
	}
}