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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.platform.bugtrackingsystem.github.utils.GitHubReaderUtils;
import org.eclipse.scava.workflow.restmule.generated.client.github.model.PullRequest;

public class GitHubPullRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean mergeable;
    private boolean merged;
    
    private Date closedAt;
    private Date mergedAt;
    private Date updatedAt;
    private Date createdAt;
 
    private int additions;
    private int changedFiles;
    private int comments;
    private int commits;
    private int deletions;
    private int number;
  
    private Integer milestone;
    
    private GitHubPullRequestMarker base;
    private GitHubPullRequestMarker head;

    private String id;
    private String body;
    private String diffUrl;
    private String htmlUrl;
    private String issueUrl;
    private String patchUrl;
    private String state;
    private String title;
    private String url;
    private String assignee;
    private String mergedBy;
    private String user;
    
    private List<GitHubComment> pullRequestComments  = new ArrayList<>();

	// ----------------------------------------------------------------------------------
	// Setters
	// ----------------------------------------------------------------------------------
	public void setMergeable(PullRequest pullRequest) {
		try{

			this.mergeable = pullRequest.getMergeable();
			
		}catch(NullPointerException np){
			
			this.mergeable = false;
			
		}
        
    }
    
    public void setMerged(PullRequest pullRequest) {
        this.merged = pullRequest.getMerged();
    }
    
    public void setClosedAt(PullRequest pullRequest) {
    	
    	try{
    		Date  date = GitHubReaderUtils.convertStringToDate(pullRequest.getClosedAt().toString().trim());
            this.closedAt = date;
    	}catch(NullPointerException np){
    		this.closedAt = null;
    		
    	}
    	
    }
	
    public void setMergedAt(PullRequest pullRequest) {
    	
       	try{
    		Date  date = GitHubReaderUtils.convertStringToDate(pullRequest.getMergedAt().toString().trim());
            this.mergedAt = date;
    	}catch(NullPointerException np){
    		this.mergedAt = null;
    	}
       
    }

    public void setUpdatedAt(PullRequest pullRequest) {
      
       	try{
    		Date  date = GitHubReaderUtils.convertStringToDate(pullRequest.getUpdatedAt().trim());
            this.updatedAt = date;
    	}catch(NullPointerException np){
    		this.updatedAt = null;
    	}
    }
    
    public void setCreatedAt(PullRequest pullRequest) {
      	try{
    		Date  date = GitHubReaderUtils.convertStringToDate(pullRequest.getCreatedAt().trim());
            this.createdAt = date;
    	}catch(NullPointerException np){
    		this.createdAt = null;
    	}
    }

    public void setAdditions(PullRequest pullRequest){
    	
        this.additions = pullRequest.getAdditions();;
    }

    public void setChangedFiles(PullRequest pullRequest) {

        this.changedFiles = pullRequest.getChangedFiles();
    }
    
    public void setId(PullRequest pullRequest) {
    	
    	try{
    		
    		this.id = Long.toUnsignedString(pullRequest.getHead().getRepo().getId().longValue());
    	}catch(NullPointerException np){
    		
    		
    	}
        
    }

    public void setComments(PullRequest pullRequest) {
    	
        this.comments = pullRequest.getComments();;
    }
    
    public void setCommits(PullRequest pullRequest) {
    	
    	
        this.commits = pullRequest.getCommits();
    }
    
    public void setDeletions(PullRequest pullRequest) {
        this.deletions = pullRequest.getDeletions();
    }
    
    public void setNumber(PullRequest pullRequest) {
        this.number = pullRequest.getNumber();
    }
    
    public void setBody(PullRequest pullRequest) {
        this.body = pullRequest.getBody().trim();
    }
    
    
//    public void setBodyHtml(PullRequest pullRequest) {
//        this.bodyHtml = pullRequest.get;
//    }
    
//    public void setBodyText(PullRequest pullRequest) {
//        this.bodyText = pullRequest.h;
//    }
    
    public void setDiffUrl(PullRequest pullRequest) {
        this.diffUrl = pullRequest.getDiffUrl().trim();
    }
    
    public void setHtmlUrl(PullRequest pullRequest) {
        this.htmlUrl = pullRequest.getHtmlUrl().trim();
    }
    
    public void setIssueUrl(PullRequest pullRequest) {
        this.issueUrl = pullRequest.getIssueUrl().trim();
    }
     
    public void setPatchUrl(PullRequest pullRequest) {
        this.patchUrl = pullRequest.getPatchUrl().trim();
    }
    
    public void setState(PullRequest pullRequest) {
        this.state = pullRequest.getState().trim();
    }
    
    public void setTitle(PullRequest pullRequest) {
        this.title = pullRequest.getTitle().trim();
    }
    
    public void setUrl(PullRequest pullRequest) {
        this.url = pullRequest.getUrl().trim();
    }

    //TODO: cannot option Assig
//    public void setAssignee(PullRequest pullRequest) {	
//        this.assignee = pullRequest.get;
//    }
    
    public void setMergedBy(PullRequest pullRequest) {
    	try{
    		this.mergedBy = pullRequest.getMergedBy().toString().trim();
    	}catch(NullPointerException np){
    		
    		this.mergedBy = null;
    	}
        
    }
    //potential error
    public void setUser(PullRequest pullRequest) {
        this.user = pullRequest.getUser().toString();
    }
    
    //need another call of this
    public void setMilestone(PullRequest pullRequest) {
    	
        this.milestone = null;
    }
    
    
    //these need further modifications
    public void setBase(GitHubPullRequestMarker base) {
        this.base = base;
    }

    public void setHead(GitHubPullRequestMarker head) {
        this.head = head;
    }

    
	// ----------------------------------------------------------------------------------
	// Getters
	// ----------------------------------------------------------------------------------
    
    public boolean isMergeable() {
        return mergeable;
    }

    public boolean isMerged() {
        return merged;
    }

    public Date getClosedAt() {
        return closedAt;
    }

    public Date getMergedAt() {
        return mergedAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }

    public int getAdditions() {
        return additions;
    }

    public int getChangedFiles() {
        return changedFiles;
    }

    public int getComments() {
        return comments;
    }

    public int getCommits() {
        return commits;
    }

    public int getDeletions() {
        return deletions;
    }

    public int getNumber() {
        return number;
    }

    public String getBody() {
        return body;
    }

//    public String getBodyHtml() {
//        return bodyHtml;
//    }
//
//    public String getBodyText() {
//        return bodyText;
//    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getIssueUrl() {
        return issueUrl;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getAssignee() {
        return assignee;
    }

    public String getMergedBy() {
        return mergedBy;
    }

    public String getUser() {
        return user;
    }

    public Integer getMilestone() {
        return milestone;
    }

    public GitHubPullRequestMarker getBase() {
        return base;
    }

    public GitHubPullRequestMarker getHead() {
        return head;
    }

	/**
	 * @return the pullRequestComments
	 */
	public List<GitHubComment> getPullRequestComments() {
		return pullRequestComments;
	}

	/**
	 * @param pullRequestComments the pullRequestComments to set
	 */
	public void addPullRequestComment(GitHubComment pullRequestComment) {
		this.pullRequestComments.add(pullRequestComment);
	}

}
