package org.eclipse.scava.metricprovider.trans.documentation.plaintext.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationEntryPlainText extends Pongo {
	
	protected List<String> plainText = null;
	
	
	public DocumentationEntryPlainText() { 
		super();
		dbObject.put("plainText", new BasicDBList());
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationEntryPlainText");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationEntryPlainText");
		PLAINTEXT.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.plaintext.model.DocumentationEntryPlainText");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static ArrayQueryProducer PLAINTEXT = new ArrayQueryProducer("plainText");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntryPlainText setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntryPlainText setEntryId(String entryId) {
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