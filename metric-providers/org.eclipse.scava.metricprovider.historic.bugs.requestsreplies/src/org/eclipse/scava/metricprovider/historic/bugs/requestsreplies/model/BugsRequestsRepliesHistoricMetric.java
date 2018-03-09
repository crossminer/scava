/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsRequestsRepliesHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackerData> bugs = null;
	
	
	public BugsRequestsRepliesHistoricMetric() { 
		super();
		dbObject.put("bugs", new BasicDBList());
		NUMBEROFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.BugsRequestsRepliesHistoricMetric");
		NUMBEROFREPLIES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.BugsRequestsRepliesHistoricMetric");
		CUMULATIVENUMBEROFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.BugsRequestsRepliesHistoricMetric");
		CUMULATIVENUMBEROFREPLIES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.BugsRequestsRepliesHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREQUESTS = new NumericalQueryProducer("cumulativeNumberOfRequests");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREPLIES = new NumericalQueryProducer("cumulativeNumberOfReplies");
	
	
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public BugsRequestsRepliesHistoricMetric setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public BugsRequestsRepliesHistoricMetric setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfRequests() {
		return parseInteger(dbObject.get("cumulativeNumberOfRequests")+"", 0);
	}
	
	public BugsRequestsRepliesHistoricMetric setCumulativeNumberOfRequests(int cumulativeNumberOfRequests) {
		dbObject.put("cumulativeNumberOfRequests", cumulativeNumberOfRequests);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfReplies() {
		return parseInteger(dbObject.get("cumulativeNumberOfReplies")+"", 0);
	}
	
	public BugsRequestsRepliesHistoricMetric setCumulativeNumberOfReplies(int cumulativeNumberOfReplies) {
		dbObject.put("cumulativeNumberOfReplies", cumulativeNumberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugTrackerData> getBugs() {
		if (bugs == null) {
			bugs = new PongoList<DailyBugTrackerData>(this, "bugs", true);
		}
		return bugs;
	}
	
	
}
