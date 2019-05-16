package org.eclipse.scava.metricprovider.trans.documentation.sentiment.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationSentimentTransMetric extends PongoDB {
	
	public DocumentationSentimentTransMetric() {}
	
	public DocumentationSentimentTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntrySentimentCollection documentationEntriesSentiment = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntrySentimentCollection getDocumentationEntriesSentiment() {
		return documentationEntriesSentiment;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntriesSentiment = new DocumentationEntrySentimentCollection(db.getCollection("DocumentationSentimentTransMetric.documentationEntriesSentiment"));
		pongoCollections.add(documentationEntriesSentiment);
	}
}