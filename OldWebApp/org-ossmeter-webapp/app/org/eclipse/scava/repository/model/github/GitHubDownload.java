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
	@Type(value = GitHubDownload.class, name="org.eclipse.scava.repository.model.github.GitHubDownload"), })
@JsonIgnoreProperties(ignoreUnknown = true)
public class GitHubDownload extends Object {

	protected int _id;
	protected String url;
	protected String html_url;
	protected String name;
	protected String description;
	protected int size;
	protected int download_count;
	protected String content_type;
	
	public int get_id() {
		return _id;
	}
	public String getUrl() {
		return url;
	}
	public String getHtml_url() {
		return html_url;
	}
	public String getName() {
		return name;
	}
	public String getDescription() {
		return description;
	}
	public int getSize() {
		return size;
	}
	public int getDownload_count() {
		return download_count;
	}
	public String getContent_type() {
		return content_type;
	}
	
}
