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
package org.eclipse.crossmeter.metricprovider.historic.bugs.severityresponsetime.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		SEVERITYLEVEL.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel");
		NUMBEROFBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel");
		AVGRESPONSETIME.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel");
		AVGRESPONSETIMEFORMATTED.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel");
	}
	
	public static StringQueryProducer SEVERITYLEVEL = new StringQueryProducer("severityLevel"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer AVGRESPONSETIME = new NumericalQueryProducer("avgResponseTime");
	public static StringQueryProducer AVGRESPONSETIMEFORMATTED = new StringQueryProducer("avgResponseTimeFormatted"); 
	
	
	public String getSeverityLevel() {
		return parseString(dbObject.get("severityLevel")+"", "");
	}
	
	public SeverityLevel setSeverityLevel(String severityLevel) {
		dbObject.put("severityLevel", severityLevel);
		notifyChanged();
		return this;
	}
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public long getAvgResponseTime() {
		return parseLong(dbObject.get("avgResponseTime")+"", 0);
	}
	
	public SeverityLevel setAvgResponseTime(long avgResponseTime) {
		dbObject.put("avgResponseTime", avgResponseTime);
		notifyChanged();
		return this;
	}
	public String getAvgResponseTimeFormatted() {
		return parseString(dbObject.get("avgResponseTimeFormatted")+"", "");
	}
	
	public SeverityLevel setAvgResponseTimeFormatted(String avgResponseTimeFormatted) {
		dbObject.put("avgResponseTimeFormatted", avgResponseTimeFormatted);
		notifyChanged();
		return this;
	}
	
	
	
	
}