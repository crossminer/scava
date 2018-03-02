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

import com.googlecode.pongo.runtime.PongoList;
import com.googlecode.pongo.runtime.querying.NumericalQueryProducer;
import com.googlecode.pongo.runtime.querying.StringQueryProducer;
import com.mongodb.BasicDBList;


public class GitHubRepository extends org.eclipse.scava.repository.model.Project {
	
	protected List<Language> languages = null;
	protected List<GitHubContent> contents = null;
	protected List<GitHubDownload> downloads = null;
	protected List<GitHubRepository> forks = null;
	protected List<GitHubMilestone> milestones = null;
	
	
	public GitHubRepository() { 
		super();
		dbObject.put("languages", new BasicDBList());
		dbObject.put("contents", new BasicDBList());
		dbObject.put("downloads", new BasicDBList());
		dbObject.put("forks", new BasicDBList());
		dbObject.put("milestones", new BasicDBList());
		super.setSuperTypes("org.eclipse.scava.repository.model.github.Project");
		FULL_NAME.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		_PRIVATE.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		FORK.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		HTML_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		CLONE_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		GIT_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		SSH_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		SVN_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		MIRROR_URL.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		SIZE.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
		MASTER_BRANCH.setOwningType("org.eclipse.scava.repository.model.github.GitHubRepository");
	}
	
	public static StringQueryProducer FULL_NAME = new StringQueryProducer("full_name"); 
	public static StringQueryProducer _PRIVATE = new StringQueryProducer("_private"); 
	public static StringQueryProducer FORK = new StringQueryProducer("fork"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url"); 
	public static StringQueryProducer CLONE_URL = new StringQueryProducer("clone_url"); 
	public static StringQueryProducer GIT_URL = new StringQueryProducer("git_url"); 
	public static StringQueryProducer SSH_URL = new StringQueryProducer("ssh_url"); 
	public static StringQueryProducer SVN_URL = new StringQueryProducer("svn_url"); 
	public static StringQueryProducer MIRROR_URL = new StringQueryProducer("mirror_url"); 
	public static NumericalQueryProducer SIZE = new NumericalQueryProducer("size");
	public static StringQueryProducer MASTER_BRANCH = new StringQueryProducer("master_branch"); 
	
	
	public String getFull_name() {
		return parseString(dbObject.get("full_name")+"", "");
	}
	
	public GitHubRepository setFull_name(String full_name) {
		dbObject.put("full_name", full_name);
		notifyChanged();
		return this;
	}
	public boolean get_private() {
		return parseBoolean(dbObject.get("_private")+"", false);
	}
	
	public GitHubRepository set_private(boolean _private) {
		dbObject.put("_private", _private);
		notifyChanged();
		return this;
	}
	public boolean getFork() {
		return parseBoolean(dbObject.get("fork")+"", false);
	}
	
	public GitHubRepository setFork(boolean fork) {
		dbObject.put("fork", fork);
		notifyChanged();
		return this;
	}
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitHubRepository setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
	public String getClone_url() {
		return parseString(dbObject.get("clone_url")+"", "");
	}
	
	public GitHubRepository setClone_url(String clone_url) {
		dbObject.put("clone_url", clone_url);
		notifyChanged();
		return this;
	}
	public String getGit_url() {
		return parseString(dbObject.get("git_url")+"", "");
	}
	
	public GitHubRepository setGit_url(String git_url) {
		dbObject.put("git_url", git_url);
		notifyChanged();
		return this;
	}
	public String getSsh_url() {
		return parseString(dbObject.get("ssh_url")+"", "");
	}
	
	public GitHubRepository setSsh_url(String ssh_url) {
		dbObject.put("ssh_url", ssh_url);
		notifyChanged();
		return this;
	}
	public String getSvn_url() {
		return parseString(dbObject.get("svn_url")+"", "");
	}
	
	public GitHubRepository setSvn_url(String svn_url) {
		dbObject.put("svn_url", svn_url);
		notifyChanged();
		return this;
	}
	public String getMirror_url() {
		return parseString(dbObject.get("mirror_url")+"", "");
	}
	
	public GitHubRepository setMirror_url(String mirror_url) {
		dbObject.put("mirror_url", mirror_url);
		notifyChanged();
		return this;
	}
	public int getSize() {
		return parseInteger(dbObject.get("size")+"", 0);
	}
	
	public GitHubRepository setSize(int size) {
		dbObject.put("size", size);
		notifyChanged();
		return this;
	}
	public String getMaster_branch() {
		return parseString(dbObject.get("master_branch")+"", "");
	}
	
	public GitHubRepository setMaster_branch(String master_branch) {
		dbObject.put("master_branch", master_branch);
		notifyChanged();
		return this;
	}
	
	
	public List<Language> getLanguages() {
		if (languages == null) {
			languages = new PongoList<Language>(this, "languages", true);
		}
		return languages;
	}
	public List<GitHubContent> getContents() {
		if (contents == null) {
			contents = new PongoList<GitHubContent>(this, "contents", true);
		}
		return contents;
	}
	public List<GitHubDownload> getDownloads() {
		if (downloads == null) {
			downloads = new PongoList<GitHubDownload>(this, "downloads", true);
		}
		return downloads;
	}
	public List<GitHubRepository> getForks() {
		if (forks == null) {
			forks = new PongoList<GitHubRepository>(this, "forks", false);
		}
		return forks;
	}
	public List<GitHubMilestone> getMilestones() {
		if (milestones == null) {
			milestones = new PongoList<GitHubMilestone>(this, "milestones", true);
		}
		return milestones;
	}
	
	
}
