package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoCommitDetail {

	public RepoCommitDetail(){}

	@JsonProperty("author_name") 
	private String authorName;
	
	@JsonProperty("authored_date") 
	private String authoredDate;
	
	@JsonProperty("committer_email") 
	private String committerEmail;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("short_id") 
	private String shortId;
	
	@JsonProperty("message") 
	private String message;
	
	@JsonProperty("parent_ids") 
	private String parentIds;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("committer_name") 
	private String committerName;
	
	@JsonProperty("committed_date") 
	private String committedDate;
	
	@JsonProperty("stats") 
	private Object stats;
	
	@JsonProperty("author_email") 
	private String authorEmail;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("status") 
	private String status;
	
	public String getAuthorName() {
		return this.authorName;
	}
	
	public String getAuthoredDate() {
		return this.authoredDate;
	}
	
	public String getCommitterEmail() {
		return this.committerEmail;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getShortId() {
		return this.shortId;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	public String getParentIds() {
		return this.parentIds;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getCommitterName() {
		return this.committerName;
	}
	
	public String getCommittedDate() {
		return this.committedDate;
	}
	
	public Object getStats() {
		return this.stats;
	}
	
	public String getAuthorEmail() {
		return this.authorEmail;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	@Override
	public String toString() {
		return "RepoCommitDetail [ "
			+ "authorName = " + this.authorName + ", "
			+ "authoredDate = " + this.authoredDate + ", "
			+ "committerEmail = " + this.committerEmail + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "shortId = " + this.shortId + ", "
			+ "message = " + this.message + ", "
			+ "parentIds = " + this.parentIds + ", "
			+ "title = " + this.title + ", "
			+ "committerName = " + this.committerName + ", "
			+ "committedDate = " + this.committedDate + ", "
			+ "stats = " + this.stats + ", "
			+ "authorEmail = " + this.authorEmail + ", "
			+ "id = " + this.id + ", "
			+ "status = " + this.status + ", "
			+ "]"; 
	}	
}	
