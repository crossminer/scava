package org.eclipse.scava.repository.model.gitlab;

import com.mongodb.*;
import java.util.*;
import com.googlecode.pongo.runtime.*;
import com.googlecode.pongo.runtime.querying.*;


public class GitLabRepository extends org.eclipse.scava.repository.model.Project {
	
	
	
	public GitLabRepository() { 
		super();
		super.setSuperTypes("org.eclipse.scava.repository.model.gitlab.Project");
		FULL_NAME.setOwningType("org.eclipse.scava.repository.model.gitlab.GitLabRepository");
		CLONE_URL.setOwningType("org.eclipse.scava.repository.model.gitlab.GitLabRepository");
		GIT_URL.setOwningType("org.eclipse.scava.repository.model.gitlab.GitLabRepository");
		HTML_URL.setOwningType("org.eclipse.scava.repository.model.gitlab.GitLabRepository");
	}
	
	public static StringQueryProducer FULL_NAME = new StringQueryProducer("full_name"); 
	public static StringQueryProducer CLONE_URL = new StringQueryProducer("clone_url"); 
	public static StringQueryProducer GIT_URL = new StringQueryProducer("git_url"); 
	public static StringQueryProducer HTML_URL = new StringQueryProducer("html_url");
	
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
	
	public String getHtml_url() {
		return parseString(dbObject.get("html_url")+"", "");
	}
	
	public GitLabRepository setHtml_url(String html_url) {
		dbObject.put("html_url", html_url);
		notifyChanged();
		return this;
	}
}