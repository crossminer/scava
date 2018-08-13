package org.eclipse.scava.platform.analysis.data.model;

import com.googlecode.pongo.runtime.*;
import java.util.*;
import com.mongodb.*;

public class ProjectMetricProviderCollection extends PongoCollection<ProjectMetricProvider> {
	
	public ProjectMetricProviderCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("metricProviderId");
		createIndex("projectId");
	}
	
	public Iterable<ProjectMetricProvider> findById(String id) {
		return new IteratorIterable<ProjectMetricProvider>(new PongoCursorIterator<ProjectMetricProvider>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ProjectMetricProvider> findByMetricProviderId(String q) {
		return new IteratorIterable<ProjectMetricProvider>(new PongoCursorIterator<ProjectMetricProvider>(this, dbCollection.find(new BasicDBObject("metricProviderId", q + ""))));
	}
	
	public ProjectMetricProvider findOneByMetricProviderId(String q) {
		ProjectMetricProvider projectMetricProvider = (ProjectMetricProvider) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("metricProviderId", q + "")));
		if (projectMetricProvider != null) {
			projectMetricProvider.setPongoCollection(this);
		}
		return projectMetricProvider;
	}
	

	public long countByMetricProviderId(String q) {
		return dbCollection.count(new BasicDBObject("metricProviderId", q + ""));
	}
	public Iterable<ProjectMetricProvider> findByProjectId(String q) {
		return new IteratorIterable<ProjectMetricProvider>(new PongoCursorIterator<ProjectMetricProvider>(this, dbCollection.find(new BasicDBObject("projectId", q + ""))));
	}
	
	public ProjectMetricProvider findOneByProjectId(String q) {
		ProjectMetricProvider projectMetricProvider = (ProjectMetricProvider) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("projectId", q + "")));
		if (projectMetricProvider != null) {
			projectMetricProvider.setPongoCollection(this);
		}
		return projectMetricProvider;
	}
	

	public long countByProjectId(String q) {
		return dbCollection.count(new BasicDBObject("projectId", q + ""));
	}
	
	@Override
	public Iterator<ProjectMetricProvider> iterator() {
		return new PongoCursorIterator<ProjectMetricProvider>(this, dbCollection.find());
	}
	
	public void add(ProjectMetricProvider projectMetricProvider) {
		super.add(projectMetricProvider);
	}
	
	public void remove(ProjectMetricProvider projectMetricProvider) {
		super.remove(projectMetricProvider);
	}
	
}