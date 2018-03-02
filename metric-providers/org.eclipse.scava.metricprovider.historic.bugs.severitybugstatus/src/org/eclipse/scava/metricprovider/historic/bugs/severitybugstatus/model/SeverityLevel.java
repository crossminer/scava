/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		SEVERITYLEVEL.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFRESOLVEDCLOSEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFRESOLVEDCLOSEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFWONTFIXBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFWONTFIXBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFWORKSFORMEBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFWORKSFORMEBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFNONRESOLVEDCLOSEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFNONRESOLVEDCLOSEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFINVALIDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFINVALIDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFFIXEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFFIXEDBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		NUMBEROFDUPLICATEBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
		PERCENTAGEOFDUPLICATEBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel");
	}
	
	public static StringQueryProducer SEVERITYLEVEL = new StringQueryProducer("severityLevel"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer NUMBEROFRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfResolvedClosedBugs");
	public static NumericalQueryProducer PERCENTAGEOFRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("percentageOfResolvedClosedBugs");
	public static NumericalQueryProducer NUMBEROFWONTFIXBUGS = new NumericalQueryProducer("numberOfWontFixBugs");
	public static NumericalQueryProducer PERCENTAGEOFWONTFIXBUGS = new NumericalQueryProducer("percentageOfWontFixBugs");
	public static NumericalQueryProducer NUMBEROFWORKSFORMEBUGS = new NumericalQueryProducer("numberOfWorksForMeBugs");
	public static NumericalQueryProducer PERCENTAGEOFWORKSFORMEBUGS = new NumericalQueryProducer("percentageOfWorksForMeBugs");
	public static NumericalQueryProducer NUMBEROFNONRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("numberOfNonResolvedClosedBugs");
	public static NumericalQueryProducer PERCENTAGEOFNONRESOLVEDCLOSEDBUGS = new NumericalQueryProducer("percentageOfNonResolvedClosedBugs");
	public static NumericalQueryProducer NUMBEROFINVALIDBUGS = new NumericalQueryProducer("numberOfInvalidBugs");
	public static NumericalQueryProducer PERCENTAGEOFINVALIDBUGS = new NumericalQueryProducer("percentageOfInvalidBugs");
	public static NumericalQueryProducer NUMBEROFFIXEDBUGS = new NumericalQueryProducer("numberOfFixedBugs");
	public static NumericalQueryProducer PERCENTAGEOFFIXEDBUGS = new NumericalQueryProducer("percentageOfFixedBugs");
	public static NumericalQueryProducer NUMBEROFDUPLICATEBUGS = new NumericalQueryProducer("numberOfDuplicateBugs");
	public static NumericalQueryProducer PERCENTAGEOFDUPLICATEBUGS = new NumericalQueryProducer("percentageOfDuplicateBugs");
	
	
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
	public int getNumberOfResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfResolvedClosedBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfResolvedClosedBugs(int numberOfResolvedClosedBugs) {
		dbObject.put("numberOfResolvedClosedBugs", numberOfResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfResolvedClosedBugs() {
		return parseFloat(dbObject.get("percentageOfResolvedClosedBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfResolvedClosedBugs(float percentageOfResolvedClosedBugs) {
		dbObject.put("percentageOfResolvedClosedBugs", percentageOfResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfWontFixBugs() {
		return parseInteger(dbObject.get("numberOfWontFixBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfWontFixBugs(int numberOfWontFixBugs) {
		dbObject.put("numberOfWontFixBugs", numberOfWontFixBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfWontFixBugs() {
		return parseFloat(dbObject.get("percentageOfWontFixBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfWontFixBugs(float percentageOfWontFixBugs) {
		dbObject.put("percentageOfWontFixBugs", percentageOfWontFixBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfWorksForMeBugs() {
		return parseInteger(dbObject.get("numberOfWorksForMeBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfWorksForMeBugs(int numberOfWorksForMeBugs) {
		dbObject.put("numberOfWorksForMeBugs", numberOfWorksForMeBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfWorksForMeBugs() {
		return parseFloat(dbObject.get("percentageOfWorksForMeBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfWorksForMeBugs(float percentageOfWorksForMeBugs) {
		dbObject.put("percentageOfWorksForMeBugs", percentageOfWorksForMeBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfNonResolvedClosedBugs() {
		return parseInteger(dbObject.get("numberOfNonResolvedClosedBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfNonResolvedClosedBugs(int numberOfNonResolvedClosedBugs) {
		dbObject.put("numberOfNonResolvedClosedBugs", numberOfNonResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfNonResolvedClosedBugs() {
		return parseFloat(dbObject.get("percentageOfNonResolvedClosedBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfNonResolvedClosedBugs(float percentageOfNonResolvedClosedBugs) {
		dbObject.put("percentageOfNonResolvedClosedBugs", percentageOfNonResolvedClosedBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfInvalidBugs() {
		return parseInteger(dbObject.get("numberOfInvalidBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfInvalidBugs(int numberOfInvalidBugs) {
		dbObject.put("numberOfInvalidBugs", numberOfInvalidBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfInvalidBugs() {
		return parseFloat(dbObject.get("percentageOfInvalidBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfInvalidBugs(float percentageOfInvalidBugs) {
		dbObject.put("percentageOfInvalidBugs", percentageOfInvalidBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfFixedBugs() {
		return parseInteger(dbObject.get("numberOfFixedBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfFixedBugs(int numberOfFixedBugs) {
		dbObject.put("numberOfFixedBugs", numberOfFixedBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfFixedBugs() {
		return parseFloat(dbObject.get("percentageOfFixedBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfFixedBugs(float percentageOfFixedBugs) {
		dbObject.put("percentageOfFixedBugs", percentageOfFixedBugs);
		notifyChanged();
		return this;
	}
	public int getNumberOfDuplicateBugs() {
		return parseInteger(dbObject.get("numberOfDuplicateBugs")+"", 0);
	}
	
	public SeverityLevel setNumberOfDuplicateBugs(int numberOfDuplicateBugs) {
		dbObject.put("numberOfDuplicateBugs", numberOfDuplicateBugs);
		notifyChanged();
		return this;
	}
	public float getPercentageOfDuplicateBugs() {
		return parseFloat(dbObject.get("percentageOfDuplicateBugs")+"", 0.0f);
	}
	
	public SeverityLevel setPercentageOfDuplicateBugs(float percentageOfDuplicateBugs) {
		dbObject.put("percentageOfDuplicateBugs", percentageOfDuplicateBugs);
		notifyChanged();
		return this;
	}
	
	
	
	
}
