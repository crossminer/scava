/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform;

import org.eclipse.crossmeter.repository.model.Project;

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
