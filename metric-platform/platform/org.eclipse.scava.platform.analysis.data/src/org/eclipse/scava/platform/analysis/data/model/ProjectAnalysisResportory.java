/*******************************************************************************
 * Copyright (c) 2018 Softeam
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.analysis.data.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectAnalysisResportory extends PongoDB {
	
	public ProjectAnalysisResportory() {}
	
	public ProjectAnalysisResportory(DB db) {
		setDb(db);
	}
	
	protected ProjectAnalysisCollection projects = null;
	protected AnalysisTaskCollection analysisTasks = null;
	protected MetricExecutionCollection metricExecutions = null;
	protected MetricProviderCollection metricProviders = null;
	protected WorkerCollection workers = null;
	
	
	
	public ProjectAnalysisCollection getProjects() {
		return projects;
	}
	
	public AnalysisTaskCollection getAnalysisTasks() {
		return analysisTasks;
	}
	
	public MetricExecutionCollection getMetricExecutions() {
		return metricExecutions;
	}
	
	public MetricProviderCollection getMetricProviders() {
		return metricProviders;
	}
	
	public WorkerCollection getWorkers() {
		return workers;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new ProjectAnalysisCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		analysisTasks = new AnalysisTaskCollection(db.getCollection("analysisTasks"));
		pongoCollections.add(analysisTasks);
		metricExecutions = new MetricExecutionCollection(db.getCollection("metricExecutions"));
		pongoCollections.add(metricExecutions);
		metricProviders = new MetricProviderCollection(db.getCollection("metricProviders"));
		pongoCollections.add(metricProviders);
		workers = new WorkerCollection(db.getCollection("workers"));
		pongoCollections.add(workers);
	}
}