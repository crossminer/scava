/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.bugs.requestsreplies.model;

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