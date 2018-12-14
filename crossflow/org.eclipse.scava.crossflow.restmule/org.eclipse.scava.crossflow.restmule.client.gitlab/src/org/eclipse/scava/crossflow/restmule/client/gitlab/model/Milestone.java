package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Milestone {

	public Milestone(){}

	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("iid") 
	private String iid;
	
	@JsonProperty("project_id") 
	private String projectId;
	
	@JsonProperty("due_date") 
	private String dueDate;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("start_date") 
	private String startDate;
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getIid() {
		return this.iid;
	}
	
	public String getProjectId() {
		return this.projectId;
	}
	
	public String getDueDate() {
		return this.dueDate;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getStartDate() {
		return this.startDate;
	}
	
	@Override
	public String toString() {
		return "Milestone [ "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "iid = " + this.iid + ", "
			+ "projectId = " + this.projectId + ", "
			+ "dueDate = " + this.dueDate + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "description = " + this.description + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "title = " + this.title + ", "
			+ "startDate = " + this.startDate + ", "
			+ "]"; 
	}	
}	
