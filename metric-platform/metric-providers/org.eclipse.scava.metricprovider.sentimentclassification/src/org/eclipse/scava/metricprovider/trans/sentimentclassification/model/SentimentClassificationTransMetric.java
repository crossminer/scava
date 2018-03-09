/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.sentimentclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class SentimentClassificationTransMetric extends PongoDB {
	
	public SentimentClassificationTransMetric() {}
	
	public SentimentClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentsDataCollection bugTrackerComments = null;
	protected NewsgroupArticlesDataCollection newsgroupArticles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentsDataCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticlesDataCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentsDataCollection(db.getCollection("SentimentClassificationTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticlesDataCollection(db.getCollection("SentimentClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
	}
}
