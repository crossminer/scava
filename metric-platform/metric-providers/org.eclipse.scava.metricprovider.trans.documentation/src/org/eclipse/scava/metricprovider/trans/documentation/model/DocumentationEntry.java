package org.eclipse.scava.metricprovider.trans.documentation.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationEntry extends Pongo {
	
	
	
	public DocumentationEntry() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
		BODY.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
		HTMLFORMATTED.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static StringQueryProducer BODY = new StringQueryProducer("body"); 
	public static StringQueryProducer HTMLFORMATTED = new StringQueryProducer("htmlFormatted"); 
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntry setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntry setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public DocumentationEntry setBody(String body) {
		dbObject.put("body", body);
		notifyChanged();
		return this;
	}
	public boolean getHtmlFormatted() {
		return parseBoolean(dbObject.get("htmlFormatted")+"", false);
	}
	
	public DocumentationEntry setHtmlFormatted(boolean htmlFormatted) {
		dbObject.put("htmlFormatted", htmlFormatted);
		notifyChanged();
		return this;
	}
	
	
	
	
}