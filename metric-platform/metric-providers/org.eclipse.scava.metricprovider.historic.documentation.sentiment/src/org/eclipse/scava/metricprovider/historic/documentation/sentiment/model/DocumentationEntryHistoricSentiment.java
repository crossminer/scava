package org.eclipse.scava.metricprovider.historic.documentation.sentiment.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationEntryHistoricSentiment extends Pongo {
	
	
	
	public DocumentationEntryHistoricSentiment() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationEntryHistoricSentiment");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationEntryHistoricSentiment");
		POLARITY.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.sentiment.model.DocumentationEntryHistoricSentiment");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static StringQueryProducer POLARITY = new StringQueryProducer("polarity"); 
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntryHistoricSentiment setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntryHistoricSentiment setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	public String getPolarity() {
		return parseString(dbObject.get("polarity")+"", "");
	}
	
	public DocumentationEntryHistoricSentiment setPolarity(String polarity) {
		dbObject.put("polarity", polarity);
		notifyChanged();
		return this;
	}
	
	
	
	
}