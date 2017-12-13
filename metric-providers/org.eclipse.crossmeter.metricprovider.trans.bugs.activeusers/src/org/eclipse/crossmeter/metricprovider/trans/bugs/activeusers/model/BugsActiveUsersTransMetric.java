/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class BugsActiveUsersTransMetric extends PongoDB {
	
	public BugsActiveUsersTransMetric() {}
	
	public BugsActiveUsersTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugDataCollection bugs = null;
	protected UserCollection users = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugDataCollection getBugs() {
		return bugs;
	}
	
	public UserCollection getUsers() {
		return users;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugs = new BugDataCollection(db.getCollection("BugsActiveUsersTransMetric.bugs"));
		pongoCollections.add(bugs);
		users = new UserCollection(db.getCollection("BugsActiveUsersTransMetric.users"));
		pongoCollections.add(users);
	}
}