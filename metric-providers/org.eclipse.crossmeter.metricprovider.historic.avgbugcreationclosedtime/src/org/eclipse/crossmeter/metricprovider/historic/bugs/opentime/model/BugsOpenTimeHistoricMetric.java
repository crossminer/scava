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
package org.eclipse.crossmeter.metricprovider.historic.bugs.opentime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugsOpenTimeHistoricMetric extends Pongo {
	
	
	
	public BugsOpenTimeHistoricMetric() { 
		super();
		AVGBUGOPENTIME.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric");
		AVGBUGOPENTIMEINDAYS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric");
		BUGSCONSIDERED.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric");
	}
	
	public static StringQueryProducer AVGBUGOPENTIME = new StringQueryProducer("avgBugOpenTime"); 
	public static NumericalQueryProducer AVGBUGOPENTIMEINDAYS = new NumericalQueryProducer("avgBugOpenTimeInDays");
	public static NumericalQueryProducer BUGSCONSIDERED = new NumericalQueryProducer("bugsConsidered");
	
	
	public String getAvgBugOpenTime() {
		return parseString(dbObject.get("avgBugOpenTime")+"", "");
	}
	
	public BugsOpenTimeHistoricMetric setAvgBugOpenTime(String avgBugOpenTime) {
		dbObject.put("avgBugOpenTime", avgBugOpenTime);
		notifyChanged();
		return this;
	}
	public double getAvgBugOpenTimeInDays() {
		return parseDouble(dbObject.get("avgBugOpenTimeInDays")+"", 0.0d);
	}
	
	public BugsOpenTimeHistoricMetric setAvgBugOpenTimeInDays(double avgBugOpenTimeInDays) {
		dbObject.put("avgBugOpenTimeInDays", avgBugOpenTimeInDays);
		notifyChanged();
		return this;
	}
	public int getBugsConsidered() {
		return parseInteger(dbObject.get("bugsConsidered")+"", 0);
	}
	
	public BugsOpenTimeHistoricMetric setBugsConsidered(int bugsConsidered) {
		dbObject.put("bugsConsidered", bugsConsidered);
		notifyChanged();
		return this;
	}
	
	
	
	
}