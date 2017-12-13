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
package org.eclipse.crossmeter.metricprovider.historic.bugs.emotions.model;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoList;
import com.mongodb.BasicDBList;


public class BugsEmotionsHistoricMetric extends Pongo {
	
	protected List<BugData> bugData = null;
	protected List<Dimension> dimensions = null;
	
	
	public BugsEmotionsHistoricMetric() { 
		super();
		dbObject.put("bugData", new BasicDBList());
		dbObject.put("dimensions", new BasicDBList());
	}
	
	
	
	
	
	public List<BugData> getBugData() {
		if (bugData == null) {
			bugData = new PongoList<BugData>(this, "bugData", true);
		}
		return bugData;
	}
	public List<Dimension> getDimensions() {
		if (dimensions == null) {
			dimensions = new PongoList<Dimension>(this, "dimensions", true);
		}
		return dimensions;
	}
	
	
}