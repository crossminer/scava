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
package org.eclipse.crossmeter.metricprovider.historic.bugs.severitysentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		SEVERITYLEVEL.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		NUMBEROFBUGS.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		AVERAGESENTIMENT.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		SENTIMENTATTHREADBEGGINING.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		SENTIMENTATTHREADEND.setOwningType("org.eclipse.crossmeter.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
	}
	
	public static StringQueryProducer SEVERITYLEVEL = new StringQueryProducer("severityLevel"); 
	public static NumericalQueryProducer NUMBEROFBUGS = new NumericalQueryProducer("numberOfBugs");
	public static NumericalQueryProducer AVERAGESENTIMENT = new NumericalQueryProducer("averageSentiment");
	public static NumericalQueryProducer SENTIMENTATTHREADBEGGINING = new NumericalQueryProducer("sentimentAtThreadBeggining");
	public static NumericalQueryProducer SENTIMENTATTHREADEND = new NumericalQueryProducer("sentimentAtThreadEnd");
	
	
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
	public float getAverageSentiment() {
		return parseFloat(dbObject.get("averageSentiment")+"", 0.0f);
	}
	
	public SeverityLevel setAverageSentiment(float averageSentiment) {
		dbObject.put("averageSentiment", averageSentiment);
		notifyChanged();
		return this;
	}
	public float getSentimentAtThreadBeggining() {
		return parseFloat(dbObject.get("sentimentAtThreadBeggining")+"", 0.0f);
	}
	
	public SeverityLevel setSentimentAtThreadBeggining(float sentimentAtThreadBeggining) {
		dbObject.put("sentimentAtThreadBeggining", sentimentAtThreadBeggining);
		notifyChanged();
		return this;
	}
	public float getSentimentAtThreadEnd() {
		return parseFloat(dbObject.get("sentimentAtThreadEnd")+"", 0.0f);
	}
	
	public SeverityLevel setSentimentAtThreadEnd(float sentimentAtThreadEnd) {
		dbObject.put("sentimentAtThreadEnd", sentimentAtThreadEnd);
		notifyChanged();
		return this;
	}
	
	
	
	
}