/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.sentiment.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;


public class NewsgroupsSentimentHistoricMetric extends Pongo {
	
	
	
	public NewsgroupsSentimentHistoricMetric() { 
		super();
		OVERALLAVERAGESENTIMENT.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.sentiment.model.NewsgroupsSentimentHistoricMetric");
		OVERALLSENTIMENTATTHREADBEGGINING.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.sentiment.model.NewsgroupsSentimentHistoricMetric");
		OVERALLSENTIMENTATTHREADEND.setOwningType("org.eclipse.scava.metricprovider.historic.newsgroups.sentiment.model.NewsgroupsSentimentHistoricMetric");
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
