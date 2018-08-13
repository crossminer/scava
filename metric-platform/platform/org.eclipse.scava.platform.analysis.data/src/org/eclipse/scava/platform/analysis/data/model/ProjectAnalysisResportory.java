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
	protected ProjectMetricProviderCollection metricProviders = null;
	
	
	
	public ProjectAnalysisCollection getProjects() {
		return projects;
	}
	
	public AnalysisTaskCollection getAnalysisTasks() {
		return analysisTasks;
	}
	
	public ProjectMetricProviderCollection getMetricProviders() {
		return metricProviders;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		projects = new ProjectAnalysisCollection(db.getCollection("projects"));
		pongoCollections.add(projects);
		analysisTasks = new AnalysisTaskCollection(db.getCollection("analysisTasks"));
		pongoCollections.add(analysisTasks);
		metricProviders = new ProjectMetricProviderCollection(db.getCollection("metricProviders"));
		pongoCollections.add(metricProviders);
	}
}