package org.eclipse.scava.metricprovider.trans.documentation.sentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationEntrySentiment extends Pongo {
	
	
	
	public DocumentationEntrySentiment() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationEntrySentiment");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationEntrySentiment");
		POLARITY.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.sentiment.model.DocumentationEntrySentiment");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static StringQueryProducer POLARITY = new StringQueryProducer("polarity"); 
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntrySentiment setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntrySentiment setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	public String getPolarity() {
		return parseString(dbObject.get("polarity")+"", "");
	}
	
	public DocumentationEntrySentiment setPolarity(String polarity) {
		dbObject.put("polarity", polarity);
		notifyChanged();
		return this;
	}
	
	
	
	
}