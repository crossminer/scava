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
package org.eclipse.crossmeter.metricprovider.trans.dailycommits.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class DailyCommits extends PongoDB {
	
	public DailyCommits() {}
	
	public DailyCommits(DB db) {
		setDb(db);
	}
	
	protected DayCollection days = null;
	
	
	
	public DayCollection getDays() {
		return days;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		days = new DayCollection(db.getCollection("DailyCommits.days"));
		pongoCollections.add(days);
	}
}