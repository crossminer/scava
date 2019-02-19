/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class BugData extends Pongo {
	
	
	
	public BugData() { 
		super();
		BUGTRACKERID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		BUGID.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		STATUS.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		RESOLUTION.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		OPERATINGSYSTEM.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		PRIORITY.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		CREATIONTIME.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		LASTCLOSEDTIME.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		AVERAGESENTIMENT.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		STARTSENTIMENT.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
		ENDSENTIMENT.setOwningType("org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData");
	}
	
	public static StringQueryProducer BUGTRACKERID = new StringQueryProducer("bugTrackerId"); 
	public static StringQueryProducer BUGID = new StringQueryProducer("bugId"); 
	public static StringQueryProducer STATUS = new StringQueryProducer("status"); 
	public static StringQueryProducer RESOLUTION = new StringQueryProducer("resolution"); 
	public static StringQueryProducer OPERATINGSYSTEM = new StringQueryProducer("operatingSystem"); 
	public static StringQueryProducer PRIORITY = new StringQueryProducer("priority"); 
	public static StringQueryProducer CREATIONTIME = new StringQueryProducer("creationTime"); 
	public static StringQueryProducer LASTCLOSEDTIME = new StringQueryProducer("lastClosedTime"); 
	public static NumericalQueryProducer AVERAGESENTIMENT = new NumericalQueryProducer("averageSentiment");
	public static StringQueryProducer STARTSENTIMENT = new StringQueryProducer("startSentiment"); 
	public static StringQueryProducer ENDSENTIMENT = new StringQueryProducer("endSentiment"); 
	
	
	public String getBugTrackerId() {
		return parseString(dbObject.get("bugTrackerId")+"", "");
	}
	
	public BugData setBugTrackerId(String bugTrackerId) {
		dbObject.put("bugTrackerId", bugTrackerId);
		notifyChanged();
		return this;
	}
	public String getBugId() {
		return parseString(dbObject.get("bugId")+"", "");
	}
	
	public BugData setBugId(String bugId) {
		dbObject.put("bugId", bugId);
		notifyChanged();
		return this;
	}
	public String getStatus() {
		return parseString(dbObject.get("status")+"", "");
	}
	
	public BugData setStatus(String status) {
		dbObject.put("status", status);
		notifyChanged();
		return this;
	}
	public String getResolution() {
		return parseString(dbObject.get("resolution")+"", "");
	}
	
	public BugData setResolution(String resolution) {
		dbObject.put("resolution", resolution);
		notifyChanged();
		return this;
	}
	public String getOperatingSystem() {
		return parseString(dbObject.get("operatingSystem")+"", "");
	}
	
	public BugData setOperatingSystem(String operatingSystem) {
		dbObject.put("operatingSystem", operatingSystem);
		notifyChanged();
		return this;
	}
	public String getPriority() {
		return parseString(dbObject.get("priority")+"", "");
	}
	
	public BugData setPriority(String priority) {
		dbObject.put("priority", priority);
		notifyChanged();
		return this;
	}
	public String getCreationTime() {
		return parseString(dbObject.get("creationTime")+"", "");
	}
	
	public BugData setCreationTime(String creationTime) {
		dbObject.put("creationTime", creationTime);
		notifyChanged();
		return this;
	}
	public String getLastClosedTime() {
		return parseString(dbObject.get("lastClosedTime")+"", "");
	}
	
	public BugData setLastClosedTime(String lastClosedTime) {
		dbObject.put("lastClosedTime", lastClosedTime);
		notifyChanged();
		return this;
	}
	public float getAverageSentiment() {
		return parseFloat(dbObject.get("averageSentiment")+"", 0.0f);
	}
	
	public BugData setAverageSentiment(float averageSentiment) {
		dbObject.put("averageSentiment", averageSentiment);
		notifyChanged();
		return this;
	}
	public String getStartSentiment() {
		return parseString(dbObject.get("startSentiment")+"", "");
	}
	
	public BugData setStartSentiment(String startSentiment) {
		dbObject.put("startSentiment", startSentiment);
		notifyChanged();
		return this;
	}
	public String getEndSentiment() {
		return parseString(dbObject.get("endSentiment")+"", "");
	}
	
	public BugData setEndSentiment(String endSentiment) {
		dbObject.put("endSentiment", endSentiment);
		notifyChanged();
		return this;
	}
	
	
	
	
}
