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
package org.eclipse.crossmeter.metricprovider.historic.bugs.requestsreplies.average.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsRequestsRepliesHistoricMetric extends Pongo {
	
	
	
	public BugsRequestsRepliesHistoricMetric() { 
		super();
		AVERAGECOMMENTSPERDAY.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEREQUESTSPERDAY.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
		AVERAGEREPLIESPERDAY.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric");
	}
	
	public static NumericalQueryProducer AVERAGECOMMENTSPERDAY = new NumericalQueryProducer("averageCommentsPerDay");
	public static NumericalQueryProducer AVERAGEREQUESTSPERDAY = new NumericalQueryProducer("averageRequestsPerDay");
	public static NumericalQueryProducer AVERAGEREPLIESPERDAY = new NumericalQueryProducer("averageRepliesPerDay");
	
	
	public float getAverageCommentsPerDay() {
		return parseFloat(dbObject.get("averageCommentsPerDay")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageCommentsPerDay(float averageCommentsPerDay) {
		dbObject.put("averageCommentsPerDay", averageCommentsPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRequestsPerDay() {
		return parseFloat(dbObject.get("averageRequestsPerDay")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageRequestsPerDay(float averageRequestsPerDay) {
		dbObject.put("averageRequestsPerDay", averageRequestsPerDay);
		notifyChanged();
		return this;
	}
	public float getAverageRepliesPerDay() {
		return parseFloat(dbObject.get("averageRepliesPerDay")+"", 0.0f);
	}
	
	public BugsRequestsRepliesHistoricMetric setAverageRepliesPerDay(float averageRepliesPerDay) {
		dbObject.put("averageRepliesPerDay", averageRepliesPerDay);
		notifyChanged();
		return this;
	}
	
	
	
	
}