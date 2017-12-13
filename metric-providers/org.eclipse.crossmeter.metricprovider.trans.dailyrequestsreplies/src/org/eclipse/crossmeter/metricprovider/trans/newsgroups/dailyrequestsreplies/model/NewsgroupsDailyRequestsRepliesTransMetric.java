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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.dailyrequestsreplies.model;

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