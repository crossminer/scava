package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Pipeline {

	public Pipeline(){}

	@JsonProperty("coverage") 
	private String coverage;
	
	@JsonProperty("before_sha") 
	private String beforeSha;
	
	@JsonProperty("finished_at") 
	private String finishedAt;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("sha") 
	private String sha;
	
	@JsonProperty("duration") 
	private String duration;
	
	@JsonProperty("yaml_errors") 
	private String yamlErrors;
	
	@JsonProperty("ref") 
	private String ref;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("committed_at") 
	private String committedAt;
	
	@JsonProperty("started_at") 
	private String startedAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("tag") 
	private String tag;
	
	@JsonProperty("user") 
	private Object user;
	
	@JsonProperty("status") 
	private String status;
	
	public String getCoverage() {
		return this.coverage;
	}
	
	public String getBeforeSha() {
		return this.beforeSha;
	}
	
	public String getFinishedAt() {
		return this.finishedAt;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getSha() {
		return this.sha;
	}
	
	public String getDuration() {
		return this.duration;
	}
	
	public String getYamlErrors() {
		return this.yamlErrors;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getCommittedAt() {
		return this.committedAt;
	}
	
	public String getStartedAt() {
		return this.startedAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTag() {
		return this.tag;
	}
	
	public Object getUser() {
		return this.user;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	@Override
	public String toString() {
		return "Pipeline [ "
			+ "coverage = " + this.coverage + ", "
			+ "beforeSha = " + this.beforeSha + ", "
			+ "finishedAt = " + this.finishedAt + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "sha = " + this.sha + ", "
			+ "duration = " + this.duration + ", "
			+ "yamlErrors = " + this.yamlErrors + ", "
			+ "ref = " + this.ref + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "committedAt = " + this.committedAt + ", "
			+ "startedAt = " + this.startedAt + ", "
			+ "id = " + this.id + ", "
			+ "tag = " + this.tag + ", "
			+ "user = " + this.user + ", "
			+ "status = " + this.status + ", "
			+ "]"; 
	}	
}	
