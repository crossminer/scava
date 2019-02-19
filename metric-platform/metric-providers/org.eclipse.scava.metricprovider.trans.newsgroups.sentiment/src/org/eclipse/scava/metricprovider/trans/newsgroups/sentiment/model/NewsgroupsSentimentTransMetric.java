/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsSentimentTransMetric extends PongoDB {
	
	public NewsgroupsSentimentTransMetric() {}
	
	public NewsgroupsSentimentTransMetric(DB db) {
		setDb(db);
	}
	
	protected ThreadStatisticsCollection threads = null;
	
	
	
	public ThreadStatisticsCollection getThreads() {
		return threads;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		threads = new ThreadStatisticsCollection(db.getCollection("NewsgroupsSentimentTransMetric.threads"));
		pongoCollections.add(threads);
	}
}
