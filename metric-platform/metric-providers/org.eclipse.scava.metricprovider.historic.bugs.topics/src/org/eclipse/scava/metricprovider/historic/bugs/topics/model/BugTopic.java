/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.topics.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugTopic extends Pongo {
	
	
	
	public BugTopic() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic");
		LABEL.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic");
		NUMBEROFDOCUMENTS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer LABEL = new StringQueryProducer("label"); 
	public static NumericalQueryProducer NUMBEROFDOCUMENTS = new NumericalQueryProducer("numberOfDocuments");
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugTopic setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getLabel() {
		return parseString(dbObject.get("label")+"", "");
	}
	
	public BugTopic setLabel(String label) {
		dbObject.put("label", label);
		notifyChanged();
		return this;
	}
	public int getNumberOfDocuments() {
		return parseInteger(dbObject.get("numberOfDocuments")+"", 0);
	}
	
	public BugTopic setNumberOfDocuments(int numberOfDocuments) {
		dbObject.put("numberOfDocuments", numberOfDocuments);
		notifyChanged();
		return this;
	}
	
	
	
	
}
