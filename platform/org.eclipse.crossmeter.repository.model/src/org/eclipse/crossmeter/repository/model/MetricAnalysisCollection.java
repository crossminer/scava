package org.eclipse.crossmeter.repository.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MetricAnalysisCollection extends PongoCollection<MetricAnalysis> {
	
	public MetricAnalysisCollection(DBCollection dbCollection) {
		super(dbCollection);
	}
	
	public Iterable<MetricAnalysis> findById(String id) {
		return new IteratorIterable<MetricAnalysis>(new PongoCursorIterator<MetricAnalysis>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	
	@Override
	public Iterator<MetricAnalysis> iterator() {
		return new PongoCursorIterator<MetricAnalysis>(this, dbCollection.find());
	}
	
	public void add(MetricAnalysis metricAnalysis) {
		super.add(metricAnalysis);
	}
	
	public void remove(MetricAnalysis metricAnalysis) {
		super.remove(metricAnalysis);
	}
	
}