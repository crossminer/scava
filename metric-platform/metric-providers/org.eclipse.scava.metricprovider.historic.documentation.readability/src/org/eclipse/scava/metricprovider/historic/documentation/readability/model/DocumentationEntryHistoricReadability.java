package org.eclipse.scava.metricprovider.historic.documentation.readability.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DocumentationEntryHistoricReadability extends Pongo {
	
	
	
	public DocumentationEntryHistoricReadability() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationEntryHistoricReadability");
		ENTRYID.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationEntryHistoricReadability");
		READABILITY.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationEntryHistoricReadability");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static StringQueryProducer ENTRYID = new StringQueryProducer("entryId"); 
	public static NumericalQueryProducer READABILITY = new NumericalQueryProducer("readability");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationEntryHistoricReadability setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public String getEntryId() {
		return parseString(dbObject.get("entryId")+"", "");
	}
	
	public DocumentationEntryHistoricReadability setEntryId(String entryId) {
		dbObject.put("entryId", entryId);
		notifyChanged();
		return this;
	}
	public double getReadability() {
		return parseDouble(dbObject.get("readability")+"", 0.0d);
	}
	
	public DocumentationEntryHistoricReadability setReadability(double readability) {
		dbObject.put("readability", readability);
		notifyChanged();
		return this;
	}
	
	
	
	
}