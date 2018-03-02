/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.newbugs.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class BugsNewBugsTransMetric extends PongoDB {
	
	public BugsNewBugsTransMetric() {}
	
	public BugsNewBugsTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerDataCollection bugTrackerData = null;
	
	
	
	public BugTrackerDataCollection getBugTrackerData() {
		return bugTrackerData;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerData = new BugTrackerDataCollection(db.getCollection("BugsNewBugsTransMetric.bugTrackerData"));
		pongoCollections.add(bugTrackerData);
	}
}
