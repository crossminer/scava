package org.eclipse.scava.metricprovider.trans.documentation.readability.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationReadabilityTransMetric extends PongoDB {
	
	public DocumentationReadabilityTransMetric() {}
	
	public DocumentationReadabilityTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntryReadabilityCollection documentationEntriesReadability = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntryReadabilityCollection getDocumentationEntriesReadability() {
		return documentationEntriesReadability;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntriesReadability = new DocumentationEntryReadabilityCollection(db.getCollection("DocumentationReadabilityTransMetric.documentationEntriesReadability"));
		pongoCollections.add(documentationEntriesReadability);
	}
}