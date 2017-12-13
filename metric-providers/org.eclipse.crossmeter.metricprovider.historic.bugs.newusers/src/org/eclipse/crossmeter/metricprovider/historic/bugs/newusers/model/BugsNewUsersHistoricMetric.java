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
package org.eclipse.crossmeter.metricprovider.historic.bugs.newusers.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsNewUsersHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackerData> bugTrackers = null;
	
	
	public BugsNewUsersHistoricMetric() { 
		super();
		dbObject.put("bugTrackers", new BasicDBList());
		NUMBEROFNEWUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.newusers.model.BugsNewUsersHistoricMetric");
		CUMULATIVENUMBEROFNEWUSERS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.newusers.model.BugsNewUsersHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFNEWUSERS = new NumericalQueryProducer("numberOfNewUsers");
	public static NumericalQueryProducer CUMULATIVENUMBEROFNEWUSERS = new NumericalQueryProducer("cumulativeNumberOfNewUsers");
	
	
	public int getNumberOfNewUsers() {
		return parseInteger(dbObject.get("numberOfNewUsers")+"", 0);
	}
	
	public BugsNewUsersHistoricMetric setNumberOfNewUsers(int numberOfNewUsers) {
		dbObject.put("numberOfNewUsers", numberOfNewUsers);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfNewUsers() {
		return parseInteger(dbObject.get("cumulativeNumberOfNewUsers")+"", 0);
	}
	
	public BugsNewUsersHistoricMetric setCumulativeNumberOfNewUsers(int cumulativeNumberOfNewUsers) {
		dbObject.put("cumulativeNumberOfNewUsers", cumulativeNumberOfNewUsers);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugTrackerData> getBugTrackers() {
		if (bugTrackers == null) {
			bugTrackers = new PongoList<DailyBugTrackerData>(this, "bugTrackers", true);
		}
		return bugTrackers;
	}
	
	
}