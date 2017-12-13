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
package org.eclipse.crossmeter.metricprovider.historic.bugs.users.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsUsersHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackingData> bugTrackers = null;
	
	
	public BugsUsersHistoricMetric() { 
		super();
		dbObject.put("bugTrackers", new BasicDBList());
		NUMBEROFUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.users.model.BugsUsersHistoricMetric");
		NUMBEROFACTIVEUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.users.model.BugsUsersHistoricMetric");
		NUMBEROFINACTIVEUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.users.model.BugsUsersHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFUSERS = new NumericalQueryProducer("numberOfUsers");
	public static NumericalQueryProducer NUMBEROFACTIVEUSERS = new NumericalQueryProducer("numberOfActiveUsers");
	public static NumericalQueryProducer NUMBEROFINACTIVEUSERS = new NumericalQueryProducer("numberOfInactiveUsers");
	
	
	public int getNumberOfUsers() {
		return parseInteger(dbObject.get("numberOfUsers")+"", 0);
	}
	
	public BugsUsersHistoricMetric setNumberOfUsers(int numberOfUsers) {
		dbObject.put("numberOfUsers", numberOfUsers);
		notifyChanged();
		return this;
	}
	public int getNumberOfActiveUsers() {
		return parseInteger(dbObject.get("numberOfActiveUsers")+"", 0);
	}
	
	public BugsUsersHistoricMetric setNumberOfActiveUsers(int numberOfActiveUsers) {
		dbObject.put("numberOfActiveUsers", numberOfActiveUsers);
		notifyChanged();
		return this;
	}
	public int getNumberOfInactiveUsers() {
		return parseInteger(dbObject.get("numberOfInactiveUsers")+"", 0);
	}
	
	public BugsUsersHistoricMetric setNumberOfInactiveUsers(int numberOfInactiveUsers) {
		dbObject.put("numberOfInactiveUsers", numberOfInactiveUsers);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugTrackingData> getBugTrackers() {
		if (bugTrackers == null) {
			bugTrackers = new PongoList<DailyBugTrackingData>(this, "bugTrackers", true);
		}
		return bugTrackers;
	}
	
	
}