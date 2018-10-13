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