package org.eclipse.scava.metricprovider.historic.documentation.sentiment.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class DocumentationSentimentHistoricMetric extends Pongo {
	
	protected List<DocumentationHistoricSentiment> documentationSentiment = null;
	protected List<DocumentationEntryHistoricSentiment> documentationEntriesSentiment = null;
	
	
	public DocumentationSentimentHistoricMetric() { 
		super();
		dbObject.put("documentationSentiment", new BasicDBList());
		dbObject.put("documentationEntriesSentiment", new BasicDBList());
	}
	
	
	
	
	
	public List<DocumentationHistoricSentiment> getDocumentationSentiment() {
		if (documentationSentiment == null) {
			documentationSentiment = new PongoList<DocumentationHistoricSentiment>(this, "documentationSentiment", true);
		}
		return documentationSentiment;
	}
	public List<DocumentationEntryHistoricSentiment> getDocumentationEntriesSentiment() {
		if (documentationEntriesSentiment == null) {
			documentationEntriesSentiment = new PongoList<DocumentationEntryHistoricSentiment>(this, "documentationEntriesSentiment", true);
		}
		return documentationEntriesSentiment;
	}
	
	
}