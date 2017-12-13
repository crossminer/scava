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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class Emotion extends Pongo {
	
	
	
	public Emotion() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model.Emotion");
		EMOTIONLABEL.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model.Emotion");
		NUMBEROFARTICLES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model.Emotion");
		CUMULATIVENUMBEROFARTICLES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model.Emotion");
		PERCENTAGE.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model.Emotion");
		CUMULATIVEPERCENTAGE.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.emotions.model.Emotion");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer EMOTIONLABEL = new StringQueryProducer("emotionLabel"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer CUMULATIVENUMBEROFARTICLES = new NumericalQueryProducer("cumulativeNumberOfArticles");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	public static NumericalQueryProducer CUMULATIVEPERCENTAGE = new NumericalQueryProducer("cumulativePercentage");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public Emotion setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getEmotionLabel() {
		return parseString(dbObject.get("emotionLabel")+"", "");
	}
	
	public Emotion setEmotionLabel(String emotionLabel) {
		dbObject.put("emotionLabel", emotionLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public Emotion setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfArticles() {
		return parseInteger(dbObject.get("cumulativeNumberOfArticles")+"", 0);
	}
	
	public Emotion setCumulativeNumberOfArticles(int cumulativeNumberOfArticles) {
		dbObject.put("cumulativeNumberOfArticles", cumulativeNumberOfArticles);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public Emotion setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	public float getCumulativePercentage() {
		return parseFloat(dbObject.get("cumulativePercentage")+"", 0.0f);
	}
	
	public Emotion setCumulativePercentage(float cumulativePercentage) {
		dbObject.put("cumulativePercentage", cumulativePercentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}