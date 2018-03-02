/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.bugs.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class BugsBugsHistoricMetric extends Pongo {
	
	protected List<DailyBugTrackerData> bugTrackers = null;
	
	
	public BugsBugsHistoricMetric() { 
		super();
		dbObject.put("bugTrackers", new BasicDBList());
		NUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
		AVERAGECOMMENTSPERBUG.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
		AVERAGEREQUESTSPERBUG.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
		AVERAGEREPLIESPERBUG.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
		AVERAGECOMMENTSPERUSER.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
		AVERAGEREQUESTSPERUSER.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
		AVERAGEREPLIESPERUSER.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer AVERAGECOMMENTSPERBUG = new NumericalQueryProducer("averageCommentsPerBug");
	public static NumericalQueryProducer AVERAGEREQUESTSPERBUG = new NumericalQueryProducer("averageRequestsPerBug");
	public static NumericalQueryProducer AVERAGEREPLIESPERBUG = new NumericalQueryProducer("averageRepliesPerBug");
	public static NumericalQueryProducer AVERAGECOMMENTSPERUSER = new NumericalQueryProducer("averageCommentsPerUser");
	public static NumericalQueryProducer AVERAGEREQUESTSPERUSER = new NumericalQueryProducer("averageRequestsPerUser");
	public static NumericalQueryProducer AVERAGEREPLIESPERUSER = new NumericalQueryProducer("averageRepliesPerUser");
	
	
	public int getNumberOfBugs() {
		return parseInteger(dbObject.get("numberOfBugs")+"", 0);
	}
	
	public BugsBugsHistoricMetric setNumberOfBugs(int numberOfBugs) {
		dbObject.put("numberOfBugs", numberOfBugs);
		notifyChanged();
		return this;
	}
	public float getAverageCommentsPerBug() {
		return parseFloat(dbObject.get("averageCommentsPerBug")+"", 0.0f);
	}
	
	public BugsBugsHistoricMetric setAverageCommentsPerBug(float averageCommentsPerBug) {
		dbObject.put("averageCommentsPerBug", averageCommentsPerBug);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerBug() {
		return parseFloat(dbObject.get("averageRequestsPerBug")+"", 0.0f);
	}
	
	public BugsBugsHistoricMetric setAverageRequestsPerBug(float averageRequestsPerBug) {
		dbObject.put("averageRequestsPerBug", averageRequestsPerBug);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerBug() {
		return parseFloat(dbObject.get("averageRepliesPerBug")+"", 0.0f);
	}
	
	public BugsBugsHistoricMetric setAverageRepliesPerBug(float averageRepliesPerBug) {
		dbObject.put("averageRepliesPerBug", averageRepliesPerBug);
		notifyChanged();
		return this;
	}
	public float getAverageCommentsPerUser() {
		return parseFloat(dbObject.get("averageCommentsPerUser")+"", 0.0f);
	}
	
	public BugsBugsHistoricMetric setAverageCommentsPerUser(float averageCommentsPerUser) {
		dbObject.put("averageCommentsPerUser", averageCommentsPerUser);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerUser() {
		return parseFloat(dbObject.get("averageRequestsPerUser")+"", 0.0f);
	}
	
	public BugsBugsHistoricMetric setAverageRequestsPerUser(float averageRequestsPerUser) {
		dbObject.put("averageRequestsPerUser", averageRequestsPerUser);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerUser() {
		return parseFloat(dbObject.get("averageRepliesPerUser")+"", 0.0f);
	}
	
	public BugsBugsHistoricMetric setAverageRepliesPerUser(float averageRepliesPerUser) {
		dbObject.put("averageRepliesPerUser", averageRepliesPerUser);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyBugTrackerData> getBugTrackers() {
		if (bugTrackers == null) {
			bugTrackers = new PongoList<DailyBugTrackerData>(this, "bugTrackers", true);
		}
		return bugTrackers;
	}
	
	
}
