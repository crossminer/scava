/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform;

import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;
import com.mongodb.Mongo;

public class MetricsRepository {
	
	protected Project project;
	protected Mongo mongo;
	protected DB db;
	
	public MetricsRepository(Project project, Mongo mongo) {
		super();
		this.project = project;
		this.mongo = mongo;
		this.db = mongo.getDB(project.getShortName());
	}
	
	public DB getDb() {
		return db;
	}
	
	/*
	@SuppressWarnings("rawtypes")
	public List<MetricCollection> getMetricCollections() {
		throw new UnsupportedOperationException();
	}
	
	@SuppressWarnings("rawtypes")
	public MetricCollection getMetricCollection(String name) {
		return new MetricCollection(db.getCollection(name));
	}
	
	@SuppressWarnings("rawtypes")
	public List<MetricCollection> getMetricCollections(IMetricProvider metricProvider) {
		ArrayList<MetricCollection> metricCollections = new ArrayList<MetricCollection>();
		for (String metricCollectionName : metricProvider.getMetricCollectionNames(mongo, project)) {
			metricCollections.add(getMetricCollection(metricCollectionName));
		}
		return metricCollections;
	}*/
}
