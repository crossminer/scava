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

import java.util.Date;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;


public class BitbucketIssue extends BugTrackingSystemBug {

	private static final long serialVersionUID = 1L;

	private String title;
	private String responsible;
	private Metadata metadata;
	private String content;
	private boolean isSpam;
	private int commentCount;
	private int followerCount;
	private String resourceUri;
	private Date updateDate;

	@Override
	public Date getCreationTime() {
		return super.getCreationTime();
	}

	// private String status;
	// private String priority;
	// private BitbucketUser reported_by;
	// private int local_id; //bugId
	// private String utc_created_on; // TODO make DateTime
	// private Date utc_last_updated;// TODO make DateTime
	// private String created_on; // TODO make DateTime

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSpam() {
		return isSpam;
	}

	public void setSpam(boolean isSpam) {
		this.isSpam = isSpam;
	}

	public String getResponsible() {
		return responsible;
	}

	public void setResponsible(String responsible) {
		this.responsible = responsible;
	}

	public Metadata getMetadata() {
		return metadata;
	}

	public void setMetadata(Metadata metadata) {
		this.metadata = metadata;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public String getResourceUri() {
		return resourceUri;
	}

	public void setResourceUri(String resourceUri) {
		this.resourceUri = resourceUri;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Override
	public String toString() {
		return getBugId() + ": " + getTitle() + " [Priority: " + getPriority()
				+ "] [Status: " + getStatus() + "]";
	}

	public static class Metadata {
		private String kind;
		private String version;
		private String component;
		private String milestone;

		public Metadata() {
		}

		public String getKind() {
			return kind;
		}

		public void setKind(String kind) {
			this.kind = kind;
		}

		public String getVersion() {
			return version;
		}

		public void setVersion(String version) {
			this.version = version;
		}

		public String getComponent() {
			return component;
		}

		public void setComponent(String component) {
			this.component = component;
		}

		public String getMilestone() {
			return milestone;
		}

		public void setMilestone(String milestone) {
			this.milestone = milestone;
		}
	}

}
