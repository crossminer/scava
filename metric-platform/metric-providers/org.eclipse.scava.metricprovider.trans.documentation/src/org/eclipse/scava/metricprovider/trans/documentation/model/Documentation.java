package org.eclipse.scava.metricprovider.trans.documentation.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PrimitiveList;
import com.googlecode.pongo.runtime.querying.ArrayQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class Documentation extends Pongo {
	
	protected List<String> entriesId = null;
	protected List<String> removedEntriesId = null;
	
	
	public Documentation() { 
		super();
		dbObject.put("entriesId", new BasicDBList());
		dbObject.put("removedEntriesId", new BasicDBList());
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		ENTRIESID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		REMOVEDENTRIESID.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		LASTUPDATEDATE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		LASTREVISIONANALYZED.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		NEXTUPDATEDATE.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
		UPDATED.setOwningType("org.eclipse.scava.metricprovider.trans.documentation.model.Documentation");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer LASTUPDATEDATE = new StringQueryProducer("lastUpdateDate"); 
	public static StringQueryProducer LASTREVISIONANALYZED = new StringQueryProducer("lastRevisionAnalyzed"); 
	public static StringQueryProducer NEXTUPDATEDATE = new StringQueryProducer("nextUpdateDate"); 
	public static StringQueryProducer UPDATED = new StringQueryProducer("updated"); 
	public static ArrayQueryProducer ENTRIESID = new ArrayQueryProducer("entriesId");
	public static ArrayQueryProducer REMOVEDENTRIESID = new ArrayQueryProducer("removedEntriesId");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public Documentation setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getLastUpdateDate() {
		return parseString(dbObject.get("lastUpdateDate")+"", "");
	}
	
	public Documentation setLastUpdateDate(String lastUpdateDate) {
		dbObject.put("lastUpdateDate", lastUpdateDate);
		notifyChanged();
		return this;
	}
	public String getLastRevisionAnalyzed() {
		return parseString(dbObject.get("lastRevisionAnalyzed")+"", "");
	}
	
	public Documentation setLastRevisionAnalyzed(String lastRevisionAnalyzed) {
		dbObject.put("lastRevisionAnalyzed", lastRevisionAnalyzed);
		notifyChanged();
		return this;
	}
	public String getNextUpdateDate() {
		return parseString(dbObject.get("nextUpdateDate")+"", "");
	}
	
	public Documentation setNextUpdateDate(String nextUpdateDate) {
		dbObject.put("nextUpdateDate", nextUpdateDate);
		notifyChanged();
		return this;
	}
	public boolean getUpdated() {
		return parseBoolean(dbObject.get("updated")+"", false);
	}
	
	public Documentation setUpdated(boolean updated) {
		dbObject.put("updated", updated);
		notifyChanged();
		return this;
	}
	
	public List<String> getEntriesId() {
		if (entriesId == null) {
			entriesId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("entriesId"));
		}
		return entriesId;
	}
	public List<String> getRemovedEntriesId() {
		if (removedEntriesId == null) {
			removedEntriesId = new PrimitiveList<String>(this, (BasicDBList) dbObject.get("removedEntriesId"));
		}
		return removedEntriesId;
	}
	
	
	
}