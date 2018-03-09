/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

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
