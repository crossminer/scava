/*******************************************************************************
 * Copyright Disclaimer
 * 
 * Contributors:
 * Daniel Campbell - Implementation.
 *******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.github.utils;

import java.util.Date;

import org.eclipse.scava.crossflow.restmule.client.github.model.Issues;
import org.eclipse.scava.crossflow.restmule.client.github.model.IssuesComments;
import org.eclipse.scava.crossflow.restmule.client.github.model.PullRequest;
import org.eclipse.scava.crossflow.restmule.client.github.model.PullsComment;
import org.eclipse.scava.platform.bugtrackingsystem.github.GitHubComment;
import org.eclipse.scava.platform.bugtrackingsystem.github.GitHubIssue;
import org.eclipse.scava.platform.bugtrackingsystem.github.GitHubPullRequest;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class GitHubReaderUtils {
/*
 * This Class provides numerous common utilities for the GitHub Reader 
 */
	
	/**
	 * Convert Issues object to a GitHubIssue object that is compatible with the BugTrackingSystem
	 * 
	 * @param issue
	 * @return gitHubIssue
	 * 
	 **/
	public static GitHubIssue convertToGitHubIssue(Issues issue, BugTrackingSystem ghbt)  {
		
		GitHubIssue gitHubIssue = new GitHubIssue();
		gitHubIssue.setCreator(issue.getUser().getLogin());
		gitHubIssue.setBugTrackingSystem(ghbt);
		gitHubIssue.setBugId(issue.getNumber().toString());
		gitHubIssue.setSummary(issue.getTitle());
		gitHubIssue.setCreationTime(convertStringToDate(issue.getCreatedAt()) );
		gitHubIssue.setClosedTime(issue);
		gitHubIssue.setUpdatedTime(issue);
		gitHubIssue.setNumComments(issue);
		gitHubIssue.addLabel(issue);
		//gitHubIssue.setMilestone(issue);
		gitHubIssue.setBody(issue);
		gitHubIssue.setAssignee(issue);
		gitHubIssue.setHtmlUrl(issue);
		gitHubIssue.setUrl(issue);

		return gitHubIssue;
	}
	
	/**
	 * Convert 'IssueComments' object to a GitHubComment object that is compatible with the BugTrackingSystem
	 * 
	 * @param IssueComments
	 * @param BugTrackingSystem
	 * @return gitHubComment
	 * 
	 **/
	
	public static GitHubComment convertToGitHubComment(IssuesComments comment, BugTrackingSystem ghbt, String issueId){
		GitHubComment gitHubComment = new GitHubComment();
		gitHubComment.setBugTrackingSystem(ghbt);
		gitHubComment.setCommentId(comment.getId().toString());
		gitHubComment.setCreator(comment.getUser().getLogin());
		gitHubComment.setCreationTime(convertStringToDate(comment.getCreatedAt().trim()));
		gitHubComment.setText(comment.getBody());
		gitHubComment.setBugId(issueId);
		gitHubComment.setUpdatedAt(convertStringToDate(comment.getUpdatedAt()));
		gitHubComment.setUrl(comment.getUrl());
		
		return gitHubComment;
	}
	
	
	/**
	 * Convert 'PullComment' object to a GitHubComment object that is compatible with the BugTrackingSystem
	 * 
	 * @param PullComment
	 * @param BugTrackingSystem
	 * @return gitHubComment
	 * 
	 **/
	
	public static GitHubComment convertToGitHubComment(PullsComment comment, BugTrackingSystem ghbt, Issues issue){
		GitHubComment gitHubComment = new GitHubComment();
		
		gitHubComment.setBugTrackingSystem(ghbt);
		gitHubComment.setCommentId(comment.getId().toString());
		gitHubComment.setCreator(comment.getUser().getId().toString());
		gitHubComment.setCreationTime(convertStringToDate(comment.getCreatedAt()));
		gitHubComment.setText(comment.getBody());
		gitHubComment.setBugId(issue.getNumber().toString());
		gitHubComment.setUpdatedAt(convertStringToDate(comment.getUpdatedAt()));
		gitHubComment.setUrl(comment.getUrl());
		
		return gitHubComment;
	}
	/**
	 * Convert 'Pulls' (PullRequest) object to a GitHubPullRequest object that is compatible with the BugTrackingSystem
	 * 
	 * @param issue
	 * @return gitHubPullRequest
	 * 
	 **/
	public static  GitHubPullRequest convertToGitHubPullRequest(PullRequest pullRequest, BugTrackingSystem ghbt, Date day){
		//TODO will also need to handle pullrequest head
		GitHubPullRequest gitHubPullRequest = new GitHubPullRequest();
		gitHubPullRequest.setId(pullRequest);
		gitHubPullRequest.setMergeable(pullRequest);
		gitHubPullRequest.setCreatedAt(pullRequest);
		gitHubPullRequest.setMerged(pullRequest);
		gitHubPullRequest.setClosedAt(pullRequest);
		gitHubPullRequest.setMergedAt(pullRequest);
		gitHubPullRequest.setUpdatedAt(pullRequest);
		gitHubPullRequest.setAdditions(pullRequest);
		gitHubPullRequest.setChangedFiles(pullRequest);
		gitHubPullRequest.setComments(pullRequest);
		gitHubPullRequest.setCommits(pullRequest);
		gitHubPullRequest.setDeletions(pullRequest);
		gitHubPullRequest.setNumber(pullRequest);
		gitHubPullRequest.setMilestone(pullRequest);
		//gitHubPullRequest.setBase(pullRequest.getBase());
		//gitHubPullRequest.setHead(pullRequest.getHead());
		gitHubPullRequest.setBody(pullRequest);
		gitHubPullRequest.setDiffUrl(pullRequest);
		gitHubPullRequest.setHtmlUrl(pullRequest);
		gitHubPullRequest.setIssueUrl(pullRequest);
		gitHubPullRequest.setPatchUrl(pullRequest);
		gitHubPullRequest.setState(pullRequest);
		gitHubPullRequest.setTitle(pullRequest);
		gitHubPullRequest.setUrl(pullRequest);
		gitHubPullRequest.setMergedBy(pullRequest);
		gitHubPullRequest.setUser(pullRequest);

		return gitHubPullRequest;
	}
	
	/**
	 * Converts ISO 8601 Timestamp from String to Java.util.Date
	 * 
	 * @param isoDate ISO 8601 in string format. For example '2014-11-07T22:01:45Z' (this has the time zone omitted hence 'Z')
	 * @return date Java.util.Date
	 * @throws Exception
	 */
	public static Date convertStringToDate(String isoDate){
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}

}
