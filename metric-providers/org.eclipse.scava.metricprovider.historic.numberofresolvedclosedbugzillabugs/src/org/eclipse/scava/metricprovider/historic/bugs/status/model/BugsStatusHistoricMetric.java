/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.status.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsStatusHistoricMetric extends Pongo {
	
	
	
	public BugsStatusHistoricMetric() { 
		super();
		NUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFRESOLVEDCLOSEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFWONTFIXBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFWORKSFORMEBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFNONRESOLVEDCLOSEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFINVALIDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFFIXEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
		NUMBEROFDUPLICATEBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer NUMBEROFRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfResolvedClosedBugs");
	public static NumericalQueryProducer NUMBEROFWONTFIXBUGS = new NumericalQueryProducer("numberOfWontFixBugs");
	public static NumericalQueryProducer NUMBEROFWORKSFORMEBUGS = new NumericalQueryProducer("numberOfWorksForMeBugs");
	public static NumericalQueryProducer NUMBEROFNONRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfNonResolvedClosedBugs");
	public static NumericalQueryProducer NUMBEROFINVALIDBUGS = new NumericalQueryProducer("numberOfInvalidBugs");
	public static NumericalQueryProducer NUMBEROFFIXEDBUGS = new NumericalQueryProducer("numberOfFixedBugs");
	public static NumericalQueryProducer NUMBEROFDUPLICATEBUGS = new NumericalQueryProducer("numberOfDuplicateBugs");
	
	
	public long getNumberOfBugs() {
		return parseLong(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfBugs(long numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfResolvedClosedBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfResolvedClosedBugs(int numberOfResolvedClosedBugs) {
		dbObject.put("numberOfResolvedClosedBugs", numberOfResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfWontFixBugs() {
		return parseInteger(dbObject.get("numberOfWontFixBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfWontFixBugs(int numberOfWontFixBugs) {
		dbObject.put("numberOfWontFixBugs", numberOfWontFixBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfWorksForMeBugs() {
		return parseInteger(dbObject.get("numberOfWorksForMeBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfWorksForMeBugs(int numberOfWorksForMeBugs) {
		dbObject.put("numberOfWorksForMeBugs", numberOfWorksForMeBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfNonResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfNonResolvedClosedBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfNonResolvedClosedBugs(int numberOfNonResolvedClosedBugs) {
		dbObject.put("numberOfNonResolvedClosedBugs", numberOfNonResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfInvalidBugs() {
		return parseInteger(dbObject.get("numberOfInvalidBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfInvalidBugs(int numberOfInvalidBugs) {
		dbObject.put("numberOfInvalidBugs", numberOfInvalidBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfFixedBugs() {
		return parseInteger(dbObject.get("numberOfFixedBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfFixedBugs(int numberOfFixedBugs) {
		dbObject.put("numberOfFixedBugs", numberOfFixedBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfDuplicateBugs() {
		return parseInteger(dbObject.get("numberOfDuplicateBugs")+"", 0);
	}
	
	public BugsStatusHistoricMetric setNumberOfDuplicateBugs(int numberOfDuplicateBugs) {
		dbObject.put("numberOfDuplicateBugs", numberOfDuplicateBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}
