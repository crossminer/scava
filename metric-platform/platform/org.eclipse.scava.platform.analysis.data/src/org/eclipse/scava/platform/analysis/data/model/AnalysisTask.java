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


public class AnalysisTask extends Pongo {
	
	protected List<MetricExecution> metricExecutions = null;
	protected ProjectAnalysis project = null;
	protected SchedulingInformation scheduling = null;
	
	
	public AnalysisTask() { 
		super();
		dbObject.put("project", new BasicDBObject());
		dbObject.put("scheduling", new SchedulingInformation().getDbObject());
		dbObject.put("metricExecutions", new BasicDBList());
		ANALYSISTASKID.setOwningType("org.eclipse.scava.platform.analysis.data.model.AnalysisTask");
		LABEL.setOwningType("org.eclipse.scava.platform.analysis.data.model.AnalysisTask");
		TYPE.setOwningType("org.eclipse.scava.platform.analysis.data.model.AnalysisTask");
		STARTDATE.setOwningType("org.eclipse.scava.platform.analysis.data.model.AnalysisTask");
		ENDDATE.setOwningType("org.eclipse.scava.platform.analysis.data.model.AnalysisTask");
	}
	
	public static StringQueryProducer ANALYSISTASKID = new StringQueryProducer("analysisTaskId"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static StringQueryProducer TYPE = new StringQueryProducer("type"); 
	public static StringQueryProducer STARTDATE = new StringQueryProducer("startDate"); 
	public static StringQueryProducer ENDDATE = new StringQueryProducer("endDate"); 
	
	
	public String getAnalysisTaskId() {
		return parseString(dbObject.get("analysisTaskId")+"", "");
	}
	
	public AnalysisTask setAnalysisTaskId(String analysisTaskId) {
		dbObject.put("analysisTaskId", analysisTaskId);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public AnalysisTask setLabel(String label) {
		dbObject.put("label", label);
		notifyChanged();
		return this;
	}
	public String getType() {
		return parseString(dbObject.get("type")+"", "");
	}
	
	public AnalysisTask setType(String type) {
		dbObject.put("type", type);
		notifyChanged();
		return this;
	}
	public Date getStartDate() {
		return parseDate(dbObject.get("startDate")+"", null);
	}
	
	public AnalysisTask setStartDate(Date startDate) {
		dbObject.put("startDate", startDate);
		notifyChanged();
		return this;
	}
	public Date getEndDate() {
		return parseDate(dbObject.get("endDate")+"", null);
	}
	
	public AnalysisTask setEndDate(Date endDate) {
		dbObject.put("endDate", endDate);
		notifyChanged();
		return this;
	}
	
	
	public List<MetricExecution> getMetricExecutions() {
		if (metricExecutions == null) {
			metricExecutions = new PongoList<MetricExecution>(this, "metricExecutions", false);
		}
		return metricExecutions;
	}
	
	public AnalysisTask setProject(ProjectAnalysis project) {
		if (this.project != project) {
			if (project == null) {
				dbObject.put("project", new BasicDBObject());
			}
			else {
				createReference("project", project);
			}
			this.project = project;
			notifyChanged();
		}
		return this;
	}
	
	public ProjectAnalysis getProject() {
		if (project == null) {
			project = (ProjectAnalysis) resolveReference("project");
		}
		return project;
	}
	
	public SchedulingInformation getScheduling() {
		if (scheduling == null && dbObject.containsField("scheduling")) {
			scheduling = (SchedulingInformation) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("scheduling"));
			scheduling.setContainer(this);
		}
		return scheduling;
	}
	
	public AnalysisTask setScheduling(SchedulingInformation scheduling) {
		if (this.scheduling != scheduling) {
			if (scheduling == null) {
				dbObject.removeField("scheduling");
			}
			else {
				dbObject.put("scheduling", scheduling.getDbObject());
			}
			this.scheduling = scheduling;
			notifyChanged();
		}
		return this;
	}
}