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

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsUnansweredThreadsHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public NewsgroupsUnansweredThreadsHistoricMetric() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
		NUMBEROFUNANSWEREDTHREADS.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model.NewsgroupsUnansweredThreadsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFUNANSWEREDTHREADS = new NumericalQueryProducer("numberOfUnansweredThreads");
	
	
	public int getNumberOfUnansweredThreads() {
		return parseInteger(dbObject.get("numberOfUnansweredThreads")+"", 0);
	}
	
	public NewsgroupsUnansweredThreadsHistoricMetric setNumberOfUnansweredThreads(int numberOfUnansweredThreads) {
		dbObject.put("numberOfUnansweredThreads", numberOfUnansweredThreads);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyNewsgroupData> getNewsgroups() {
		if (newsgroups == null) {
			newsgroups = new PongoList<DailyNewsgroupData>(this, "newsgroups", true);
		}
		return newsgroups;
	}
	
	
}
