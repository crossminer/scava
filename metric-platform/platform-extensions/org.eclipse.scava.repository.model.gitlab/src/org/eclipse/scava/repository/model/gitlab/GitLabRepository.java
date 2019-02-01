/*******************************************************************************
 * Copyright (c) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.repository.model.gitlab;

import com.googlecode.pongo.runtime.querying.StringQueryProducer;


public class GitLabRepository extends org.eclipse.scava.repository.model.Project {
	
	public GitLabRepository() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.github.Project");
		FULL_NAME.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		CLONE_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		GIT_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
	}
	
	public static StringQueryProducer FULL_NAME = new StringQueryProducer("full_name"); 
	public static StringQueryProducer CLONE_URL = new StringQueryProducer("clone_url"); 
	public static StringQueryProducer GIT_URL = new StringQueryProducer("git_url"); 
	
	
	public String getFull_name() {
		return parseString(dbObject.get("full_name")+"", "");
	}
	
	public GitLabRepository setFull_name(String full_name) {
		dbObject.put("full_name", full_name);
		notifyChanged();
		return this;
	}

	public String getClone_url() {
		return parseString(dbObject.get("clone_url")+"", "");
	}
	
	public GitLabRepository setClone_url(String clone_url) {
		dbObject.put("clone_url", clone_url);
		notifyChanged();
		return this;
	}
	public String getGit_url() {
		return parseString(dbObject.get("git_url")+"", "");
	}
	
	public GitLabRepository setGit_url(String git_url) {
		dbObject.put("git_url", git_url);
		notifyChanged();
		return this;
	}
}
