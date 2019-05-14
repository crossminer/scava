package org.eclipse.scava.metricprovider.trans.documentation.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Documentation extends Pongo {
	
	protected List<String> entriesId = null;
	
	
	public Documentation() { 
		super();
		dbObject.put("entriesId", new BasicDBList());
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		ENTRIESID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		REMOVEDENTRIESUPDATE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer REMOVEDENTRIESUPDATE = new StringQueryProducer("removedEntriesUpdate"); 
	public static ArrayQueryProducer ENTRIESID = new ArrayQueryProducer("entriesId");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public Documentation setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public boolean getRemovedEntriesUpdate() {
		return parseBoolean(dbObject.get("removedEntriesUpdate")+"", false);
	}
	
	public Documentation setRemovedEntriesUpdate(boolean removedEntriesUpdate) {
		dbObject.put("removedEntriesUpdate", removedEntriesUpdate);
		notifyChanged();
		return this;
	}
	
	public List<String> getEntriesId() {
		if (entriesId == null) {
			entriesId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("entriesId"));
		}
		return entriesId;
	}
	
	
	
}