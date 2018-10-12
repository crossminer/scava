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
import java.util.*;
import com.mongodb.*;

public class ProjectAnalysisCollection extends PongoCollection<ProjectAnalysis> {
	
	public ProjectAnalysisCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("projectId");
	}
	
	public Iterable<ProjectAnalysis> findById(String id) {
		return new IteratorIterable<ProjectAnalysis>(new PongoCursorIterator<ProjectAnalysis>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<ProjectAnalysis> findByProjectId(String q) {
		return new IteratorIterable<ProjectAnalysis>(new PongoCursorIterator<ProjectAnalysis>(this, dbCollection.find(new BasicDBObject("projectId", q + ""))));
	}
	
	public ProjectAnalysis findOneByProjectId(String q) {
		ProjectAnalysis projectAnalysis = (ProjectAnalysis) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("projectId", q + "")));
		if (projectAnalysis != null) {
			projectAnalysis.setPongoCollection(this);
		}
		return projectAnalysis;
	}
	/*
	 * Find by AnalysisTaskId
	 */
	public AnalysisTask findOneByAnalysisTaskId(String q) {
		AnalysisTask analysisTask = (AnalysisTask) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("analysisTaskId", q + "")));
		if (analysisTask != null) {
			analysisTask.setPongoCollection(this);
		}
		return analysisTask;
	}
	

	public long countByProjectId(String q) {
		return dbCollection.count(new BasicDBObject("projectId", q + ""));
	}
	
	@Override
	public Iterator<ProjectAnalysis> iterator() {
		return new PongoCursorIterator<ProjectAnalysis>(this, dbCollection.find());
	}
	
	public void add(ProjectAnalysis projectAnalysis) {
		super.add(projectAnalysis);
	}
	
	public void remove(ProjectAnalysis projectAnalysis) {
		super.remove(projectAnalysis);
	}
	
}