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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.contentclasses.model;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class ContentClass extends Pongo {
	
	
	
	public ContentClass() { 
		super();
		NEWSGROUPNAME.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
		CLASSLABEL.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
		NUMBEROFARTICLES.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
		PERCENTAGE.setOwningType("org.eclipse.crossmeter.metricprovider.trans.newsgroups.contentclasses.model.ContentClass");
	}
	
	public static StringQueryProducer NEWSGROUPNAME = new StringQueryProducer("newsgroupName"); 
	public static StringQueryProducer CLASSLABEL = new StringQueryProducer("classLabel"); 
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer PERCENTAGE = new NumericalQueryProducer("percentage");
	
	
	public String getNewsgroupName() {
		return parseString(dbObject.get("newsgroupName")+"", "");
	}
	
	public ContentClass setNewsgroupName(String newsgroupName) {
		dbObject.put("newsgroupName", newsgroupName);
		notifyChanged();
		return this;
	}
	public String getClassLabel() {
		return parseString(dbObject.get("classLabel")+"", "");
	}
	
	public ContentClass setClassLabel(String classLabel) {
		dbObject.put("classLabel", classLabel);
		notifyChanged();
		return this;
	}
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public ContentClass setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public float getPercentage() {
		return parseFloat(dbObject.get("percentage")+"", 0.0f);
	}
	
	public ContentClass setPercentage(float percentage) {
		dbObject.put("percentage", percentage);
		notifyChanged();
		return this;
	}
	
	
	
	
}