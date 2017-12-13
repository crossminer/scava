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
package org.eclipse.crossmeter.metricprovider.historic.bugs.patches.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsPatchesHistoricMetric extends Pongo {
	
	protected List<DailyBugData> bugs = null;
	
	
	public BugsPatchesHistoricMetric() { 
		super();
		dbObject.put("bugs", new BasicDBList());
		NUMBEROFPATCHES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric");
		CUMULATIVENUMBEROFPATCHES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFPATCHES = new NumericalQueryProducer("numberOfPatches");
	public static NumericalQueryProducer CUMULATIVENUMBEROFPATCHES = new NumericalQueryProducer("cumulativeNumberOfPatches");
	
	
	public int getNumberOfPatches() {
		return parseInteger(dbObject.get("numberOfPatches")+"", 0);
	}
	
	public BugsPatchesHistoricMetric setNumberOfPatches(int numberOfPatches) {
		dbObject.put("numberOfPatches", numberOfPatches);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfPatches() {
		return parseInteger(dbObject.get("cumulativeNumberOfPatches")+"", 0);
	}
	
	public BugsPatchesHistoricMetric setCumulativeNumberOfPatches(int cumulativeNumberOfPatches) {
		dbObject.put("cumulativeNumberOfPatches", cumulativeNumberOfPatches);
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