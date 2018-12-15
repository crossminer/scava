package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupDetail {

	public GroupDetail(){}

	@JsonProperty("lfs_enabled") 
	private String lfsEnabled;
	
	@JsonProperty("shared_projects") 
	private Object sharedProjects;
	
	@JsonProperty("path") 
	private String path;
	
	@JsonProperty("projects") 
	private Object projects;
	
	@JsonProperty("avatar_url") 
	private String avatarUrl;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("request_access_enabled") 
	private String requestAccessEnabled;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("visibility_level") 
	private String visibilityLevel;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("statistics") 
	private String statistics;
	
	public String getLfsEnabled() {
		return this.lfsEnabled;
	}
	
	public Object getSharedProjects() {
		return this.sharedProjects;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Object getProjects() {
		return this.projects;
	}
	
	public String getAvatarUrl() {
		return this.avatarUrl;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getRequestAccessEnabled() {
		return this.requestAccessEnabled;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getVisibilityLevel() {
		return this.visibilityLevel;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getStatistics() {
		return this.statistics;
	}
	
	@Override
	public String toString() {
		return "GroupDetail [ "
			+ "lfsEnabled = " + this.lfsEnabled + ", "
			+ "sharedProjects = " + this.sharedProjects + ", "
			+ "path = " + this.path + ", "
			+ "projects = " + this.projects + ", "
			+ "avatarUrl = " + this.avatarUrl + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "requestAccessEnabled = " + this.requestAccessEnabled + ", "
			+ "name = " + this.name + ", "
			+ "description = " + this.description + ", "
			+ "visibilityLevel = " + this.visibilityLevel + ", "
			+ "id = " + this.id + ", "
			+ "statistics = " + this.statistics + ", "
			+ "]"; 
	}	
}	
