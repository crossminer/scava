package org.eclipse.scava.metricprovider.historic.documentation.readability.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class DocumentationReadabilityHistoricMetric extends Pongo {
	
	protected List<DocumentationHistoricReadability> documentationReadability = null;
	protected List<DocumentationEntryHistoricReadability> documentationEntriesReadability = null;
	
	
	public DocumentationReadabilityHistoricMetric() { 
		super();
		dbObject.put("documentationReadability", new BasicDBList());
		dbObject.put("documentationEntriesReadability", new BasicDBList());
	}
	
	
	
	
	
	public List<DocumentationHistoricReadability> getDocumentationReadability() {
		if (documentationReadability == null) {
			documentationReadability = new PongoList<DocumentationHistoricReadability>(this, "documentationReadability", true);
		}
		return documentationReadability;
	}
	public List<DocumentationEntryHistoricReadability> getDocumentationEntriesReadability() {
		if (documentationEntriesReadability == null) {
			documentationEntriesReadability = new PongoList<DocumentationEntryHistoricReadability>(this, "documentationEntriesReadability", true);
		}
		return documentationEntriesReadability;
	}
	
	
}