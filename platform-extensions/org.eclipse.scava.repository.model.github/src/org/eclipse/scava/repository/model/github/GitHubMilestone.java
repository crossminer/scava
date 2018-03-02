/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.github;

import java.util.List;

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.PongoList;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


public class GitHubMilestone extends Pongo {
	
	protected List<GitHubIssue> open_issues = null;
	protected List<GitHubIssue> closed_issues = null;
	protected GitHubUser creator = null;
	
	
	public GitHubMilestone() { 
		super();
		dbObject.put("open_issues", new BasicDBList());
		dbObject.put("closed_issues", new BasicDBList());
		URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
		NUMBER.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
		STATE.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
		TITLE.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
		DESCRIPTION.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
		DUE_ON.setOwningType("org.eclipse.scava.repository.model.github.GitHubMilestone");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static NumericalQueryProducer NUMBER = new NumericalQueryProducer("number");
	public static StringQueryProducer STATE = new StringQueryProducer("state"); 
	public static StringQueryProducer TITLE = new StringQueryProducer("title"); 
	public static StringQueryProducer DESCRIPTION = new StringQueryProducer("description"); 
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer DUE_ON = new StringQueryProducer("due_on"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubMilestone setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public int getNumber() {
		return parseInteger(dbObject.get("number")+"", 0);
	}
	
	public GitHubMilestone setNumber(int number) {
		dbObject.put("number", number);
		notifyChanged();
		return this;
	}
	public String getState() {
		return parseString(dbObject.get("state")+"", "");
	}
	
	public GitHubMilestone setState(String state) {
		dbObject.put("state", state);
		notifyChanged();
		return this;
	}
	public String getTitle() {
		return parseString(dbObject.get("title")+"", "");
	}
	
	public GitHubMilestone setTitle(String title) {
		dbObject.put("title", title);
		notifyChanged();
		return this;
	}
	public String getDescription() {
		return parseString(dbObject.get("description")+"", "");
	}
	
	public GitHubMilestone setDescription(String description) {
		dbObject.put("description", description);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GitHubMilestone setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getDue_on() {
		return parseString(dbObject.get("due_on")+"", "");
	}
	
	public GitHubMilestone setDue_on(String due_on) {
		dbObject.put("due_on", due_on);
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubIssue> getOpen_issues() {
		if (open_issues == null) {
			open_issues = new PongoList<GitHubIssue>(this, "open_issues", true);
		}
		return open_issues;
	}
	public List<GitHubIssue> getClosed_issues() {
		if (closed_issues == null) {
			closed_issues = new PongoList<GitHubIssue>(this, "closed_issues", true);
		}
		return closed_issues;
	}
	
	
	public GitHubUser getCreator() {
		if (creator == null && dbObject.containsField("creator")) {
			creator = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("creator"));
		}
		return creator;
	}
	
	public GitHubMilestone setCreator(GitHubUser creator) {
		if (this.creator != creator) {
			if (creator == null) {
				dbObject.removeField("creator");
			}
			else {
				dbObject.put("creator", creator.getDbObject());
			}
			this.creator = creator;
			notifyChanged();
		}
		return this;
	}
}
