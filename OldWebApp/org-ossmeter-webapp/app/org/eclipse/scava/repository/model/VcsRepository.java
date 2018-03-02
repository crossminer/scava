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
	@Type(value = VcsRepository.class, name="org.eclipse.scava.repository.model.VcsRepository"), 	@Type(value = org.eclipse.scava.repository.model.vcs.cvs.CvsRepository.class, name="org.eclipse.scava.repository.model.vcs.cvs.CvsRepository"),
	@Type(value = org.eclipse.scava.repository.model.vcs.git.GitRepository.class, name="org.eclipse.scava.repository.model.vcs.git.GitRepository"),
	@Type(value = org.eclipse.scava.repository.model.vcs.svn.SvnRepository.class, name="org.eclipse.scava.repository.model.vcs.svn.SvnRepository"),
})
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class VcsRepository extends NamedElement {

	protected List<Person> persons;
	protected String created_at;
	protected String updated_at;
	protected String url;
	
	public String getCreated_at() {
		return created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public String getUrl() {
		return url;
	}
	
	public List<Person> getPersons() {
		return persons;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
