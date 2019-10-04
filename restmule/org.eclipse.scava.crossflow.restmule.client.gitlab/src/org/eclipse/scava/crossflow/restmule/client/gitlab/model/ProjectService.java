package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectService {

	public ProjectService(){}

	@JsonProperty("pipeline_events") 
	private String pipelineEvents;
	
	@JsonProperty("issues_events") 
	private String issuesEvents;
	
	@JsonProperty("active") 
	private String active;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("merge_requests_events") 
	private String mergeRequestsEvents;
	
	@JsonProperty("note_events") 
	private String noteEvents;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("push_events") 
	private String pushEvents;
	
	@JsonProperty("tag_push_events") 
	private String tagPushEvents;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("build_events") 
	private String buildEvents;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("properties") 
	private String properties;
	
	public String getPipelineEvents() {
		return this.pipelineEvents;
	}
	
	public String getIssuesEvents() {
		return this.issuesEvents;
	}
	
	public String getActive() {
		return this.active;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getMergeRequestsEvents() {
		return this.mergeRequestsEvents;
	}
	
	public String getNoteEvents() {
		return this.noteEvents;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getPushEvents() {
		return this.pushEvents;
	}
	
	public String getTagPushEvents() {
		return this.tagPushEvents;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getBuildEvents() {
		return this.buildEvents;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getProperties() {
		return this.properties;
	}
	
	@Override
	public String toString() {
		return "ProjectService [ "
			+ "pipelineEvents = " + this.pipelineEvents + ", "
			+ "issuesEvents = " + this.issuesEvents + ", "
			+ "active = " + this.active + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "mergeRequestsEvents = " + this.mergeRequestsEvents + ", "
			+ "noteEvents = " + this.noteEvents + ", "
			+ "title = " + this.title + ", "
			+ "pushEvents = " + this.pushEvents + ", "
			+ "tagPushEvents = " + this.tagPushEvents + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "buildEvents = " + this.buildEvents + ", "
			+ "id = " + this.id + ", "
			+ "properties = " + this.properties + ", "
			+ "]"; 
	}	
}	
