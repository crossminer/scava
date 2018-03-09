/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.unansweredbugs.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsUnansweredBugsHistoricMetric extends Pongo {
	
	
	
	public BugsUnansweredBugsHistoricMetric() { 
		super();
		NUMBEROFUNANSWEREDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.unansweredbugs.model.BugsUnansweredBugsHistoricMetric");
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
