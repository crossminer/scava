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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.topics.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupTopicsHistoricMetric extends Pongo {
	
	protected List<NewsgrpTopic> newsgrpTopics = null;
	
	
	public NewsgroupTopicsHistoricMetric() { 
		super();
		dbObject.put("newsgrpTopics", new BasicDBList());
	}
	
	
	
	
	
	public List<NewsgrpTopic> getNewsgrpTopics() {
		if (newsgrpTopics == null) {
			newsgrpTopics = new PongoList<NewsgrpTopic>(this, "newsgrpTopics", true);
		}
		return newsgrpTopics;
	}
	
	
}