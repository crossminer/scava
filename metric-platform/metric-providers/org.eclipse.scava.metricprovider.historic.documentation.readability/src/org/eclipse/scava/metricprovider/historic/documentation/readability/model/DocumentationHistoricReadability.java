package org.eclipse.scava.metricprovider.historic.documentation.readability.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationHistoricReadability extends Pongo {
	
	
	
	public DocumentationHistoricReadability() { 
		super();
		DOCUMENTATIONID.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationHistoricReadability");
		NUMBEROFDOCUMENTATIONENTRIES.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationHistoricReadability");
		AVERAGEDOCUMENTATIONREADABILITY.setOwningType("org.eclipse.scava.metricprovider.historic.documentation.readability.model.DocumentationHistoricReadability");
	}
	
	public static StringQueryProducer DOCUMENTATIONID = new StringQueryProducer("documentationId"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTATIONENTRIES = new NumericalQueryProducer("numberOfDocumentationEntries");
	public static NumericalQueryProducer AVERAGEDOCUMENTATIONREADABILITY = new NumericalQueryProducer("averageDocumentationReadability");
	
	
	public String getDocumentationId() {
		return parseString(dbObject.get("documentationId")+"", "");
	}
	
	public DocumentationHistoricReadability setDocumentationId(String documentationId) {
		dbObject.put("documentationId", documentationId);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocumentationEntries() {
		return parseInteger(dbObject.get("numberOfDocumentationEntries")+"", 0);
	}
	
	public DocumentationHistoricReadability setNumberOfDocumentationEntries(int numberOfDocumentationEntries) {
		dbObject.put("numberOfDocumentationEntries", numberOfDocumentationEntries);
		notifyChanged();
		return this;
	}
	public double getAverageDocumentationReadability() {
		return parseDouble(dbObject.get("averageDocumentationReadability")+"", 0.0d);
	}
	
	public DocumentationHistoricReadability setAverageDocumentationReadability(double averageDocumentationReadability) {
		dbObject.put("averageDocumentationReadability", averageDocumentationReadability);
		notifyChanged();
		return this;
	}
	
	
	
	
}