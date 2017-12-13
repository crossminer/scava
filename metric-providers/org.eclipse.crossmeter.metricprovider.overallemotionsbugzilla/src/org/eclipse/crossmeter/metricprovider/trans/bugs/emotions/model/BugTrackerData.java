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
package org.eclipse.crossmeter.metricprovider.trans.bugs.emotions.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerData extends Pongo {
	
	
	
	public BugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.emotions.model.BugTrackerData");
		NUMBEROFCOMMENTS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.emotions.model.BugTrackerData");
		CUMULATIVENUMBEROFCOMMENTS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.emotions.model.BugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFCOMMENTS = new NumericalQueryProducer("numberOfComments");
	public static NumericalQueryProducer CUMULATIVENUMBEROFCOMMENTS = new NumericalQueryProducer("cumulativeNumberOfComments");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfComments() {
		return parseInteger(dbObject.get("numberOfComments")+"", 0);
	}
	
	public BugTrackerData setNumberOfComments(int numberOfComments) {
		dbObject.put("numberOfComments", numberOfComments);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfComments() {
		return parseInteger(dbObject.get("cumulativeNumberOfComments")+"", 0);
	}
	
	public BugTrackerData setCumulativeNumberOfComments(int cumulativeNumberOfComments) {
		dbObject.put("cumulativeNumberOfComments", cumulativeNumberOfComments);
		notifyChanged();
		return this;
	}
	
	
	
	
}