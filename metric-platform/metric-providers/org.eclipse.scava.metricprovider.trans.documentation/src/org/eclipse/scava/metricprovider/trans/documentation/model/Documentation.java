package org.eclipse.scava.metricprovider.trans.documentation.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class Documentation extends Pongo {
	
	protected List<String> entryId = null;
	
	
	public Documentation() { 
		super();
		dbObject.put("entryId", new BasicDBList());
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		REMOVEDENTRIESUPDATE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer REMOVEDENTRIESUPDATE = new StringQueryProducer("removedEntriesUpdate"); 
	public static ArrayQueryProducer ENTRYID = new ArrayQueryProducer("entryId");
	
	
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
	
	public List<String> getEntryId() {
		if (entryId == null) {
			entryId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("entryId"));
		}
		return entryId;
	}
	
	
	
}