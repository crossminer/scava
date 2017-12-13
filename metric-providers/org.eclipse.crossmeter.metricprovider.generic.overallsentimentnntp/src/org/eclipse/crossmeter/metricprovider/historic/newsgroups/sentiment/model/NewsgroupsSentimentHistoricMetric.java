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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.sentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class NewsgroupsSentimentHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsSentimentHistoricMetric() { 
		super();
		OVERALLAVERAGESENTIMENT.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.sentiment.model.NewsgroupsSentimentHistoricMetric");
		OVERALLSENTIMENTATTHREADBEGGINING.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.sentiment.model.NewsgroupsSentimentHistoricMetric");
		OVERALLSENTIMENTATTHREADEND.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.sentiment.model.NewsgroupsSentimentHistoricMetric");
	}
	
	public static NumericalQueryProducer OVERALLAVERAGESENTIMENT = new NumericalQueryProducer("overallAverageSentiment");
	public static NumericalQueryProducer OVERALLSENTIMENTATTHREADBEGGINING = new NumericalQueryProducer("overallSentimentAtThreadBeggining");
	public static NumericalQueryProducer OVERALLSENTIMENTATTHREADEND = new NumericalQueryProducer("overallSentimentAtThreadEnd");
	
	
	public float getOverallAverageSentiment() {
		return parseFloat(dbObject.get("overallAverageSentiment")+"", 0.0f);
	}
	
	public NewsgroupsSentimentHistoricMetric setOverallAverageSentiment(float overallAverageSentiment) {
		dbObject.put("overallAverageSentiment", overallAverageSentiment);
		notifyChanged();
		return this;
	}
	public float getOverallSentimentAtThreadBeggining() {
		return parseFloat(dbObject.get("overallSentimentAtThreadBeggining")+"", 0.0f);
	}
	
	public NewsgroupsSentimentHistoricMetric setOverallSentimentAtThreadBeggining(float overallSentimentAtThreadBeggining) {
		dbObject.put("overallSentimentAtThreadBeggining", overallSentimentAtThreadBeggining);
		notifyChanged();
		return this;
	}
	public float getOverallSentimentAtThreadEnd() {
		return parseFloat(dbObject.get("overallSentimentAtThreadEnd")+"", 0.0f);
	}
	
	public NewsgroupsSentimentHistoricMetric setOverallSentimentAtThreadEnd(float overallSentimentAtThreadEnd) {
		dbObject.put("overallSentimentAtThreadEnd", overallSentimentAtThreadEnd);
		notifyChanged();
		return this;
	}
	
	
	
	
}