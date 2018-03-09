/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.severityclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class SeverityClassificationTransMetric extends PongoDB {
	
	public SeverityClassificationTransMetric() {}
	
	public SeverityClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerBugsDataCollection bugTrackerBugs = null;
	protected NewsgroupArticleDataCollection newsgroupArticles = null;
	protected NewsgroupThreadDataCollection newsgroupThreads = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerBugsDataCollection getBugTrackerBugs() {
		return bugTrackerBugs;
	}
	
	public NewsgroupArticleDataCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public NewsgroupThreadDataCollection getNewsgroupThreads() {
		return newsgroupThreads;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerBugs = new BugTrackerBugsDataCollection(db.getCollection("SeverityClassificationTransMetric.bugTrackerBugs"));
		pongoCollections.add(bugTrackerBugs);
		newsgroupArticles = new NewsgroupArticleDataCollection(db.getCollection("SeverityClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		newsgroupThreads = new NewsgroupThreadDataCollection(db.getCollection("SeverityClassificationTransMetric.newsgroupThreads"));
		pongoCollections.add(newsgroupThreads);
	}
}
