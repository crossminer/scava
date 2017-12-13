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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.articles.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class NewsgroupsArticlesHistoricMetric extends Pongo {
	
	protected List<DailyNewsgroupData> newsgroups = null;
	
	
	public NewsgroupsArticlesHistoricMetric() { 
		super();
		dbObject.put("newsgroups", new BasicDBList());
		NUMBEROFARTICLES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.articles.model.NewsgroupsArticlesHistoricMetric");
		CUMULATIVENUMBEROFARTICLES.setOwningType("org.eclipse.crossmeter.metricprovider.historic.newsgroups.articles.model.NewsgroupsArticlesHistoricMetric");
	}
	
	public static NumericalQueryProducer NUMBEROFARTICLES = new NumericalQueryProducer("numberOfArticles");
	public static NumericalQueryProducer CUMULATIVENUMBEROFARTICLES = new NumericalQueryProducer("cumulativeNumberOfArticles");
	
	
	public int getNumberOfArticles() {
		return parseInteger(dbObject.get("numberOfArticles")+"", 0);
	}
	
	public NewsgroupsArticlesHistoricMetric setNumberOfArticles(int numberOfArticles) {
		dbObject.put("numberOfArticles", numberOfArticles);
		notifyChanged();
		return this;
	}
	public int getCumulativeNumberOfArticles() {
		return parseInteger(dbObject.get("cumulativeNumberOfArticles")+"", 0);
	}
	
	public NewsgroupsArticlesHistoricMetric setCumulativeNumberOfArticles(int cumulativeNumberOfArticles) {
		dbObject.put("cumulativeNumberOfArticles", cumulativeNumberOfArticles);
		notifyChanged();
		return this;
	}
	
	
	public List<DailyNewsgroupData> getNewsgroups() {
		if (newsgroups == null) {
			newsgroups = new PongoList<DailyNewsgroupData>(this, "newsgroups", true);
		}
		return newsgroups;
	}
	
	
}