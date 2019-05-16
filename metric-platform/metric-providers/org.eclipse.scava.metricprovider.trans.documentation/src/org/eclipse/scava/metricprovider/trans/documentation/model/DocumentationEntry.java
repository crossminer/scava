package org.eclipse.scava.metricprovider.trans.documentation.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationEntry extends Pongo {
	
	protected List<String> plainText = null;
	
	
	public DocumentationEntry() { 
		super();
		dbObject.put("plainText", new BasicDBList());
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
		PLAINTEXT.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.DocumentationEntry");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static ArrayQueryProducer PLAINTEXT = new ArrayQueryProducer("plainText");
	
	
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
	
	public List<String> getPlainText() {
		if (plainText == null) {
			plainText = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("plainText"));
		}
		return plainText;
	}
	
	
	
}