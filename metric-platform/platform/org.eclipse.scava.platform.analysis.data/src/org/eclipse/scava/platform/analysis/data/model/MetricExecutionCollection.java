package org.eclipse.scava.platform.analysis.data.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class MetricExecutionCollection extends PongoCollection<MetricExecution> {
	
	public MetricExecutionCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("metricProviderId");
		createIndex("projectId");
	}
	
	public Iterable<MetricExecution> findById(String id) {
		return new IteratorIterable<MetricExecution>(new PongoCursorIterator<MetricExecution>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<MetricExecution> findByMetricProviderId(String q) {
		return new IteratorIterable<MetricExecution>(new PongoCursorIterator<MetricExecution>(this, dbCollection.find(new BasicDBObject("metricProviderId", q + ""))));
	}
	
	public MetricExecution findOneByMetricProviderId(String q) {
		MetricExecution metricExecution = (MetricExecution) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("metricProviderId", q + "")));
		if (metricExecution != null) {
			metricExecution.setPongoCollection(this);
		}
		return metricExecution;
	}
	

	public long countByMetricProviderId(String q) {
		return dbCollection.count(new BasicDBObject("metricProviderId", q + ""));
	}
	public Iterable<MetricExecution> findByProjectId(String q) {
		return new IteratorIterable<MetricExecution>(new PongoCursorIterator<MetricExecution>(this, dbCollection.find(new BasicDBObject("projectId", q + ""))));
	}
	
	public MetricExecution findOneByProjectId(String q) {
		MetricExecution metricExecution = (MetricExecution) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("projectId", q + "")));
		if (metricExecution != null) {
			metricExecution.setPongoCollection(this);
		}
		return metricExecution;
	}
	

	public long countByProjectId(String q) {
		return dbCollection.count(new BasicDBObject("projectId", q + ""));
	}
	
	@Override
	public Iterator<MetricExecution> iterator() {
		return new PongoCursorIterator<MetricExecution>(this, dbCollection.find());
	}
	
	public void add(MetricExecution metricExecution) {
		super.add(metricExecution);
	}
	
	public void remove(MetricExecution metricExecution) {
		super.remove(metricExecution);
	}
	
}