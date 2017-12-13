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
package org.eclipse.crossmeter.metricprovider.trans.commits.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;

public class Commits extends PongoDB {
	
	public Commits() {}
	
	public Commits(DB db) {
		setDb(db);
	}
	
	protected RepositoryDataCollection repositories = null;
	protected CommitDataCollection commits = null;
	
	
	
	public RepositoryDataCollection getRepositories() {
		return repositories;
	}
	
	public CommitDataCollection getCommits() {
		return commits;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		repositories = new RepositoryDataCollection(db.getCollection("Commits.repositories"));
		pongoCollections.add(repositories);
		commits = new CommitDataCollection(db.getCollection("Commits.commits"));
		pongoCollections.add(commits);
	}
}