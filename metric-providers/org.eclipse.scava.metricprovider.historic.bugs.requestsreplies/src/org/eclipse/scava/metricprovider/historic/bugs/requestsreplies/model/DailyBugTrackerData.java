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

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugTrackerData extends Pongo {
	
	
	
	public DailyBugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.DailyBugTrackerData");
		NUMBEROFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.DailyBugTrackerData");
		NUMBEROFREPLIES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.DailyBugTrackerData");
		CUMULATIVENUMBEROFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.DailyBugTrackerData");
		CUMULATIVENUMBEROFREPLIES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.DailyBugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREQUESTS = new NumericalQueryProducer("cumulativeNumberOfRequests");
	public static NumericalQueryProducer CUMULATIVENUMBEROFREPLIES = new NumericalQueryProducer("cumulativeNumberOfReplies");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public DailyBugTrackerData setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public DailyBugTrackerData setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfRequests() {
		return parseInteger(dbObject.get("cumulativeNumberOfRequests")+"", 0);
	}
	
	public DailyBugTrackerData setCumulativeNumberOfRequests(int cumulativeNumberOfRequests) {
		dbObject.put("cumulativeNumberOfRequests", cumulativeNumberOfRequests);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfReplies() {
		return parseInteger(dbObject.get("cumulativeNumberOfReplies")+"", 0);
	}
	
	public DailyBugTrackerData setCumulativeNumberOfReplies(int cumulativeNumberOfReplies) {
		dbObject.put("cumulativeNumberOfReplies", cumulativeNumberOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}
