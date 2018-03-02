/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.topics.model;

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
