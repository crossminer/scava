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
package org.eclipse.crossmeter.metricprovider.trans.newsgroups.activeusers.model;

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