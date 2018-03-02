/*******************************************************************************
 * Copyright (C) 2017 University of L'Aquila
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.business.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Juri Di Rocco
 *
 */
@Document
public class Artifact {

	private boolean hasPomFiles;
	@Id
	private String id;
	private List<Tag> tags;
	
	@TextIndexed(weight=4)
	String name;
	@TextIndexed(weight=3)
	String shortName;
	@TextIndexed(weight=1)
	String description;
	@TextIndexed(weight=4)
	String fullName;
	
	private int year;
	private boolean active = true;
	private String homePage;
	@DBRef
	private List<Artifact> subArtifacts;
	private ArtifactType type;
	private Boolean private_;
	private Boolean fork;
	private String html_url;
	private String clone_url;
	private String git_url;
	private String ssh_url;
	private String svn_url;
	private String mirror_url;
	private long size;
	private String master_branch;
	@JsonIgnore
	private List<GithubUser> committeers = new ArrayList<>();
	private String readmeText;
	//@JsonIgnore
	private List<String> dependencies = new ArrayList<>(); 
	
	private List<Stargazers> starred = new ArrayList<>();
	public String getFullName() {
		return fullName;
	}
	

	public void setFullName(String full_name) {
		this.fullName = full_name;
	}

	public Boolean getPrivate_() {
		return private_;
	}

	public void setPrivate_(Boolean private_) {
		this.private_ = private_;
	}

	public Boolean getFork() {
		return fork;
	}

	public void setFork(Boolean fork) {
		this.fork = fork;
	}

	public String getHtml_url() {
		return html_url;
	}

	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}

	public String getClone_url() {
		return clone_url;
	}

	public void setClone_url(String clone_url) {
		this.clone_url = clone_url;
	}

	public String getGit_url() {
		return git_url;
	}

	public void setGit_url(String git_url) {
		this.git_url = git_url;
	}

	public String getSsh_url() {
		return ssh_url;
	}

	public void setSsh_url(String ssh_url) {
		this.ssh_url = ssh_url;
	}

	public String getSvn_url() {
		return svn_url;
	}

	public void setSvn_url(String svn_url) {
		this.svn_url = svn_url;
	}

	public String getMirror_url() {
		return mirror_url;
	}

	public void setMirror_url(String mirror_url) {
		this.mirror_url = mirror_url;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getMaster_branch() {
		return master_branch;
	}

	public void setMaster_branch(String master_branch) {
		this.master_branch = master_branch;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public List<Artifact> getParent() {
		return subArtifacts;
	}

	public void setParent(List<Artifact> subArtifacts) {
		this.subArtifacts = subArtifacts;
	}

	public String getReadmeText() {
		return readmeText;
	}

	public void setReadmeText(String readmeText) {
		this.readmeText = readmeText;
	}

	public List<String> getDependencies() {
		return dependencies;
	}

	public void setDependencies(List<String> dependencies) {
		this.dependencies = dependencies;
	}

	public ArtifactType getType() {
		return type;
	}

	public void setType(ArtifactType type) {
		this.type = type;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public void setStarred(List<Stargazers> users) {
		starred = users;
		
	}
	public List<Stargazers> getStarred(){
		return starred;
	}


	public boolean HasPomFiles() {
		return hasPomFiles;
	}


	public void setHasPomFiles(boolean hasPomFiles) {
		this.hasPomFiles = hasPomFiles;
	}


	public List<GithubUser> getCommitteers() {
		return committeers;
	}


	public void setCommitteers(List<GithubUser> committeers) {
		this.committeers = committeers;
	}
	
	
	


//	VcsRepository[*] vcsRepositories;
//	CommunicationChannel[*] communicationChannels;
//	BugTrackingSystem[*] bugTrackingSystems;
//	Person[*] persons;
//	License[*] licenses;
//	MetricProvider[*] metricProviderData;
//	ProjectExecutionInformation executionInformation;
//	Company[*] companies;
}
