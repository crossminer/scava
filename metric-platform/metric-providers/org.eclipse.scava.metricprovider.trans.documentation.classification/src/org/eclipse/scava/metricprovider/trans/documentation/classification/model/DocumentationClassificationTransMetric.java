package org.eclipse.scava.metricprovider.trans.documentation.classification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationClassificationTransMetric extends PongoDB {
	
	public DocumentationClassificationTransMetric() {}
	
	public DocumentationClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntryClassificationCollection documentationEntriesClassification = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntryClassificationCollection getDocumentationEntriesClassification() {
		return documentationEntriesClassification;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntriesClassification = new DocumentationEntryClassificationCollection(db.getCollection("DocumentationClassificationTransMetric.documentationEntriesClassification"));
		pongoCollections.add(documentationEntriesClassification);
	}
}