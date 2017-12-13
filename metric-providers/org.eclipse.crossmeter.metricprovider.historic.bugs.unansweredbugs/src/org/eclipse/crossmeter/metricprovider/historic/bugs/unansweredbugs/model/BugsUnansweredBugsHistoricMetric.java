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
package org.eclipse.crossmeter.metricprovider.historic.bugs.unansweredbugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsUnansweredBugsHistoricMetric extends Pongo {
	
	
	
	public BugsUnansweredBugsHistoricMetric() { 
		super();
		NUMBEROFUNANSWEREDBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.unansweredbugs.model.BugsUnansweredBugsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFUNANSWEREDBUGS = new NumericalQueryProducer("numberOfUnansweredBugs");
	
	
	public int getNumberOfUnansweredBugs() {
		return parseInteger(dbObject.get("numberOfUnansweredBugs")+"", 0);
	}
	
	public BugsUnansweredBugsHistoricMetric setNumberOfUnansweredBugs(int numberOfUnansweredBugs) {
		dbObject.put("numberOfUnansweredBugs", numberOfUnansweredBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}