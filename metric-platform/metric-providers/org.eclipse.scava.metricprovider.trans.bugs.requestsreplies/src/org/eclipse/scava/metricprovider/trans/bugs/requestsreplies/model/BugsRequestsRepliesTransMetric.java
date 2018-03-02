/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsRequestsRepliesTransMetric extends PongoDB {
	
	public BugsRequestsRepliesTransMetric() {}
	
	public BugsRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugStatisticsCollection bugs = null;
	
	
	
	public BugStatisticsCollection getBugs() {
		return bugs;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugs = new BugStatisticsCollection(db.getCollection("BugsRequestsRepliesTransMetric.bugs"));
		pongoCollections.add(bugs);
	}
}
