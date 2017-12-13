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
package org.eclipse.crossmeter.metricprovider.historic.bugs.severity.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugsSeveritiesHistoricMetric extends Pongo {
	
	protected List<BugData> bugData = null;
	protected List<SeverityLevel> severityLevels = null;
	
	
	public BugsSeveritiesHistoricMetric() { 
		super();
		dbObject.put("bugData", new BasicDBList());
		dbObject.put("severityLevels", new BasicDBList());
	}
	
	
	
	
	
	public List<BugData> getBugData() {
		if (bugData == null) {
			bugData = new PongoList<BugData>(this, "bugData", true);
		}
		return bugData;
	}
	public List<SeverityLevel> getSeverityLevels() {
		if (severityLevels == null) {
			severityLevels = new PongoList<SeverityLevel>(this, "severityLevels", true);
		}
		return severityLevels;
	}
	
	
}