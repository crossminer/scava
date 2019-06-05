/*******************************************************************************
* Copyright (c) 2019 Edge Hill University
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.commits.document;

import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.delta.vcs.VcsCommit;

public class CommitDocument {

	private String project_name;
	private String uid;
	private String repository;
	private String revision;
	private String author;
	private String author_email;
	private String body;
	private Date created_at;
	//NLP
	private String plain_text;
	private List<String> commits_references;
	private List<String> bugs_references;
	
	public CommitDocument(String projectName, String uid, String repository, VcsCommit commit) {
		this.project_name = projectName;
		this.uid = uid;
		this.repository=repository;
		this.revision = commit.getRevision();
		this.author=commit.getAuthor();
		this.body=commit.getMessage();
		this.author_email=commit.getAuthorEmail();
		this.created_at=commit.getJavaDate();
	}

	public String getPlain_text() {
		return plain_text;
	}

	public void setPlain_text(String plain_text) {
		this.plain_text = plain_text;
	}

	public List<String> getCommits_references() {
		return commits_references;
	}

	public void setCommits_references(List<String> commits_references) {
		if(this.commits_references.size()==0)
			this.commits_references = commits_references;
		else
		{
			for(String commitReference: commits_references)
			{
				if(!this.commits_references.contains(commitReference))
					this.commits_references.add(commitReference);
			}
		}
	}

	public List<String> getBugs_references() {
		return bugs_references;
	}

	public void setBugs_references(List<String> bugs_references) {
		if(this.bugs_references.size()==0)
			this.bugs_references = bugs_references;
		else
		{
			for(String bugReference: bugs_references)
			{
				if(!this.bugs_references.contains(bugReference))
					this.bugs_references.add(bugReference);
			}
		}
	}

	public String getProject_name() {
		return project_name;
	}

	public String getUid() {
		return uid;
	}

	public String getRepository() {
		return repository;
	}

	public String getRevision() {
		return revision;
	}

	public String getBody() {
		return body;
	}

	public String getAuthor() {
		return author;
	}

	public String getAuthor_email() {
		return author_email;
	}

	public Date getCreated_at() {
		return created_at;
	}
	
	

}
