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

import com.googlecode.pongo.runtime.Pongo;
import com.googlecode.pongo.runtime.PongoFactory;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.DBObject;


public class GitHubComment extends Pongo {
	
	protected GitHubUser user = null;
	
	
	public GitHubComment() { 
		super();
		URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
		BODY.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
		PATH.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
		POSITION.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
		LINE.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
		CREATED_AT.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
		UPDATED_AT.setOwningType("org.eclipse.scava.repository.model.github.GitHubComment");
	}
	
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer BODY = new StringQueryProducer("body"); 
	public static StringQueryProducer PATH = new StringQueryProducer("path"); 
	public static NumericalQueryProducer POSITION = new NumericalQueryProducer("position");
	public static NumericalQueryProducer LINE = new NumericalQueryProducer("line");
	public static StringQueryProducer CREATED_AT = new StringQueryProducer("created_at"); 
	public static StringQueryProducer UPDATED_AT = new StringQueryProducer("updated_at"); 
	
	
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubComment setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getBody() {
		return parseString(dbObject.get("body")+"", "");
	}
	
	public GitHubComment setBody(String body) {
		dbObject.put("body", body);
		notifyChanged();
		return this;
	}
	public String getPath() {
		return parseString(dbObject.get("path")+"", "");
	}
	
	public GitHubComment setPath(String path) {
		dbObject.put("path", path);
		notifyChanged();
		return this;
	}
	public int getPosition() {
		return parseInteger(dbObject.get("position")+"", 0);
	}
	
	public GitHubComment setPosition(int position) {
		dbObject.put("position", position);
		notifyChanged();
		return this;
	}
	public int getLine() {
		return parseInteger(dbObject.get("line")+"", 0);
	}
	
	public GitHubComment setLine(int line) {
		dbObject.put("line", line);
		notifyChanged();
		return this;
	}
	public String getCreated_at() {
		return parseString(dbObject.get("created_at")+"", "");
	}
	
	public GitHubComment setCreated_at(String created_at) {
		dbObject.put("created_at", created_at);
		notifyChanged();
		return this;
	}
	public String getUpdated_at() {
		return parseString(dbObject.get("updated_at")+"", "");
	}
	
	public GitHubComment setUpdated_at(String updated_at) {
		dbObject.put("updated_at", updated_at);
		notifyChanged();
		return this;
	}
	
	
	
	
	public GitHubUser getUser() {
		if (user == null && dbObject.containsField("user")) {
			user = (GitHubUser) PongoFactory.getInstance().createPongo((DBObject) dbObject.get("user"));
		}
		return user;
	}
	
	public GitHubComment setUser(GitHubUser user) {
		if (this.user != user) {
			if (user == null) {
				dbObject.removeField("user");
			}
			else {
				dbObject.put("user", user.getDbObject());
			}
			this.user = user;
			notifyChanged();
		}
		return this;
	}
}
