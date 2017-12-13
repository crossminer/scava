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
package org.eclipse.crossmeter.metricprovider.trans.bugs.newbugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerData extends Pongo {
	
	
	
	public BugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.newbugs.model.BugTrackerData");
		NUMBEROFBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.newbugs.model.BugTrackerData");
		CUMULATIVENUMBEROFBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.newbugs.model.BugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer CUMULATIVENUMBEROFBUGS = new NumericalQueryProducer("cumulativeNumberOfBugs");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugTrackerData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfBugs() {
		return parseInteger(dbObject.get("cumulativeNumberOfBugs")+"", 0);
	}
	
	public BugTrackerData setCumulativeNumberOfBugs(int cumulativeNumberOfBugs) {
		dbObject.put("cumulativeNumberOfBugs", cumulativeNumberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}