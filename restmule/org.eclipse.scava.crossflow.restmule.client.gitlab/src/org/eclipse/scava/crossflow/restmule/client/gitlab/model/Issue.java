package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

	public Issue(){}

	@JsonProperty("upvotes") 
	private String upvotes;
	
	@JsonProperty("iid") 
	private String iid;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("due_date") 
	private String dueDate;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("downvotes") 
	private String downvotes;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("labels") 
	private String labels;
	
	@JsonProperty("subscribed") 
	private String subscribed;
	
	@JsonProperty("milestone") 
	private Object milestone;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("project_id") 
	private String projectId;
	
	@JsonProperty("user_notes_count") 
	private String userNotesCount;
	
	@JsonProperty("assignee") 
	private Object assignee;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("confidential") 
	private String confidential;
	
	public String getUpvotes() {
		return this.upvotes;
	}
	
	public String getIid() {
		return this.iid;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getDueDate() {
		return this.dueDate;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getDownvotes() {
		return this.downvotes;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getLabels() {
		return this.labels;
	}
	
	public String getSubscribed() {
		return this.subscribed;
	}
	
	public Object getMilestone() {
		return this.milestone;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	
	public String getUserNotesCount() {
		return this.userNotesCount;
	}
	
	public Object getAssignee() {
		return this.assignee;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getConfidential() {
		return this.confidential;
	}
	
	@Override
	public String toString() {
		return "Issue [ "
			+ "upvotes = " + this.upvotes + ", "
			+ "iid = " + this.iid + ", "
			+ "author = " + this.author + ", "
			+ "dueDate = " + this.dueDate + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "description = " + this.description + ", "
			+ "downvotes = " + this.downvotes + ", "
			+ "title = " + this.title + ", "
			+ "labels = " + this.labels + ", "
			+ "subscribed = " + this.subscribed + ", "
			+ "milestone = " + this.milestone + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "projectId = " + this.projectId + ", "
			+ "userNotesCount = " + this.userNotesCount + ", "
			+ "assignee = " + this.assignee + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "confidential = " + this.confidential + ", "
			+ "]"; 
	}	
}	
