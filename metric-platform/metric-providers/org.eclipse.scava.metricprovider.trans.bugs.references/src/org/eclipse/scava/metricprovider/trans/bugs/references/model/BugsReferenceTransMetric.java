/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.references.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsReferenceTransMetric extends PongoDB {
	
	public BugsReferenceTransMetric() {}
	
	public BugsReferenceTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugReferringToCollection bugsReferringTo = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugReferringToCollection getBugsReferringTo() {
		return bugsReferringTo;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugsReferringTo = new BugReferringToCollection(db.getCollection("BugsReferenceTransMetric.bugsReferringTo"));
		pongoCollections.add(bugsReferringTo);
	}
}