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
package org.eclipse.crossmeter.metricprovider.trans.bugs.dailyrequestsreplies.model;

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