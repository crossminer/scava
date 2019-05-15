package org.eclipse.scava.metricprovider.historic.documentation.readability.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


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