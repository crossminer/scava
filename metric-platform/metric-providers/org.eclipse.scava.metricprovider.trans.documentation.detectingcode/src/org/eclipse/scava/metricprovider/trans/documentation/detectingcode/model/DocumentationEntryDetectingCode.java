package org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationEntryDetectingCode extends Pongo {
	
	
	
	public DocumentationEntryDetectingCode() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode");
		NATURALLANGUAGE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode");
		CODE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.detectingcode.model.DocumentationEntryDetectingCode");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static StringQueryProducer NATURALLANGUAGE = new StringQueryProducer("naturalLanguage"); 
	public static StringQueryProducer CODE = new StringQueryProducer("code"); 
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntryDetectingCode setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntryDetectingCode setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	public String getNaturalLanguage() {
		return parseString(dbObject.get("naturalLanguage")+"", "");
	}
	
	public DocumentationEntryDetectingCode setNaturalLanguage(String naturalLanguage) {
		dbObject.put("naturalLanguage", naturalLanguage);
		notifyChanged();
		return this;
	}
	public String getCode() {
		return parseString(dbObject.get("code")+"", "");
	}
	
	public DocumentationEntryDetectingCode setCode(String code) {
		dbObject.put("code", code);
		notifyChanged();
		return this;
	}
	
	
	
	
}