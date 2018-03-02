package org.eclipse.scava.repository.model.github;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.eclipse.scava.platform.factoids.*;
import org.eclipse.scava.repository.model.*;
import org.eclipse.scava.repository.model.bts.bugzilla.*;
import org.eclipse.scava.repository.model.cc.forum.*;
import org.eclipse.scava.repository.model.cc.nntp.*;
import org.eclipse.scava.repository.model.cc.wiki.*;
import org.eclipse.scava.repository.model.eclipse.*;
import org.eclipse.scava.repository.model.github.*;
import org.eclipse.scava.repository.model.googlecode.*;
import org.eclipse.scava.repository.model.metrics.*;
import org.eclipse.scava.repository.model.redmine.*;
import org.eclipse.scava.repository.model.sourceforge.*;
import org.eclipse.scava.repository.model.vcs.cvs.*;
import org.eclipse.scava.repository.model.vcs.git.*;
import org.eclipse.scava.repository.model.vcs.svn.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = GitHubMilestone.class, name="org.eclipse.scava.repository.model.github.GitHubMilestone"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubMilestone extends Object {

	protected List<GitHubIssue> open_issues;
	protected List<GitHubIssue> closed_issues;
	protected String url;
	protected int number;
	protected String state;
	protected String title;
	protected String description;
	protected GitHubUser creator;
	protected String created_at;
	protected String due_on;
	
	public String getUrl() {
		return url;
	}
	public int getNumber() {
		return number;
	}
	public String getState() {
		return state;
	}
	public String getTitle() {
		return title;
	}
	public String getDescription() {
		return description;
	}
	public String getCreated_at() {
		return created_at;
	}
	public String getDue_on() {
		return due_on;
	}
	
	public List<GitHubIssue> getOpen_issues() {
		return open_issues;
	}
	public List<GitHubIssue> getClosed_issues() {
		return closed_issues;
	}
}
