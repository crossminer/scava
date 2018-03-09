/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.dailycommits.model;

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
