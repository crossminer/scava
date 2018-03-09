/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.threads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupData");
		THREADS.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupData");
		PREVIOUSTHREADS.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer THREADS = new NumericalQueryProducer("threads");
	public static NumericalQueryProducer PREVIOUSTHREADS = new NumericalQueryProducer("previousThreads");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getThreads() {
		return parseInteger(dbObject.get("threads")+"", 0);
	}
	
	public NewsgroupData setThreads(int threads) {
		dbObject.put("threads", threads);
		notifyChanged();
		return this;
	}
	public int getPreviousThreads() {
		return parseInteger(dbObject.get("previousThreads")+"", 0);
	}
	
	public NewsgroupData setPreviousThreads(int previousThreads) {
		dbObject.put("previousThreads", previousThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}
