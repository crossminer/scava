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

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;

// protected region custom-imports on begin
// protected region custom-imports end

public class GitHubBugTracker extends org.eclipse.scava.repository.model.BugTrackingSystem {
	
	protected List<GitHubIssue> issues = null;
	
	// protected region custom-fields-and-methods on begin
    @Override
    public String getBugTrackerType() {
        return "github";
    }

    @Override
    public String getInstanceId() {
        return getUser() + '/' + getRepository();
    }

    // protected region custom-fields-and-methods end
	
	public GitHubBugTracker() { 
		super();
		dbObject.put("issues", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.github.BugTrackingSystem");
		USER.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		REPOSITORY.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		LOGIN.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
		PASSWORD.setOwningType("org.eclipse.scava.repository.model.github.GitHubBugTracker");
	}
	
	public static StringQueryProducer USER = new StringQueryProducer("user"); 
	public static StringQueryProducer REPOSITORY = new StringQueryProducer("repository"); 
	public static StringQueryProducer LOGIN = new StringQueryProducer("login"); 
	public static StringQueryProducer PASSWORD = new StringQueryProducer("password"); 
	
	
	public String getUser() {
		return parseString(dbObject.get("user")+"", "");
	}
	
	public GitHubBugTracker setUser(String user) {
		dbObject.put("user", user);
		notifyChanged();
		return this;
	}
	public String getRepository() {
		return parseString(dbObject.get("repository")+"", "");
	}
	
	public GitHubBugTracker setRepository(String repository) {
		dbObject.put("repository", repository);
		notifyChanged();
		return this;
	}
	public String getLogin() {
		return parseString(dbObject.get("login")+"", "");
	}
	
	public GitHubBugTracker setLogin(String login) {
		dbObject.put("login", login);
		notifyChanged();
		return this;
	}
	public String getPassword() {
		return parseString(dbObject.get("password")+"", "");
	}
	
	public GitHubBugTracker setPassword(String password) {
		dbObject.put("password", password);
		notifyChanged();
		return this;
	}
	
	
	public List<GitHubIssue> getIssues() {
		if (issues == null) {
			issues = new PongoList<GitHubIssue>(this, "issues", true);
		}
		return issues;
	}
	
	
}
