/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.sentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class BugsSentimentHistoricMetric extends Pongo {
	
	
	
	public BugsSentimentHistoricMetric() { 
		super();
		OVERALLAVERAGESENTIMENT.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.sentiment.model.BugsSentimentHistoricMetric");
		OVERALLSENTIMENTATTHREADBEGGINING.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.sentiment.model.BugsSentimentHistoricMetric");
		OVERALLSENTIMENTATTHREADEND.setOwningType("org.eclipse.scava.metricprovider.historic.bugs.sentiment.model.BugsSentimentHistoricMetric");
	}
	
	public static NumericalQueryProducer OVERALLAVERAGESENTIMENT = new NumericalQueryProducer("overallAverageSentiment");
	public static NumericalQueryProducer OVERALLSENTIMENTATTHREADBEGGINING = new NumericalQueryProducer("overallSentimentAtThreadBeggining");
	public static NumericalQueryProducer OVERALLSENTIMENTATTHREADEND = new NumericalQueryProducer("overallSentimentAtThreadEnd");
	
	
	public float getOverallAverageSentiment() {
		return parseFloat(dbObject.get("overallAverageSentiment")+"", 0.0f);
	}
	
	public BugsSentimentHistoricMetric setOverallAverageSentiment(float overallAverageSentiment) {
		dbObject.put("overallAverageSentiment", overallAverageSentiment);
		notifyChanged();
		return this;
	}
	public float getOverallSentimentAtThreadBeggining() {
		return parseFloat(dbObject.get("overallSentimentAtThreadBeggining")+"", 0.0f);
	}
	
	public BugsSentimentHistoricMetric setOverallSentimentAtThreadBeggining(float overallSentimentAtThreadBeggining) {
		dbObject.put("overallSentimentAtThreadBeggining", overallSentimentAtThreadBeggining);
		notifyChanged();
		return this;
	}
	public float getOverallSentimentAtThreadEnd() {
		return parseFloat(dbObject.get("overallSentimentAtThreadEnd")+"", 0.0f);
	}
	
	public BugsSentimentHistoricMetric setOverallSentimentAtThreadEnd(float overallSentimentAtThreadEnd) {
		dbObject.put("overallSentimentAtThreadEnd", overallSentimentAtThreadEnd);
		notifyChanged();
		return this;
	}
	
	
	
	
}
