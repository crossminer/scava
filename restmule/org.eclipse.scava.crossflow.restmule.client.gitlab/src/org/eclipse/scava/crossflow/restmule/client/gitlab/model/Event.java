package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Event {

	public Event(){}

	@JsonProperty("note") 
	private Object note;
	
	@JsonProperty("data") 
	private String data;
	
	@JsonProperty("project_id") 
	private String projectId;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("action_name") 
	private String actionName;
	
	@JsonProperty("target_title") 
	private String targetTitle;
	
	@JsonProperty("target_type") 
	private String targetType;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("target_id") 
	private String targetId;
	
	@JsonProperty("author_id") 
	private String authorId;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("author_username") 
	private String authorUsername;
	
	public Object getNote() {
		return this.note;
	}
	
	public String getData() {
		return this.data;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getActionName() {
		return this.actionName;
	}
	
	public String getTargetTitle() {
		return this.targetTitle;
	}
	
	public String getTargetType() {
		return this.targetType;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getTargetId() {
		return this.targetId;
	}
	
	public String getAuthorId() {
		return this.authorId;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getAuthorUsername() {
		return this.authorUsername;
	}
	
	@Override
	public String toString() {
		return "Event [ "
			+ "note = " + this.note + ", "
			+ "data = " + this.data + ", "
			+ "projectId = " + this.projectId + ", "
			+ "author = " + this.author + ", "
			+ "actionName = " + this.actionName + ", "
			+ "targetTitle = " + this.targetTitle + ", "
			+ "targetType = " + this.targetType + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "targetId = " + this.targetId + ", "
			+ "authorId = " + this.authorId + ", "
			+ "title = " + this.title + ", "
			+ "authorUsername = " + this.authorUsername + ", "
			+ "]"; 
	}	
}	
