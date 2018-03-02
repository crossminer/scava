/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.emotions.model;

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
