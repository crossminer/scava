/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket.api;

import java.io.Serializable;
import java.util.Date;

public class BitbucketPullRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private BitbucketLinks links;
	private String state;
	private String description;
	private String title;
	private Boolean closeSourceBranch;
	private String reason;
	private BitbucketPullRequestRepository destination;
	private BitbucketPullRequestRepository source;
	private BitbucketCommitSummary mergeCommit;
	private Date createdOn;
	private Date updatedOn;
	private String author;
	private String closed_by;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BitbucketPullRequestRepository getDestination() {
		return destination;
	}

	public void setDestination(BitbucketPullRequestRepository destination) {
		this.destination = destination;
	}

	public BitbucketPullRequestRepository getSource() {
		return source;
	}

	public void setSource(BitbucketPullRequestRepository source) {
		this.source = source;
	}

	public BitbucketCommitSummary getMergeCommit() {
		return mergeCommit;
	}

	public void setMergeCommit(BitbucketCommitSummary mergeCommit) {
		this.mergeCommit = mergeCommit;
	}

	public BitbucketLinks getLinks() {
		return links;
	}

	public void setLinks(BitbucketLinks links) {
		this.links = links;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getCloseSourceBranch() {
		return closeSourceBranch;
	}

	public void setCloseSourceBranch(Boolean closeSourceBranch) {
		this.closeSourceBranch = closeSourceBranch;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getClosed_by() {
		return closed_by;
	}

	public void setClosed_by(String closed_by) {
		this.closed_by = closed_by;
	}
}
