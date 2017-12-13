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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.severity.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class NewsgroupsSeveritiesHistoricMetric extends Pongo {
	
	protected List<NewsgroupData> newsgroupData = null;
	protected List<SeverityLevel> severityLevels = null;
	
	
	public NewsgroupsSeveritiesHistoricMetric() { 
		super();
		dbObject.put("newsgroupData", new BasicDBList());
		dbObject.put("severityLevels", new BasicDBList());
	}
	
	
	
	
	
	public List<NewsgroupData> getNewsgroupData() {
		if (newsgroupData == null) {
			newsgroupData = new PongoList<NewsgroupData>(this, "newsgroupData", true);
		}
		return newsgroupData;
	}
	public List<SeverityLevel> getSeverityLevels() {
		if (severityLevels == null) {
			severityLevels = new PongoList<SeverityLevel>(this, "severityLevels", true);
		}
		return severityLevels;
	}
	
	
}