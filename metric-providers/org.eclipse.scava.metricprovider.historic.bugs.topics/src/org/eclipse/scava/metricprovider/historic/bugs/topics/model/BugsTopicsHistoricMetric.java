/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.topics.model;

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
