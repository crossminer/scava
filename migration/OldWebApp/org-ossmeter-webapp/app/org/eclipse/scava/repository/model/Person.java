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
	@Type(value = Person.class, name="org.eclipse.scava.repository.model.Person"), 	@Type(value = org.eclipse.scava.repository.model.github.GitHubUser.class, name="org.eclipse.scava.repository.model.github.GitHubUser"),
	@Type(value = org.eclipse.scava.repository.model.googlecode.GoogleUser.class, name="org.eclipse.scava.repository.model.googlecode.GoogleUser"),
	@Type(value = org.eclipse.scava.repository.model.redmine.RedmineUser.class, name="org.eclipse.scava.repository.model.redmine.RedmineUser"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person extends NamedElement {

	protected List<Role> roles;
	protected String homePage;
	
	public String getHomePage() {
		return homePage;
	}
	
	public List<Role> getRoles() {
		return roles;
	}
}
