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