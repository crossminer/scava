package org.eclipse.scava.metricprovider.indexing.documentation.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DocumentationIndexingMetric extends PongoDB {
	
	public DocumentationIndexingMetric() {}
	
	public DocumentationIndexingMetric(DB db) {
		setDb(db);
	}
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
	}
}