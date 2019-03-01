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

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class GitHubUser extends org.eclipse.scava.repository.model.Person {
	
	
	
	public GitHubUser() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.github.Person");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.github.GitHubUser");
		HTML_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubUser");
		URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubUser");
		FOLLOWERS_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubUser");
	}
	
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	public static StringQueryProducer URL = new StringQueryProducer("url"); 
	public static StringQueryProducer FOLLOWERS_URL = new StringQueryProducer("followers_url"); 
	
	
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public GitHubUser setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubUser setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
	public String getUrl() {
		return parseString(dbObject.get("url")+"", "");
	}
	
	public GitHubUser setUrl(String url) {
		dbObject.put("url", url);
		notifyChanged();
		return this;
	}
	public String getFollowers_url() {
		return parseString(dbObject.get("followers_url")+"", "");
	}
	
	public GitHubUser setFollowers_url(String followers_url) {
		dbObject.put("followers_url", followers_url);
		notifyChanged();
		return this;
	}
	
	
	
	
}
