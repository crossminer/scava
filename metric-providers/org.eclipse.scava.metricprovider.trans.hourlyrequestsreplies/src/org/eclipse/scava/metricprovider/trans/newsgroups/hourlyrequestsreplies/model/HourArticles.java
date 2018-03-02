/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class HourArticles extends Pongo {
	
	
	
	public HourArticles() { 
		super();
		HOUR.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFARTICLES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		NUMBEROFREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		PERCENTAGEOFARTICLES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		PERCENTAGEOFREQUESTS.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
		PERCENTAGEOFREPLIES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles");
	}
	
	public static StringQueryProducer HOUR = new StringQueryProducer("hour"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer NUMBEROFREQUESTS = new NumericalQueryProducer("numberOfRequests");
	public static NumericalQueryProducer NUMBEROFREPLIES = new NumericalQueryProducer("numberOfReplies");
	public static NumericalQueryProducer PERCENTAGEOFARTICLES = new NumericalQueryProducer("percentageOfArticles");
	public static NumericalQueryProducer PERCENTAGEOFREQUESTS = new NumericalQueryProducer("percentageOfRequests");
	public static NumericalQueryProducer PERCENTAGEOFREPLIES = new NumericalQueryProducer("percentageOfReplies");
	
	
	public String getHour() {
		return parseString(dbObject.get("hour")+"", "");
	}
	
	public HourArticles setHour(String hour) {
		dbObject.put("hour", hour);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public HourArticles setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getNumberOfRequests() {
		return parseInteger(dbObject.get("numberOfRequests")+"", 0);
	}
	
	public HourArticles setNumberOfRequests(int numberOfRequests) {
		dbObject.put("numberOfRequests", numberOfRequests);
		notifyChanged();
		return this;
	}
	public int getNumberOfReplies() {
		return parseInteger(dbObject.get("numberOfReplies")+"", 0);
	}
	
	public HourArticles setNumberOfReplies(int numberOfReplies) {
		dbObject.put("numberOfReplies", numberOfReplies);
		notifyChanged();
		return this;
	}
	public float getPercentageOfArticles() {
		return parseFloat(dbObject.get("percentageOfArticles")+"", 0.0f);
	}
	
	public HourArticles setPercentageOfArticles(float percentageOfArticles) {
		dbObject.put("percentageOfArticles", percentageOfArticles);
		notifyChanged();
		return this;
	}
	public float getPercentageOfRequests() {
		return parseFloat(dbObject.get("percentageOfRequests")+"", 0.0f);
	}
	
	public HourArticles setPercentageOfRequests(float percentageOfRequests) {
		dbObject.put("percentageOfRequests", percentageOfRequests);
		notifyChanged();
		return this;
	}
	public float getPercentageOfReplies() {
		return parseFloat(dbObject.get("percentageOfReplies")+"", 0.0f);
	}
	
	public HourArticles setPercentageOfReplies(float percentageOfReplies) {
		dbObject.put("percentageOfReplies", percentageOfReplies);
		notifyChanged();
		return this;
	}
	
	
	
	
}
