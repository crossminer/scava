package org.eclipse.scava.metricprovider.trans.documentation.classification.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationEntryClassification extends Pongo {
	
	protected List<String> types = null;
	
	
	public DocumentationEntryClassification() { 
		super();
		dbObject.put("types", new BasicDBList());
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationEntryClassification");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationEntryClassification");
		TYPES.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.classification.model.DocumentationEntryClassification");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static ArrayQueryProducer TYPES = new ArrayQueryProducer("types");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntryClassification setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntryClassification setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	
	public List<String> getTypes() {
		if (types == null) {
			types = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("types"));
		}
		return types;
	}
	
	
	
}