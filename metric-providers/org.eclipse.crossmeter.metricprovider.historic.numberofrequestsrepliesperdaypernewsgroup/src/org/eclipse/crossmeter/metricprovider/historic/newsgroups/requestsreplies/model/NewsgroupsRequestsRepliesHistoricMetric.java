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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsRequestsRepliesHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public NewsgroupsRequestsRepliesHistoricMetric() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
		NUMBEROFREQUESTS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric");
		NUMBEROFREPLIES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric");
		CUMULATIVENUMBEROFREQUESTS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric");
		CUMULATIVENUMBEROFREPLIES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.requestsreplies.model.NewsgroupsRequestsRepliesHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREQUESTS = new NumericalQueryProducer("cumulativeNumberOfRequests");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREPLIES = new NumericalQueryProducer("cumulativeNumberOfReplies");
	
	
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public NewsgroupsRequestsRepliesHistoricMetric setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public NewsgroupsRequestsRepliesHistoricMetric setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfRequests() {
		return parseInteger(dbObject.get("cumulativeNumberOfRequests")+"", 0);
	}
	
	public NewsgroupsRequestsRepliesHistoricMetric setCumulativeNumberOfRequests(int cumulativeNumberOfRequests) {
		dbObject.put("cumulativeNumberOfRequests", cumulativeNumberOfRequests);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfReplies() {
		return parseInteger(dbObject.get("cumulativeNumberOfReplies")+"", 0);
	}
	
	public NewsgroupsRequestsRepliesHistoricMetric setCumulativeNumberOfReplies(int cumulativeNumberOfReplies) {
		dbObject.put("cumulativeNumberOfReplies", cumulativeNumberOfReplies);
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