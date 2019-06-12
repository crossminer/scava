package org.eclipse.scava.metricprovider.indexing.commits.model;

import com.googlecode.pongo.runtime.PongoDB;
// protected region custom-imports on begin
// protected region custom-imports end
import com.mongodb.DB;

public class CommitsIndexingMetric extends PongoDB {
	
	public CommitsIndexingMetric() {}
	
	public CommitsIndexingMetric(DB db) {
		setDb(db);
	}
	
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
	}
}