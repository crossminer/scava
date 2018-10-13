package org.eclipse.scava.repository.model.redmine;

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
	@Type(value = RedmineProject.class, name="org.eclipse.scava.repository.model.redmine.RedmineProject"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineProject extends Project {

	protected List<RedmineBugIssueTracker> issueTracker;
	protected List<RedmineUser> members;
	protected List<RedmineProjectVersion> versions;
	protected String identifier;
	protected String description;
	protected String created_on;
	protected String updated_on;
	protected RedmineWiki wiki;
	protected RedmineQueryManager queryManager;
	
	public String getIdentifier() {
		return identifier;
	}
	public String getDescription() {
		return description;
	}
	public String getCreated_on() {
		return created_on;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	
	public List<RedmineBugIssueTracker> getIssueTracker() {
		return issueTracker;
	}
	public List<RedmineUser> getMembers() {
		return members;
	}
	public List<RedmineProjectVersion> getVersions() {
		return versions;
	}
}
