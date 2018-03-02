/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupsResponseTimeHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsResponseTimeHistoricMetric() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		AVGRESPONSETIME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		AVGRESPONSETIMEFORMATTED.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		THREADSCONSIDERED.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		CUMULATIVEAVGRESPONSETIME.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		CUMULATIVEAVGRESPONSETIMEFORMATTED.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
		CUMULATIVETHREADSCONSIDERED.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.responsetime.model.NewsgroupsResponseTimeHistoricMetric");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer AVGRESPONSETIME = new NumericalQueryProducer("avgResponseTime");
	public static StringQueryProducer AVGRESPONSETIMEFORMATTED = new StringQueryProducer("avgResponseTimeFormatted"); 
	public static NumericalQueryProducer THREADSCONSIDERED = new NumericalQueryProducer("threadsConsidered");
	public static NumericalQueryProducer CUMULATIVEAVGRESPONSETIME = new NumericalQueryProducer("cumulativeAvgResponseTime");
	public static StringQueryProducer CUMULATIVEAVGRESPONSETIMEFORMATTED = new StringQueryProducer("cumulativeAvgResponseTimeFormatted"); 
	public static NumericalQueryProducer CUMULATIVETHREADSCONSIDERED = new NumericalQueryProducer("cumulativeThreadsConsidered");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupsResponseTimeHistoricMetric setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public long getAvgResponseTime() {
		return parseLong(dbObject.get("avgResponseTime")+"", 0);
	}
	
	public NewsgroupsResponseTimeHistoricMetric setAvgResponseTime(long avgResponseTime) {
		dbObject.put("avgResponseTime", avgResponseTime);
		notifyChanged();
		return this;
	}
	public String getAvgResponseTimeFormatted() {
		return parseString(dbObject.get("avgResponseTimeFormatted")+"", "");
	}
	
	public NewsgroupsResponseTimeHistoricMetric setAvgResponseTimeFormatted(String avgResponseTimeFormatted) {
		dbObject.put("avgResponseTimeFormatted", avgResponseTimeFormatted);
		notifyChanged();
		return this;
	}
	public int getThreadsConsidered() {
		return parseInteger(dbObject.get("threadsConsidered")+"", 0);
	}
	
	public NewsgroupsResponseTimeHistoricMetric setThreadsConsidered(int threadsConsidered) {
		dbObject.put("threadsConsidered", threadsConsidered);
		notifyChanged();
		return this;
	}
	public long getCumulativeAvgResponseTime() {
		return parseLong(dbObject.get("cumulativeAvgResponseTime")+"", 0);
	}
	
	public NewsgroupsResponseTimeHistoricMetric setCumulativeAvgResponseTime(long cumulativeAvgResponseTime) {
		dbObject.put("cumulativeAvgResponseTime", cumulativeAvgResponseTime);
		notifyChanged();
		return this;
	}
	public String getCumulativeAvgResponseTimeFormatted() {
		return parseString(dbObject.get("cumulativeAvgResponseTimeFormatted")+"", "");
	}
	
	public NewsgroupsResponseTimeHistoricMetric setCumulativeAvgResponseTimeFormatted(String cumulativeAvgResponseTimeFormatted) {
		dbObject.put("cumulativeAvgResponseTimeFormatted", cumulativeAvgResponseTimeFormatted);
		notifyChanged();
		return this;
	}
	public int getCumulativeThreadsConsidered() {
		return parseInteger(dbObject.get("cumulativeThreadsConsidered")+"", 0);
	}
	
	public NewsgroupsResponseTimeHistoricMetric setCumulativeThreadsConsidered(int cumulativeThreadsConsidered) {
		dbObject.put("cumulativeThreadsConsidered", cumulativeThreadsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}
