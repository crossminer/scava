/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsDailyRequestsRepliesTransMetric extends PongoDB {
	
	public BugsDailyRequestsRepliesTransMetric() {}
	
	public BugsDailyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected DayCommentsCollection dayComments = null;
	
	
	
	public DayCommentsCollection getDayComments() {
		return dayComments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dayComments = new DayCommentsCollection(db.getCollection("BugsDailyRequestsRepliesTransMetric.dayComments"));
		pongoCollections.add(dayComments);
	}
}
