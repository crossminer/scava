/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.newbugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugData extends Pongo {
	
	
	
	public DailyBugData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.DailyBugData");
		NUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.DailyBugData");
		CUMULATIVENUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.DailyBugData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer CUMULATIVENUMBEROFBUGS = new NumericalQueryProducer("cumulativeNumberOfBugs");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public DailyBugData setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfBugs() {
		return parseInteger(dbObject.get("cumulativeNumberOfBugs")+"", 0);
	}
	
	public DailyBugData setCumulativeNumberOfBugs(int cumulativeNumberOfBugs) {
		dbObject.put("cumulativeNumberOfBugs", cumulativeNumberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}
