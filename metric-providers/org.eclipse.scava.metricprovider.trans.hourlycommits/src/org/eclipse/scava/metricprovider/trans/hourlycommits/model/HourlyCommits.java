/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.hourlycommits.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class HourlyCommits extends PongoDB {
	
	public HourlyCommits() {}
	
	public HourlyCommits(DB db) {
		setDb(db);
	}
	
	protected HourCollection hours = null;
	
	
	
	public HourCollection getHours() {
		return hours;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		hours = new HourCollection(db.getCollection("HourlyCommits.hours"));
		pongoCollections.add(hours);
	}
}
