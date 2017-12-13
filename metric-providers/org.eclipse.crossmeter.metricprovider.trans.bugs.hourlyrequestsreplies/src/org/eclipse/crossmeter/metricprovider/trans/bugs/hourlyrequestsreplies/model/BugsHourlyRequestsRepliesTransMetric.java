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
package org.eclipse.crossmeter.metricprovider.trans.bugs.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsHourlyRequestsRepliesTransMetric extends PongoDB {
	
	public BugsHourlyRequestsRepliesTransMetric() {}
	
	public BugsHourlyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected HourCommentsCollection hourComments = null;
	
	
	
	public HourCommentsCollection getHourComments() {
		return hourComments;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hourComments = new HourCommentsCollection(db.getCollection("BugsHourlyRequestsRepliesTransMetric.hourComments"));
		pongoCollections.add(hourComments);
	}
}