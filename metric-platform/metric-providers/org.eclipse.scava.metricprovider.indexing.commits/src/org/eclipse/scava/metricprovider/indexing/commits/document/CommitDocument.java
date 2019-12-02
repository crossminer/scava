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

import java.util.ArrayList;
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
	private ReferringTo referring_to;
	
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

	public void addBugReference(String bugReference)
	{
		if(referring_to==null)
			referring_to=new ReferringTo();
		referring_to.addBug(bugReference);
	}
	
	public void addCommitReference(String commitReference)
	{
		if(referring_to==null)
			referring_to=new ReferringTo();
		referring_to.addCommit(commitReference);
	}
	
	public ReferringTo getReferring_to() {
		return referring_to;
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
	
	public class ReferringTo
	{
		List<String> commits;
		List<String> bugs;
		
		public void addCommit(String commitReference)
		{
			if(commits==null)
				commits=new ArrayList<String>();
			commits.add(commitReference);
		}
		
		public void addBug(String bugReference)
		{
			if(bugs==null)
				bugs=new ArrayList<String>();
			bugs.add(bugReference);
		}
		
		public List<String> getBugs() {
			return bugs;
		}
		
		public List<String> getCommits() {
			return commits;
		}
	}

}
