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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.sentiment.model;

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