/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.bugtrackingsystem.github.utils.GitHubReaderUtils;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.Issues;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;

public class GitHubIssue extends BugTrackingSystemBug {

	private static final long serialVersionUID = 1L;

	
	private Date closedTime;
	private Date updatedTime;
	private int numComments;
	private List<Issues.Labels> labels = new ArrayList<Issues.Labels>();
	private Integer milestone;
	private String body;
	private String url;
	private String htmlUrl;
	private String assignee;
	private List<GitHubComment> githubComments = new ArrayList<>();

	
	public GitHubIssue() {
	}

	// ----------------------------------------------------------------------------------
	// Setters
	// ----------------------------------------------------------------------------------
	public GitHubIssue(Issues issue) {
		long id = Long.valueOf(issue.getNumber());
		setBugId(Long.toString(id));
	}



	public void setClosedTime(Issues issue) {
		
		try{
			
			this.closedTime = GitHubReaderUtils.convertStringToDate(issue.getClosedAt().trim());
			
		}catch(NullPointerException np){
			
			this.closedTime = null;
		}
		
	}

	public void setUpdatedTime(Issues issue) {
		try{
				this.updatedTime = GitHubReaderUtils.convertStringToDate(issue.getUpdatedAt().trim());
				
			}catch(NullPointerException np){
				
				this.updatedTime = null;
			}
	}

	public void setNumComments(Issues issue) {
		
		int IssueNumComments = issue.getComments();
		this.numComments = IssueNumComments;
	}

	public void addLabel(Issues issue) {
		issue.getLabels().forEach(e ->labels.add(e));
	
	}

	public void setMilestone(Issues issue) {
		
		try{
		
			int issueMilestone = Integer.parseInt(issue.getMilestone().toString().trim());
			this.milestone = issueMilestone;
		}catch (NullPointerException np){
			
			this.milestone = null;
			
		}
	
	}

	public void setBody(Issues issue) {
		String issueBody = issue.getBody().trim();
		this.body = issueBody;
	}

	public void setAssignee(Issues issue){
		
		try{
			String issueAssignee = issue.getAssignee().toString().trim();
			this.assignee = issueAssignee;
		}catch (NullPointerException np){
			this.assignee = null;
		}

	}

	public void setHtmlUrl(Issues issue) {
		String issueHtmlUrl = issue.getHtmlUrl().trim();
		this.htmlUrl = issueHtmlUrl;
	}

	public void setUrl(Issues issue) {
		String issueUrl = issue.getUrl().trim();
		this.url = issueUrl;
	}

	// ----------------------------------------------------------------------------------
	// Getters
	// ----------------------------------------------------------------------------------


	public Date getClosedTime() {
		return closedTime;
	}

	public Date getUpdatedTime() {

		return updatedTime;
	}

	public int getNumComments() {
		return numComments;
	}

	public List<Issues.Labels> getLabels() {
		return labels;
	}

	public Integer getMilestone() {
		return milestone;
	}

	public String getBody() {
		return body;
	}

	public String getUrl() {
		return url;
	}

	public String getHtmlUrl() {
		return htmlUrl;
	}

	public String getAssignee() {
		return assignee;
	}

	/**
	 * @return the githubComments
	 */
	public List<GitHubComment> getGithubComments() {
		return githubComments;
	}

	/**
	 * @param githubComment
	 */
	public void addGithubComment(GitHubComment githubComment) {
		this.githubComments.add(githubComment);
	}


	

}
