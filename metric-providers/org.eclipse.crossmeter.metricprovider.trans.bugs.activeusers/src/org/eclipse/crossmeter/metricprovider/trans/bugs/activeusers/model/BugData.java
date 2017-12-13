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
package org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugData extends Pongo {
	
	
	
	public BugData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData");
		ACTIVEUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData");
		INACTIVEUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData");
		PREVIOUSUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData");
		USERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData");
		DAYS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData");
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