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
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;
import com.mongodb.DBObject;


public class GitHubCommit extends Pongo {
	
	protected List<GitHubComment> comments = null;
	protected List<GitHubCommit> parents = null;
	protected GitHubUser author = null;
	protected GitHubUser committer = null;
	
	
	public GitHubCommit() { 
		super();
		dbObject.put("comments", new BasicDBList());
		dbObject.put("parents", new BasicDBList());
		URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubCommit");
		SHA.setOwningType("org.eclipse.scava.repository.model.github.GitHubCommit");
		MESSAGE.setOwningType("org.eclipse.scava.repository.model.github.GitHubCommit");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer SHA = new StringQueryProducer("sha"); 
	public static StringQueryProducer MESSAGE = new StringQueryProducer("message"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubCommit setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getSha() {
		return parseString(dbObject.get("sha")+"", "");
	}
	
	public GitHubCommit setSha(String sha) {
		dbObject.put("sha", sha);
		notifyChanged();
		return this;
	}
	public String getMessage() {
		return parseString(dbObject.get("message")+"", "");
	}
	
	public GitHubCommit setMessage(String message) {
		dbObject.put("message", message);
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubComment> getComments() {
		if (comments == null) {
			comments = new PongoList<GitHubComment>(this, "comments", true);
		}
		return comments;
	}
	public List<GitHubCommit> getParents() {
		if (parents == null) {
			parents = new PongoList<GitHubCommit>(this, "parents", true);
		}
		return parents;
	}
	
	
	public GitHubUser getAuthor() {
		if (author == null && dbObject.containsField("author")) {
			author = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("author"));
		}
		return author;
	}
	
	public GitHubCommit setAuthor(GitHubUser author) {
		if (this.author != author) {
			if (author == null) {
				dbObject.removeField("author");
			}
			else {
				dbObject.put("author", author.getDbObject());
			}
			this.author = author;
			notifyChanged();
		}
		return this;
	}
	public GitHubUser getCommitter() {
		if (committer == null && dbObject.containsField("committer")) {
			committer = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("committer"));
		}
		return committer;
	}
	
	public GitHubCommit setCommitter(GitHubUser committer) {
		if (this.committer != committer) {
			if (committer == null) {
				dbObject.removeField("committer");
			}
			else {
				dbObject.put("committer", committer.getDbObject());
			}
			this.committer = committer;
			notifyChanged();
		}
		return this;
	}
}
