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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class NewsgroupsRequestsRepliesAverageHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric() { 
		super();
		AVERAGEARTICLESPERDAY.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric");
		AVERAGEREQUESTSPERDAY.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric");
		AVERAGEREPLIESPERDAY.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.average.model.NewsgroupsRequestsRepliesAverageHistoricMetric");
	}
	
	public static NumericalQueryProducer AVERAGEARTICLESPERDAY = new NumericalQueryProducer("averageArticlesPerDay");
	public static NumericalQueryProducer AVERAGEREQUESTSPERDAY = new NumericalQueryProducer("averageRequestsPerDay");
	public static NumericalQueryProducer AVERAGEREPLIESPERDAY = new NumericalQueryProducer("averageRepliesPerDay");
	
	
	public float getAverageArticlesPerDay() {
		return parseFloat(dbObject.get("averageArticlesPerDay")+"", 0.0f);
	}
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric setAverageArticlesPerDay(float averageArticlesPerDay) {
		dbObject.put("averageArticlesPerDay", averageArticlesPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerDay() {
		return parseFloat(dbObject.get("averageRequestsPerDay")+"", 0.0f);
	}
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric setAverageRequestsPerDay(float averageRequestsPerDay) {
		dbObject.put("averageRequestsPerDay", averageRequestsPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerDay() {
		return parseFloat(dbObject.get("averageRepliesPerDay")+"", 0.0f);
	}
	
	public NewsgroupsRequestsRepliesAverageHistoricMetric setAverageRepliesPerDay(float averageRepliesPerDay) {
		dbObject.put("averageRepliesPerDay", averageRepliesPerDay);
		notifyChanged();
		return this;
	}
	
	
	
	
}