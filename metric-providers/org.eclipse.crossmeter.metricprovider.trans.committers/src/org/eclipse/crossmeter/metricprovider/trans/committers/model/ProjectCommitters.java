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
package org.eclipse.crossmeter.metricprovider.trans.committers.model;

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