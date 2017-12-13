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
package org.eclipse.crossmeter.metricprovider.trans.bugs.patches.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTrackerData extends Pongo {
	
	
	
	public BugTrackerData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.patches.model.BugTrackerData");
		NUMBEROFPATCHES.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.patches.model.BugTrackerData");
		CUMULATIVENUMBEROFPATCHES.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.patches.model.BugTrackerData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFPATCHES = new NumericalQueryProducer("numberOfPatches");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPATCHES = new NumericalQueryProducer("cumulativeNumberOfPatches");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTrackerData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfPatches() {
		return parseInteger(dbObject.get("numberOfPatches")+"", 0);
	}
	
	public BugTrackerData setNumberOfPatches(int numberOfPatches) {
		dbObject.put("numberOfPatches", numberOfPatches);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPatches() {
		return parseInteger(dbObject.get("cumulativeNumberOfPatches")+"", 0);
	}
	
	public BugTrackerData setCumulativeNumberOfPatches(int cumulativeNumberOfPatches) {
		dbObject.put("cumulativeNumberOfPatches", cumulativeNumberOfPatches);
		notifyChanged();
		return this;
	}
	
	
	
	
}