package org.eclipse.scava.platform.analysis.data.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class ProjectAnalysis extends Pongo {
	
	protected List<AnalysisTask> analysisTasks = null;
	
	
	public ProjectAnalysis() { 
		super();
		dbObject.put("analysisTasks", new BasicDBList());
		PROJECTID.setOwningType("org.eclipse.scava.platform.analysis.data.model.ProjectAnalysis");
	}
	
	public static StringQueryProducer PROJECTID = new StringQueryProducer("projectId"); 
	
	
	public String getProjectId() {
		return parseString(dbObject.get("projectId")+"", "");
	}
	
	public ProjectAnalysis setProjectId(String projectId) {
		dbObject.put("projectId", projectId);
		notifyChanged();
		return this;
	}
	
	
	public List<AnalysisTask> getAnalysisTasks() {
		if (analysisTasks == null) {
			analysisTasks = new PongoList<AnalysisTask>(this, "analysisTasks", false);
		}
		return analysisTasks;
	}
	
	
}