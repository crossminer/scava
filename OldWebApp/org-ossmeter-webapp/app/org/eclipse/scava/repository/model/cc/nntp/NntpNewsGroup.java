package org.eclipse.scava.repository.model.cc.nntp;

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
	@Type(value = NntpNewsGroup.class, name="org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup"), 	@Type(value = org.eclipse.scava.repository.model.eclipse.EclipseNewsGroup.class, name="org.eclipse.scava.repository.model.eclipse.EclipseNewsGroup"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class NntpNewsGroup extends CommunicationChannel {

	protected boolean authenticationRequired;
	protected String username;
	protected String password;
	protected int port;
	protected String description;
	protected String name;
	protected int interval;
	protected String lastArticleChecked;
	
	public boolean getAuthenticationRequired() {
		return authenticationRequired;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getPort() {
		return port;
	}
	public String getDescription() {
		return description;
	}
	public String getName() {
		return name;
	}
	public int getInterval() {
		return interval;
	}
	public String getLastArticleChecked() {
		return lastArticleChecked;
	}

	public void setName(String name){
		this.name = name;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
