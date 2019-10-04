package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MergeRequest {

	public MergeRequest(){}

	@JsonProperty("upvotes") 
	private String upvotes;
	
	@JsonProperty("iid") 
	private String iid;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("source_branch") 
	private String sourceBranch;
	
	@JsonProperty("subscribed") 
	private String subscribed;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("project_id") 
	private String projectId;
	
	@JsonProperty("merge_commit_sha") 
	private String mergeCommitSha;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("work_in_progress") 
	private String workInProgress;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("target_branch") 
	private String targetBranch;
	
	@JsonProperty("source_project_id") 
	private String sourceProjectId;
	
	@JsonProperty("downvotes") 
	private String downvotes;
	
	@JsonProperty("should_remove_source_branch") 
	private String shouldRemoveSourceBranch;
	
	@JsonProperty("sha") 
	private String sha;
	
	@JsonProperty("labels") 
	private String labels;
	
	@JsonProperty("milestone") 
	private Object milestone;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("merge_status") 
	private String mergeStatus;
	
	@JsonProperty("merge_when_build_succeeds") 
	private String mergeWhenBuildSucceeds;
	
	@JsonProperty("user_notes_count") 
	private String userNotesCount;
	
	@JsonProperty("assignee") 
	private Object assignee;
	
	@JsonProperty("target_project_id") 
	private String targetProjectId;
	
	@JsonProperty("force_remove_source_branch") 
	private String forceRemoveSourceBranch;
	
	public String getUpvotes() {
		return this.upvotes;
	}
	
	public String getIid() {
		return this.iid;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getSourceBranch() {
		return this.sourceBranch;
	}
	
	public String getSubscribed() {
		return this.subscribed;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	
	public String getMergeCommitSha() {
		return this.mergeCommitSha;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getWorkInProgress() {
		return this.workInProgress;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getTargetBranch() {
		return this.targetBranch;
	}
	
	public String getSourceProjectId() {
		return this.sourceProjectId;
	}
	
	public String getDownvotes() {
		return this.downvotes;
	}
	
	public String getShouldRemoveSourceBranch() {
		return this.shouldRemoveSourceBranch;
	}
	
	public String getSha() {
		return this.sha;
	}
	
	public String getLabels() {
		return this.labels;
	}
	
	public Object getMilestone() {
		return this.milestone;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getMergeStatus() {
		return this.mergeStatus;
	}
	
	public String getMergeWhenBuildSucceeds() {
		return this.mergeWhenBuildSucceeds;
	}
	
	public String getUserNotesCount() {
		return this.userNotesCount;
	}
	
	public Object getAssignee() {
		return this.assignee;
	}
	
	public String getTargetProjectId() {
		return this.targetProjectId;
	}
	
	public String getForceRemoveSourceBranch() {
		return this.forceRemoveSourceBranch;
	}
	
	@Override
	public String toString() {
		return "MergeRequest [ "
			+ "upvotes = " + this.upvotes + ", "
			+ "iid = " + this.iid + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "description = " + this.description + ", "
			+ "title = " + this.title + ", "
			+ "sourceBranch = " + this.sourceBranch + ", "
			+ "subscribed = " + this.subscribed + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "projectId = " + this.projectId + ", "
			+ "mergeCommitSha = " + this.mergeCommitSha + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "workInProgress = " + this.workInProgress + ", "
			+ "author = " + this.author + ", "
			+ "targetBranch = " + this.targetBranch + ", "
			+ "sourceProjectId = " + this.sourceProjectId + ", "
			+ "downvotes = " + this.downvotes + ", "
			+ "shouldRemoveSourceBranch = " + this.shouldRemoveSourceBranch + ", "
			+ "sha = " + this.sha + ", "
			+ "labels = " + this.labels + ", "
			+ "milestone = " + this.milestone + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "mergeStatus = " + this.mergeStatus + ", "
			+ "mergeWhenBuildSucceeds = " + this.mergeWhenBuildSucceeds + ", "
			+ "userNotesCount = " + this.userNotesCount + ", "
			+ "assignee = " + this.assignee + ", "
			+ "targetProjectId = " + this.targetProjectId + ", "
			+ "forceRemoveSourceBranch = " + this.forceRemoveSourceBranch + ", "
			+ "]"; 
	}	
}	
