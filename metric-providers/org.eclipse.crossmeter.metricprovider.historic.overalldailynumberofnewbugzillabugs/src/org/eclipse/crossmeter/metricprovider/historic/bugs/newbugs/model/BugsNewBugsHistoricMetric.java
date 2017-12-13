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
package org.eclipse.crossmeter.metricprovider.historic.bugs.newbugs.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsNewBugsHistoricMetric extends Pongo {
	
	protected List<DailyBugData> bugs = null;
	
	
	public BugsNewBugsHistoricMetric() { 
		super();
		dbObject.put("bugs", new BasicDBList());
		NUMBEROFBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric");
		CUMULATIVENUMBEROFBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer CUMULATIVENUMBEROFBUGS = new NumericalQueryProducer("cumulativeNumberOfBugs");
	
	
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugsNewBugsHistoricMetric setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfBugs() {
		return parseInteger(dbObject.get("cumulativeNumberOfBugs")+"", 0);
	}
	
	public BugsNewBugsHistoricMetric setCumulativeNumberOfBugs(int cumulativeNumberOfBugs) {
		dbObject.put("cumulativeNumberOfBugs", cumulativeNumberOfBugs);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugData> getBugs() {
		if (bugs == null) {
			bugs = new PongoList<DailyBugData>(this, "bugs", true);
		}
		return bugs;
	}
	
	
}