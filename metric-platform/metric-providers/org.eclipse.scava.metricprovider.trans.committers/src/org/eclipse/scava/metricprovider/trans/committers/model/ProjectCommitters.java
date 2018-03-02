/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.committers.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class ProjectCommitters extends PongoDB {
	
	public ProjectCommitters() {}
	
	public ProjectCommitters(DB db) {
		setDb(db);
	}
	
	protected CommitterCollection committers = null;
	
	
	
	public CommitterCollection getCommitters() {
		return committers;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		committers = new CommitterCollection(db.getCollection("committers"));
		pongoCollections.add(committers);
	}
}
