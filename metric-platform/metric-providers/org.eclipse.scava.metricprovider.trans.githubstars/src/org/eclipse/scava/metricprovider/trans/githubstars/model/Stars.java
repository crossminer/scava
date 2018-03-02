/*******************************************************************************
 * Copyright (c) 2018 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.githubstars.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Stars extends PongoDB {
	
	public Stars() {}
	
	public Stars(DB db) {
		setDb(db);
	}
	
	protected StargazersCollection stargazers = null;
	
	
	
	public StargazersCollection getStargazers() {
		return stargazers;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		stargazers = new StargazersCollection(db.getCollection("Stars.stargazers"));
		pongoCollections.add(stargazers);
	}
}
