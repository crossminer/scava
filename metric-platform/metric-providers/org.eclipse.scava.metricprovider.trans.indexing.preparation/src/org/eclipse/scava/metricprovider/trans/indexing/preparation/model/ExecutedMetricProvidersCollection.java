package org.eclipse.scava.metricprovider.trans.indexing.preparation.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ExecutedMetricProvidersCollection extends PongoCollection<ExecutedMetricProviders> {
	
	public ExecutedMetricProvidersCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<ExecutedMetricProviders> findById(String id) {
		return new IteratorIterable<ExecutedMetricProviders>(new PongoCursorIterator<ExecutedMetricProviders>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<ExecutedMetricProviders> iterator() {
		return new PongoCursorIterator<ExecutedMetricProviders>(this, dbCollection.find());
	}
	
	public void add(ExecutedMetricProviders executedMetricProviders) {
		super.add(executedMetricProviders);
	}
	
	public void remove(ExecutedMetricProviders executedMetricProviders) {
		super.remove(executedMetricProviders);
	}
	
}