/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class SeverityLevel extends Pongo {
	
	
	
	public SeverityLevel() { 
		super();
		SEVERITYLEVEL.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		NUMBEROFBUGS.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		AVERAGESENTIMENT.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		SENTIMENTATTHREADBEGGINING.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
		SENTIMENTATTHREADEND.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel");
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
