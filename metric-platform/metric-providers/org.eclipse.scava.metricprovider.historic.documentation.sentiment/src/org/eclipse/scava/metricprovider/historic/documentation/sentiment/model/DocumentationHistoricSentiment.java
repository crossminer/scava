package org.eclipse.scava.metricprovider.historic.documentation.sentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationHistoricSentiment extends Pongo {
	
	
	
	public DocumentationHistoricSentiment() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationHistoricSentiment");
		NUMBEROFDOCUMENTATIONENTRIES.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationHistoricSentiment");
		AVERAGEDOCUMENTATIONSENTIMENT.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationHistoricSentiment");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTATIONENTRIES = new NumericalQueryProducer("numberOfDocumentationEntries");
	public static NumericalQueryProducer AVERAGEDOCUMENTATIONSENTIMENT = new NumericalQueryProducer("averageDocumentationSentiment");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationHistoricSentiment setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocumentationEntries() {
		return parseInteger(dbObject.get("numberOfDocumentationEntries")+"", 0);
	}
	
	public DocumentationHistoricSentiment setNumberOfDocumentationEntries(int numberOfDocumentationEntries) {
		dbObject.put("numberOfDocumentationEntries", numberOfDocumentationEntries);
		notifyChanged();
		return this;
	}
	public double getAverageDocumentationSentiment() {
		return parseDouble(dbObject.get("averageDocumentationSentiment")+"", 0.0d);
	}
	
	public DocumentationHistoricSentiment setAverageDocumentationSentiment(double averageDocumentationSentiment) {
		dbObject.put("averageDocumentationSentiment", averageDocumentationSentiment);
		notifyChanged();
		return this;
	}
	
	
	
	
}