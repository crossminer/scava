package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MergeRequestDiffFull {

	public MergeRequestDiffFull(){}

	@JsonProperty("merge_request_id") 
	private String mergeRequestId;
	
	@JsonProperty("head_commit_sha") 
	private String headCommitSha;
	
	@JsonProperty("start_commit_sha") 
	private String startCommitSha;
	
	@JsonProperty("commits") 
	private Object commits;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("real_size") 
	private String realSize;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("diffs") 
	private Object diffs;
	
	@JsonProperty("base_commit_sha") 
	private String baseCommitSha;
	
	public String getMergeRequestId() {
		return this.mergeRequestId;
	}
	
	public String getHeadCommitSha() {
		return this.headCommitSha;
	}
	
	public String getStartCommitSha() {
		return this.startCommitSha;
	}
	
	public Object getCommits() {
		return this.commits;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getRealSize() {
		return this.realSize;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public Object getDiffs() {
		return this.diffs;
	}
	
	public String getBaseCommitSha() {
		return this.baseCommitSha;
	}
	
	@Override
	public String toString() {
		return "MergeRequestDiffFull [ "
			+ "mergeRequestId = " + this.mergeRequestId + ", "
			+ "headCommitSha = " + this.headCommitSha + ", "
			+ "startCommitSha = " + this.startCommitSha + ", "
			+ "commits = " + this.commits + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "realSize = " + this.realSize + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "diffs = " + this.diffs + ", "
			+ "baseCommitSha = " + this.baseCommitSha + ", "
			+ "]"; 
	}	
}	
