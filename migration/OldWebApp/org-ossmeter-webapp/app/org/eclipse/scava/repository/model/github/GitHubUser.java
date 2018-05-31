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
	@Type(value = GitHubUser.class, name="org.eclipse.scava.repository.model.github.GitHubUser"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubUser extends Person {

	protected String login;
	protected String html_url;
	protected String url;
	protected String followers_url;
	
	public String getLogin() {
		return login;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getUrl() {
		return url;
	}
	public String getFollowers_url() {
		return followers_url;
	}
	
}
