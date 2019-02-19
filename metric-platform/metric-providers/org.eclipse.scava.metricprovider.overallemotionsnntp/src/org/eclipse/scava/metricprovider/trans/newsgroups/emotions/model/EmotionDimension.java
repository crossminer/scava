/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class EmotionDimension extends Pongo {
	
	
	
	public EmotionDimension() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension");
		EMOTIONLABEL.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension");
		NUMBEROFARTICLES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension");
		CUMULATIVENUMBEROFARTICLES.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension");
		PERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension");
		CUMULATIVEPERCENTAGE.setOwningType("org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension");
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
	
	public EmotionDimension setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getEmotionLabel() {
		return parseString(dbObject.get("emotionLabel")+"", "");
	}
	
	public EmotionDimension setEmotionLabel(String emotionLabel) {
		dbObject.put("emotionLabel", emotionLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public EmotionDimension setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfArticles() {
		return parseInteger(dbObject.get("cumulativeNumberOfArticles")+"", 0);
	}
	
	public EmotionDimension setCumulativeNumberOfArticles(int cumulativeNumberOfArticles) {
		dbObject.put("cumulativeNumberOfArticles", cumulativeNumberOfArticles);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public EmotionDimension setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	public float getCumulativePercentage() {
		return parseFloat(dbObject.get("cumulativePercentage")+"", 0.0f);
	}
	
	public EmotionDimension setCumulativePercentage(float cumulativePercentage) {
		dbObject.put("cumulativePercentage", cumulativePercentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}
