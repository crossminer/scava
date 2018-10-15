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

public class AnalysisTaskCollection extends PongoCollection<AnalysisTask> {
	
	public AnalysisTaskCollection(DBCollection dbCollection) {
		super(dbCollection);
		createIndex("analysisTaskId");
	}
	
	public Iterable<AnalysisTask> findById(String id) {
		return new IteratorIterable<AnalysisTask>(new PongoCursorIterator<AnalysisTask>(this, dbCollection.find(new BasicDBObject("_id", id))));
	}
	
	public Iterable<AnalysisTask> findByAnalysisTaskId(String q) {
		return new IteratorIterable<AnalysisTask>(new PongoCursorIterator<AnalysisTask>(this, dbCollection.find(new BasicDBObject("analysisTaskId", q + ""))));
	}
	
	public AnalysisTask findOneByAnalysisTaskId(String q) {
		AnalysisTask analysisTask = (AnalysisTask) PongoFactory.getInstance().createPongo(dbCollection.findOne(new BasicDBObject("analysisTaskId", q + "")));
		if (analysisTask != null) {
			analysisTask.setPongoCollection(this);
		}
		return analysisTask;
	}
	

	public long countByAnalysisTaskId(String q) {
		return dbCollection.count(new BasicDBObject("analysisTaskId", q + ""));
	}
	
	@Override
	public Iterator<AnalysisTask> iterator() {
		return new PongoCursorIterator<AnalysisTask>(this, dbCollection.find());
	}
	
	public void add(AnalysisTask analysisTask) {
		super.add(analysisTask);
	}
	
	public void remove(AnalysisTask analysisTask) {
		super.remove(analysisTask);
	}
	
}