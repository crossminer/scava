/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsNewThreadsHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public NewsgroupsNewThreadsHistoricMetric() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
		NUMBEROFNEWTHREADS.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric");
		CUMULATIVENUMBEROFNEWTHREADS.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFNEWTHREADS = new NumericalQueryProducer("numberOfNewThreads");
	public static NumericalQueryProducer CUMULATIVENUMBEROFNEWTHREADS = new NumericalQueryProducer("cumulativeNumberOfNewThreads");
	
	
	public int getNumberOfNewThreads() {
		return parseInteger(dbObject.get("numberOfNewThreads")+"", 0);
	}
	
	public NewsgroupsNewThreadsHistoricMetric setNumberOfNewThreads(int numberOfNewThreads) {
		dbObject.put("numberOfNewThreads", numberOfNewThreads);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfNewThreads() {
		return parseInteger(dbObject.get("cumulativeNumberOfNewThreads")+"", 0);
	}
	
	public NewsgroupsNewThreadsHistoricMetric setCumulativeNumberOfNewThreads(int cumulativeNumberOfNewThreads) {
		dbObject.put("cumulativeNumberOfNewThreads", cumulativeNumberOfNewThreads);
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
