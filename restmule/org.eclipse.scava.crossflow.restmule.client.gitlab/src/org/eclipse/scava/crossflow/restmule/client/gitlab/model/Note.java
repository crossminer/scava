package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Note {

	public Note(){}

	@JsonProperty("noteable_id") 
	private String noteableId;
	
	@JsonProperty("upvote?") 
	private String upvote;
	
	@JsonProperty("system") 
	private String system;
	
	@JsonProperty("attachment") 
	private String attachment;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("noteable_type") 
	private String noteableType;
	
	@JsonProperty("downvote?") 
	private String downvote;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("body") 
	private String body;
	
	public String getNoteableId() {
		return this.noteableId;
	}
	
	public String getUpvote() {
		return this.upvote;
	}
	
	public String getSystem() {
		return this.system;
	}
	
	public String getAttachment() {
		return this.attachment;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getNoteableType() {
		return this.noteableType;
	}
	
	public String getDownvote() {
		return this.downvote;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getBody() {
		return this.body;
	}
	
	@Override
	public String toString() {
		return "Note [ "
			+ "noteableId = " + this.noteableId + ", "
			+ "upvote = " + this.upvote + ", "
			+ "system = " + this.system + ", "
			+ "attachment = " + this.attachment + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "author = " + this.author + ", "
			+ "noteableType = " + this.noteableType + ", "
			+ "downvote = " + this.downvote + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "body = " + this.body + ", "
			+ "]"; 
	}	
}	
