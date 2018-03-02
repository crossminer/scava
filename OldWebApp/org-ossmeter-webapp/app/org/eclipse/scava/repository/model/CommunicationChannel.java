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
	@Type(value = CommunicationChannel.class, name="org.eclipse.scava.repository.model.CommunicationChannel"), 	@Type(value = org.eclipse.scava.repository.model.eclipse.MailingList.class, name="org.eclipse.scava.repository.model.eclipse.MailingList"),
	@Type(value = org.eclipse.scava.repository.model.eclipse.Documentation.class, name="org.eclipse.scava.repository.model.eclipse.Documentation"),
	@Type(value = org.eclipse.scava.repository.model.cc.forum.Forum.class, name="org.eclipse.scava.repository.model.cc.forum.Forum"),
	@Type(value = org.eclipse.scava.repository.model.googlecode.GoogleWiki.class, name="org.eclipse.scava.repository.model.googlecode.GoogleWiki"),
	@Type(value = org.eclipse.scava.repository.model.googlecode.GoogleForum.class, name="org.eclipse.scava.repository.model.googlecode.GoogleForum"),
	@Type(value = org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup.class, name="org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup"),
	@Type(value = org.eclipse.scava.repository.model.redmine.RedmineWiki.class, name="org.eclipse.scava.repository.model.redmine.RedmineWiki"),
	@Type(value = org.eclipse.scava.repository.model.redmine.RedmineQueryManager.class, name="org.eclipse.scava.repository.model.redmine.RedmineQueryManager"),
	@Type(value = org.eclipse.scava.repository.model.sourceforge.MailingList.class, name="org.eclipse.scava.repository.model.sourceforge.MailingList"),
	@Type(value = org.eclipse.scava.repository.model.sourceforge.Discussion.class, name="org.eclipse.scava.repository.model.sourceforge.Discussion"),
	@Type(value = org.eclipse.scava.repository.model.cc.wiki.Wiki.class, name="org.eclipse.scava.repository.model.cc.wiki.Wiki"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class CommunicationChannel extends Object {

	protected List<Person> persons;
	protected String url;
	protected boolean nonProcessable;
	
	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
	public boolean getNonProcessable() {
		return nonProcessable;
	}
	
	public List<Person> getPersons() {
		return persons;
	}
}
