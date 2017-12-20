package org.eclipse.crossmeter.repository.model.redmine;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import org.eclipse.crossmeter.platform.factoids.*;
import org.eclipse.crossmeter.repository.model.*;
import org.eclipse.crossmeter.repository.model.bts.bugzilla.*;
import org.eclipse.crossmeter.repository.model.cc.forum.*;
import org.eclipse.crossmeter.repository.model.cc.nntp.*;
import org.eclipse.crossmeter.repository.model.cc.wiki.*;
import org.eclipse.crossmeter.repository.model.eclipse.*;
import org.eclipse.crossmeter.repository.model.github.*;
import org.eclipse.crossmeter.repository.model.googlecode.*;
import org.eclipse.crossmeter.repository.model.metrics.*;
import org.eclipse.crossmeter.repository.model.redmine.*;
import org.eclipse.crossmeter.repository.model.sourceforge.*;
import org.eclipse.crossmeter.repository.model.vcs.cvs.*;
import org.eclipse.crossmeter.repository.model.vcs.git.*;
import org.eclipse.crossmeter.repository.model.vcs.svn.*;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME,
	include=JsonTypeInfo.As.PROPERTY,
	property = "_type")
@JsonSubTypes({
	@Type(value = RedmineIssue.class, name="org.eclipse.crossmeter.repository.model.redmine.RedmineIssue"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedmineIssue extends Object {

	protected List<RedminIssueRelation> relations;
	protected RedmineCategory category;
	protected String description;
	protected String status;
	protected RedmineFeature feature;
	protected String priority;
	protected RedmineUser author;
	protected String template;
	protected String start_date;
	protected String update_date;
	protected String due_date;
	protected RedmineUser assignedTo;
	
	public String getDescription() {
		return description;
	}
	public String getStatus() {
		return status;
	}
	public String getPriority() {
		return priority;
	}
	public String getTemplate() {
		return template;
	}
	public String getStart_date() {
		return start_date;
	}
	public String getUpdate_date() {
		return update_date;
	}
	public String getDue_date() {
		return due_date;
	}
	
	public List<RedminIssueRelation> getRelations() {
		return relations;
	}
}
