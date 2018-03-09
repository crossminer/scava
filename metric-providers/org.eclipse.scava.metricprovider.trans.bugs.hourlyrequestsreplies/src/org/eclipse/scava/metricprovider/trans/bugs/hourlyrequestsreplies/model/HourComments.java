/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class HourComments extends Pongo {
	
	
	
	public HourComments() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		HOUR.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		NUMBEROFCOMMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		NUMBEROFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		NUMBEROFREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		PERCENTAGEOFCOMMENTS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		PERCENTAGEOFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
		PERCENTAGEOFREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFCOMMENTS = new NumericalQueryProducer("numberOfComments");
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer PERCENTAGEOFCOMMENTS = new NumericalQueryProducer("percentageOfComments");
	public static NumericalQueryProducer PERCENTAGEOFREQUESTS = new NumericalQueryProducer("percentageOfRequests");
	public static NumericalQueryProducer PERCENTAGEOFREPLIES = new NumericalQueryProducer("percentageOfReplies");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public HourComments setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public HourComments setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public HourComments setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public HourComments setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public HourComments setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public float getPercentageOfComments() {
		return parseFloat(dbObject.get("percentageOfComments")+"", 0.0f);
	}
	
	public HourComments setPercentageOfComments(float percentageOfComments) {
		dbObject.put("percentageOfComments", percentageOfComments);
		notifyChanged();
		return this;
	}
	public float getPercentageOfRequests() {
		return parseFloat(dbObject.get("percentageOfRequests")+"", 0.0f);
	}
	
	public HourComments setPercentageOfRequests(float percentageOfRequests) {
		dbObject.put("percentageOfRequests", percentageOfRequests);
		notifyChanged();
		return this;
	}
	public float getPercentageOfReplies() {
		return parseFloat(dbObject.get("percentageOfReplies")+"", 0.0f);
	}
	
	public HourComments setPercentageOfReplies(float percentageOfReplies) {
		dbObject.put("percentageOfReplies", percentageOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}
