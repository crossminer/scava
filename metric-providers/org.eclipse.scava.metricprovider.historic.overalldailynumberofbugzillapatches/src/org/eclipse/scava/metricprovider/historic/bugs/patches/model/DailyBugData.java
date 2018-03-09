/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.patches.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugData extends Pongo {
	
	
	
	public DailyBugData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.patches.model.DailyBugData");
		NUMBEROFPATCHES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.patches.model.DailyBugData");
		CUMULATIVENUMBEROFPATCHES.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.patches.model.DailyBugData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFPATCHES = new NumericalQueryProducer("numberOfPatches");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPATCHES = new NumericalQueryProducer("cumulativeNumberOfPatches");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfPatches() {
		return parseInteger(dbObject.get("numberOfPatches")+"", 0);
	}
	
	public DailyBugData setNumberOfPatches(int numberOfPatches) {
		dbObject.put("numberOfPatches", numberOfPatches);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPatches() {
		return parseInteger(dbObject.get("cumulativeNumberOfPatches")+"", 0);
	}
	
	public DailyBugData setCumulativeNumberOfPatches(int cumulativeNumberOfPatches) {
		dbObject.put("cumulativeNumberOfPatches", cumulativeNumberOfPatches);
		notifyChanged();
		return this;
	}
	
	
	
	
}
