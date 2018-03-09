/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugData extends Pongo {
	
	
	
	public BugData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData");
		ACTIVEUSERS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData");
		INACTIVEUSERS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData");
		PREVIOUSUSERS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData");
		USERS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData");
		DAYS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static NumericalQueryProducer ACTIVEUSERS = new NumericalQueryProducer("activeUsers");
	public static NumericalQueryProducer INACTIVEUSERS = new NumericalQueryProducer("inactiveUsers");
	public static NumericalQueryProducer PREVIOUSUSERS = new NumericalQueryProducer("previousUsers");
	public static NumericalQueryProducer USERS = new NumericalQueryProducer("users");
	public static NumericalQueryProducer DAYS = new NumericalQueryProducer("days");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public int getActiveUsers() {
		return parseInteger(dbObject.get("activeUsers")+"", 0);
	}
	
	public BugData setActiveUsers(int activeUsers) {
		dbObject.put("activeUsers", activeUsers);
		notifyChanged();
		return this;
	}
	public int getInactiveUsers() {
		return parseInteger(dbObject.get("inactiveUsers")+"", 0);
	}
	
	public BugData setInactiveUsers(int inactiveUsers) {
		dbObject.put("inactiveUsers", inactiveUsers);
		notifyChanged();
		return this;
	}
	public int getPreviousUsers() {
		return parseInteger(dbObject.get("previousUsers")+"", 0);
	}
	
	public BugData setPreviousUsers(int previousUsers) {
		dbObject.put("previousUsers", previousUsers);
		notifyChanged();
		return this;
	}
	public int getUsers() {
		return parseInteger(dbObject.get("users")+"", 0);
	}
	
	public BugData setUsers(int users) {
		dbObject.put("users", users);
		notifyChanged();
		return this;
	}
	public int getDays() {
		return parseInteger(dbObject.get("days")+"", 0);
	}
	
	public BugData setDays(int days) {
		dbObject.put("days", days);
		notifyChanged();
		return this;
	}
	
	
	
	
}
