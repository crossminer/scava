package org.eclipse.scava.metricprovider.trans.documentation.license.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationLicenseTransMetric extends PongoDB {
	
	public DocumentationLicenseTransMetric() {}
	
	public DocumentationLicenseTransMetric(DB db) {
		setDb(db);
	}
	
	protected DocumentationEntryLicenseCollection documentationEntriesLicense = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public DocumentationEntryLicenseCollection getDocumentationEntriesLicense() {
		return documentationEntriesLicense;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		documentationEntriesLicense = new DocumentationEntryLicenseCollection(db.getCollection("DocumentationLicenseTransMetric.documentationEntriesLicense"));
		pongoCollections.add(documentationEntriesLicense);
	}
}