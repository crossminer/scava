package org.eclipse.scava.repository.model.googlecode;

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
	@Type(value = GoogleIssue.class, name="org.eclipse.scava.repository.model.googlecode.GoogleIssue"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleIssue extends Object {

	protected List<GoogleIssueComment> comments;
	protected List<GoogleLabel> labels;
	protected GoogleUser owner;
	protected String created_at;
	protected String updated_at;
	protected String priority;
	protected String type;
	protected String component;
	protected String status;
	protected int stars;
	protected String summary;
	
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getPriority() {
		return priority;
	}
	public String getType() {
		return type;
	}
	public String getComponent() {
		return component;
	}
	public String getStatus() {
		return status;
	}
	public int getStars() {
		return stars;
	}
	public String getSummary() {
		return summary;
	}
	
	public List<GoogleIssueComment> getComments() {
		return comments;
	}
	public List<GoogleLabel> getLabels() {
		return labels;
	}
}
