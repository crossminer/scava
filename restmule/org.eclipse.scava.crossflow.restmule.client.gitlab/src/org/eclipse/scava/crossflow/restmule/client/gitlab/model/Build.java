package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Build {

	public Build(){}

	@JsonProperty("coverage") 
	private String coverage;
	
	@JsonProperty("finished_at") 
	private String finishedAt;
	
	@JsonProperty("commit") 
	private Object commit;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("pipeline") 
	private Object pipeline;
	
	@JsonProperty("ref") 
	private String ref;
	
	@JsonProperty("artifacts_file") 
	private Object artifactsFile;
	
	@JsonProperty("stage") 
	private String stage;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("started_at") 
	private String startedAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("tag") 
	private String tag;
	
	@JsonProperty("runner") 
	private Object runner;
	
	@JsonProperty("user") 
	private Object user;
	
	@JsonProperty("status") 
	private String status;
	
	public String getCoverage() {
		return this.coverage;
	}
	
	public String getFinishedAt() {
		return this.finishedAt;
	}
	
	public Object getCommit() {
		return this.commit;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public Object getPipeline() {
		return this.pipeline;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public Object getArtifactsFile() {
		return this.artifactsFile;
	}
	
	public String getStage() {
		return this.stage;
	}
	
	public String getName() {
		return this.name;
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
	
	public Object getRunner() {
		return this.runner;
	}
	
	public Object getUser() {
		return this.user;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	@Override
	public String toString() {
		return "Build [ "
			+ "coverage = " + this.coverage + ", "
			+ "finishedAt = " + this.finishedAt + ", "
			+ "commit = " + this.commit + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "pipeline = " + this.pipeline + ", "
			+ "ref = " + this.ref + ", "
			+ "artifactsFile = " + this.artifactsFile + ", "
			+ "stage = " + this.stage + ", "
			+ "name = " + this.name + ", "
			+ "startedAt = " + this.startedAt + ", "
			+ "id = " + this.id + ", "
			+ "tag = " + this.tag + ", "
			+ "runner = " + this.runner + ", "
			+ "user = " + this.user + ", "
			+ "status = " + this.status + ", "
			+ "]"; 
	}	
}	
