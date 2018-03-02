package org.eclipse.scava.repository.model.bts.bugzilla;

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
	@Type(value = Bugzilla.class, name="org.eclipse.scava.repository.model.bts.bugzilla.Bugzilla"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bugzilla extends BugTrackingSystem {

	protected String username;
	protected String password;
	protected String product;
	protected String component;
	protected String cgiQueryProgram;
	
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getProduct() {
		return product;
	}
	public String getComponent() {
		return component;
	}
	public String getCgiQueryProgram() {
		return cgiQueryProgram;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
