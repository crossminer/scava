/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.dailyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsDailyRequestsRepliesTransMetric extends PongoDB {
	
	public NewsgroupsDailyRequestsRepliesTransMetric() {}
	
	public NewsgroupsDailyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected DayArticlesCollection dayArticles = null;
	
	
	
	public DayArticlesCollection getDayArticles() {
		return dayArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		dayArticles = new DayArticlesCollection(db.getCollection("NewsgroupsDailyRequestsRepliesTransMetric.dayArticles"));
		pongoCollections.add(dayArticles);
	}
}
