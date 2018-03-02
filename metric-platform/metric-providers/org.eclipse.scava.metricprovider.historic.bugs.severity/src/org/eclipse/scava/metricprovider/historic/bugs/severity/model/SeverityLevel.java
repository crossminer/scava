/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.severity.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severity.model.SeverityLevel");
		SEVERITYLEVEL.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severity.model.SeverityLevel");
		NUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severity.model.SeverityLevel");
		PERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severity.model.SeverityLevel");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer SEVERITYLEVEL = new StringQueryProducer("severityLevel"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public SeverityLevel setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
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
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public SeverityLevel setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}
