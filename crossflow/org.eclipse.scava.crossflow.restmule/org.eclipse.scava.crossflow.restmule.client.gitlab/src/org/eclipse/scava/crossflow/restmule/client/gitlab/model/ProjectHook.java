package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectHook {

	public ProjectHook(){}

	@JsonProperty("pipeline_events") 
	private String pipelineEvents;
	
	@JsonProperty("issues_events") 
	private String issuesEvents;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("merge_requests_events") 
	private String mergeRequestsEvents;
	
	@JsonProperty("note_events") 
	private String noteEvents;
	
	@JsonProperty("url") 
	private String url;
	
	@JsonProperty("push_events") 
	private String pushEvents;
	
	@JsonProperty("tag_push_events") 
	private String tagPushEvents;
	
	@JsonProperty("project_id") 
	private String projectId;
	
	@JsonProperty("build_events") 
	private String buildEvents;
	
	@JsonProperty("wiki_page_events") 
	private String wikiPageEvents;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("enable_ssl_verification") 
	private String enableSslVerification;
	
	public String getPipelineEvents() {
		return this.pipelineEvents;
	}
	
	public String getIssuesEvents() {
		return this.issuesEvents;
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
	
	public String getUrl() {
		return this.url;
	}
	
	public String getPushEvents() {
		return this.pushEvents;
	}
	
	public String getTagPushEvents() {
		return this.tagPushEvents;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	
	public String getBuildEvents() {
		return this.buildEvents;
	}
	
	public String getWikiPageEvents() {
		return this.wikiPageEvents;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getEnableSslVerification() {
		return this.enableSslVerification;
	}
	
	@Override
	public String toString() {
		return "ProjectHook [ "
			+ "pipelineEvents = " + this.pipelineEvents + ", "
			+ "issuesEvents = " + this.issuesEvents + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "mergeRequestsEvents = " + this.mergeRequestsEvents + ", "
			+ "noteEvents = " + this.noteEvents + ", "
			+ "url = " + this.url + ", "
			+ "pushEvents = " + this.pushEvents + ", "
			+ "tagPushEvents = " + this.tagPushEvents + ", "
			+ "projectId = " + this.projectId + ", "
			+ "buildEvents = " + this.buildEvents + ", "
			+ "wikiPageEvents = " + this.wikiPageEvents + ", "
			+ "id = " + this.id + ", "
			+ "enableSslVerification = " + this.enableSslVerification + ", "
			+ "]"; 
	}	
}	
