/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.commits.model;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class RepositoryData extends Pongo {
	
	protected List<CommitData> commits = null;
	
	
	public RepositoryData() { 
		super();
		dbObject.put("commits", new BasicDBList());
		URL.setOwningType("org.eclipse.scava.metricprovider.trans.commits.model.RepositoryData");
		REPOTYPE.setOwningType("org.eclipse.scava.metricprovider.trans.commits.model.RepositoryData");
		REVISION.setOwningType("org.eclipse.scava.metricprovider.trans.commits.model.RepositoryData");
		TOTALCOMMITS.setOwningType("org.eclipse.scava.metricprovider.trans.commits.model.RepositoryData");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer REPOTYPE = new StringQueryProducer("repoType"); 
	public static StringQueryProducer REVISION = new StringQueryProducer("revision"); 
	public static NumericalQueryProducer TOTALCOMMITS = new NumericalQueryProducer("totalCommits");
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public RepositoryData setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getRepoType() {
		return parseString(dbObject.get("repoType")+"", "");
	}
	
	public RepositoryData setRepoType(String repoType) {
		dbObject.put("repoType", repoType);
		notifyChanged();
		return this;
	}
	public String getRevision() {
		return parseString(dbObject.get("revision")+"", "");
	}
	
	public RepositoryData setRevision(String revision) {
		dbObject.put("revision", revision);
		notifyChanged();
		return this;
	}
	public int getTotalCommits() {
		return parseInteger(dbObject.get("totalCommits")+"", 0);
	}
	
	public RepositoryData setTotalCommits(int totalCommits) {
		dbObject.put("totalCommits", totalCommits);
		notifyChanged();
		return this;
	}
	
	
	public List<CommitData> getCommits() {
		if (commits == null) {
			commits = new PongoList<CommitData>(this, "commits", false);
		}
		return commits;
	}
	
	
}
