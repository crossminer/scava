/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsThreadsRequestsRepliesTransMetric extends PongoDB {
	
	public NewsgroupsThreadsRequestsRepliesTransMetric() {}
	
	public NewsgroupsThreadsRequestsRepliesTransMetric(DB db) {
		setDb(db);
	}
	
	protected ThreadStatisticsCollection threads = null;
	
	
	
	public ThreadStatisticsCollection getThreads() {
		return threads;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		threads = new ThreadStatisticsCollection(db.getCollection("NewsgroupsThreadsRequestsRepliesTransMetric.threads"));
		pongoCollections.add(threads);
	}
}
