package org.eclipse.scava.repository.model;

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
	@Type(value = BugTrackingSystem.class, name="org.eclipse.scava.repository.model.BugTrackingSystem"), 	@Type(value = org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla.class, name="org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla"),
	@Type(value = org.eclipse.scava.repository.model.github.GitHubBugTracker.class, name="org.eclipse.scava.repository.model.github.GitHubBugTracker"),
	@Type(value = org.eclipse.scava.repository.model.googlecode.GoogleIssueTracker.class, name="org.eclipse.scava.repository.model.googlecode.GoogleIssueTracker"),
	@Type(value = org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker.class, name="org.eclipse.scava.repository.model.redmine.RedmineBugIssueTracker"),
	@Type(value = org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem.class, name="org.eclipse.scava.repository.model.sourceforge.SourceForgeBugTrackingSystem"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class BugTrackingSystem extends Object {

	protected List<Person> persons;
	protected String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
