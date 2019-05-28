package org.eclipse.scava.metricprovider.trans.indexing.preparation.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class IndexPrepTransMetric extends PongoDB {
	
	public IndexPrepTransMetric() {}
	
	public IndexPrepTransMetric(DB db) {
		setDb(db);
	}
	
	protected ExecutedMetricProvidersCollection executedMetricProviders = null;
	
	
	
	public ExecutedMetricProvidersCollection getExecutedMetricProviders() {
		return executedMetricProviders;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		executedMetricProviders = new ExecutedMetricProvidersCollection(db.getCollection("IndexPrepTransMetric.executedMetricProviders"));
		pongoCollections.add(executedMetricProviders);
	}
}