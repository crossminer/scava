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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsHourlyRequestsRepliesTransMetric extends PongoDB {
	
	public NewsgroupsHourlyRequestsRepliesTransMetric() {}
	
	public NewsgroupsHourlyRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected HourArticlesCollection hourArticles = null;
	
	
	
	public HourArticlesCollection getHourArticles() {
		return hourArticles;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hourArticles = new HourArticlesCollection(db.getCollection("NewsgroupsHourlyRequestsRepliesTransMetric.hourArticles"));
		pongoCollections.add(hourArticles);
	}
}