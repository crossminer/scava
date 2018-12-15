package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoCommit {

	public RepoCommit(){}

	@JsonProperty("author_name") 
	private String authorName;
	
	@JsonProperty("committer_email") 
	private String committerEmail;
	
	@JsonProperty("author_email") 
	private String authorEmail;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("short_id") 
	private String shortId;
	
	@JsonProperty("message") 
	private String message;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("committer_name") 
	private String committerName;
	
	public String getAuthorName() {
		return this.authorName;
	}
	
	public String getCommitterEmail() {
		return this.committerEmail;
	}
	
	public String getAuthorEmail() {
		return this.authorEmail;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getShortId() {
		return this.shortId;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getCommitterName() {
		return this.committerName;
	}
	
	@Override
	public String toString() {
		return "RepoCommit [ "
			+ "authorName = " + this.authorName + ", "
			+ "committerEmail = " + this.committerEmail + ", "
			+ "authorEmail = " + this.authorEmail + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "shortId = " + this.shortId + ", "
			+ "message = " + this.message + ", "
			+ "title = " + this.title + ", "
			+ "committerName = " + this.committerName + ", "
			+ "]"; 
	}	
}	
