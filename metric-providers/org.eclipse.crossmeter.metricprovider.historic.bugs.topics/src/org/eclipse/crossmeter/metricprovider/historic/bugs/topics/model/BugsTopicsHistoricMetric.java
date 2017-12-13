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
package org.eclipse.crossmeter.metricprovider.historic.bugs.topics.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugsTopicsHistoricMetric extends Pongo {
	
	protected List<BugTopic> bugTopics = null;
	
	
	public BugsTopicsHistoricMetric() { 
		super();
		dbObject.put("bugTopics", new BasicDBList());
	}
	
	
	
	
	
	public List<BugTopic> getBugTopics() {
		if (bugTopics == null) {
			bugTopics = new PongoList<BugTopic>(this, "bugTopics", true);
		}
		return bugTopics;
	}
	
	
}