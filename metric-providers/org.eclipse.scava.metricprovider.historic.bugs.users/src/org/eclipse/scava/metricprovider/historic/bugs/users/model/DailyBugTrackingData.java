/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.users.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class DailyBugTrackingData extends Pongo {
	
	
	
	public DailyBugTrackingData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.users.model.DailyBugTrackingData");
		NUMBEROFUSERS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.users.model.DailyBugTrackingData");
		NUMBEROFACTIVEUSERS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.users.model.DailyBugTrackingData");
		NUMBEROFINACTIVEUSERS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.users.model.DailyBugTrackingData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer NUMBEROFUSERS = new NumericalQueryProducer("numberOfUsers");
	public static NumericalQueryProducer NUMBEROFACTIVEUSERS = new NumericalQueryProducer("numberOfActiveUsers");
	public static NumericalQueryProducer NUMBEROFINACTIVEUSERS = new NumericalQueryProducer("numberOfInactiveUsers");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public DailyBugTrackingData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getNumberOfUsers() {
		return parseInteger(dbObject.get("numberOfUsers")+"", 0);
	}
	
	public DailyBugTrackingData setNumberOfUsers(int numberOfUsers) {
		dbObject.put("numberOfUsers", numberOfUsers);
		notifyChanged();
		return this;
	}
	public int getNumberOfActiveUsers() {
		return parseInteger(dbObject.get("numberOfActiveUsers")+"", 0);
	}
	
	public DailyBugTrackingData setNumberOfActiveUsers(int numberOfActiveUsers) {
		dbObject.put("numberOfActiveUsers", numberOfActiveUsers);
		notifyChanged();
		return this;
	}
	public int getNumberOfInactiveUsers() {
		return parseInteger(dbObject.get("numberOfInactiveUsers")+"", 0);
	}
	
	public DailyBugTrackingData setNumberOfInactiveUsers(int numberOfInactiveUsers) {
		dbObject.put("numberOfInactiveUsers", numberOfInactiveUsers);
		notifyChanged();
		return this;
	}
	
	
	
	
}
