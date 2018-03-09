/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyNewsgroupData extends Pongo {
	
	
	
	public DailyNewsgroupData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model.DailyNewsgroupData");
		NUMBEROFUNANSWEREDTHREADS.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model.DailyNewsgroupData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer NUMBEROFUNANSWEREDTHREADS = new NumericalQueryProducer("numberOfUnansweredThreads");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public DailyNewsgroupData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getNumberOfUnansweredThreads() {
		return parseInteger(dbObject.get("numberOfUnansweredThreads")+"", 0);
	}
	
	public DailyNewsgroupData setNumberOfUnansweredThreads(int numberOfUnansweredThreads) {
		dbObject.put("numberOfUnansweredThreads", numberOfUnansweredThreads);
		notifyChanged();
		return this;
	}
	
	
	
	
}
