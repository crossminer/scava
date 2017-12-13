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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class NewsgroupData extends Pongo {
	
	
	
	public NewsgroupData() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData");
		ACTIVEUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData");
		INACTIVEUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData");
		PREVIOUSUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData");
		USERS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData");
		DAYS.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static NumericalQueryProducer ACTIVEUSERS = new NumericalQueryProducer("activeUsers");
	public static NumericalQueryProducer INACTIVEUSERS = new NumericalQueryProducer("inactiveUsers");
	public static NumericalQueryProducer PREVIOUSUSERS = new NumericalQueryProducer("previousUsers");
	public static NumericalQueryProducer USERS = new NumericalQueryProducer("users");
	public static NumericalQueryProducer DAYS = new NumericalQueryProducer("days");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public NewsgroupData setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public int getActiveUsers() {
		return parseInteger(dbObject.get("activeUsers")+"", 0);
	}
	
	public NewsgroupData setActiveUsers(int activeUsers) {
		dbObject.put("activeUsers", activeUsers);
		notifyChanged();
		return this;
	}
	public int getInactiveUsers() {
		return parseInteger(dbObject.get("inactiveUsers")+"", 0);
	}
	
	public NewsgroupData setInactiveUsers(int inactiveUsers) {
		dbObject.put("inactiveUsers", inactiveUsers);
		notifyChanged();
		return this;
	}
	public int getPreviousUsers() {
		return parseInteger(dbObject.get("previousUsers")+"", 0);
	}
	
	public NewsgroupData setPreviousUsers(int previousUsers) {
		dbObject.put("previousUsers", previousUsers);
		notifyChanged();
		return this;
	}
	public int getUsers() {
		return parseInteger(dbObject.get("users")+"", 0);
	}
	
	public NewsgroupData setUsers(int users) {
		dbObject.put("users", users);
		notifyChanged();
		return this;
	}
	public int getDays() {
		return parseInteger(dbObject.get("days")+"", 0);
	}
	
	public NewsgroupData setDays(int days) {
		dbObject.put("days", days);
		notifyChanged();
		return this;
	}
	
	
	
	
}