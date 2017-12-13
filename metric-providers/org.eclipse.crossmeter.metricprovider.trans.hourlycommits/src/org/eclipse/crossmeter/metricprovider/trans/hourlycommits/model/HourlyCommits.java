/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.hourlycommits.model;

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