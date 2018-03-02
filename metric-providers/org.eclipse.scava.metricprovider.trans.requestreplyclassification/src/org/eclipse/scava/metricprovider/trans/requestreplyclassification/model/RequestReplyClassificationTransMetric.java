/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.requestreplyclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class RequestReplyClassificationTransMetric extends PongoDB {
	
	public RequestReplyClassificationTransMetric() {}
	
	public RequestReplyClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentsCollection bugTrackerComments = null;
	protected NewsgroupArticlesCollection newsgroupArticles = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentsCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticlesCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentsCollection(db.getCollection("RequestReplyClassificationTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticlesCollection(db.getCollection("RequestReplyClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
	}
}
