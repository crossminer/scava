/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.activeusers.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class NewsgroupsActiveUsersTransMetric extends PongoDB {
	
	public NewsgroupsActiveUsersTransMetric() {}
	
	public NewsgroupsActiveUsersTransMetric(DB db) {
		setDb(db);
	}
	
	protected NewsgroupDataCollection newsgroups = null;
	protected UserCollection users = null;
	
	
	
	public NewsgroupDataCollection getNewsgroups() {
		return newsgroups;
	}
	
	public UserCollection getUsers() {
		return users;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		newsgroups = new NewsgroupDataCollection(db.getCollection("NewsgroupsActiveUsersTransMetric.newsgroups"));
		pongoCollections.add(newsgroups);
		users = new UserCollection(db.getCollection("NewsgroupsActiveUsersTransMetric.users"));
		pongoCollections.add(users);
	}
}
