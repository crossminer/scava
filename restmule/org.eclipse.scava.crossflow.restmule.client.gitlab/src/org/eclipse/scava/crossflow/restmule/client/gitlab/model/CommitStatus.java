package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitStatus {

	public CommitStatus(){}

	@JsonProperty("allow_failure") 
	private String allowFailure;
	
	@JsonProperty("ref") 
	private String ref;
	
	@JsonProperty("finished_at") 
	private String finishedAt;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("target_url") 
	private String targetUrl;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("started_at") 
	private String startedAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("sha") 
	private String sha;
	
	@JsonProperty("status") 
	private String status;
	
	public String getAllowFailure() {
		return this.allowFailure;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public String getFinishedAt() {
		return this.finishedAt;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getTargetUrl() {
		return this.targetUrl;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getStartedAt() {
		return this.startedAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSha() {
		return this.sha;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	@Override
	public String toString() {
		return "CommitStatus [ "
			+ "allowFailure = " + this.allowFailure + ", "
			+ "ref = " + this.ref + ", "
			+ "finishedAt = " + this.finishedAt + ", "
			+ "author = " + this.author + ", "
			+ "targetUrl = " + this.targetUrl + ", "
			+ "name = " + this.name + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "description = " + this.description + ", "
			+ "startedAt = " + this.startedAt + ", "
			+ "id = " + this.id + ", "
			+ "sha = " + this.sha + ", "
			+ "status = " + this.status + ", "
			+ "]"; 
	}	
}	
