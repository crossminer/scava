package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Label {

	public Label(){}

	@JsonProperty("subscribed") 
	private String subscribed;
	
	@JsonProperty("color") 
	private String color;
	
	@JsonProperty("open_issues_count") 
	private String openIssuesCount;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("open_merge_requests_count") 
	private String openMergeRequestsCount;
	
	@JsonProperty("closed_issues_count") 
	private String closedIssuesCount;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("priority") 
	private String priority;
	
	public String getSubscribed() {
		return this.subscribed;
	}
	
	public String getColor() {
		return this.color;
	}
	
	public String getOpenIssuesCount() {
		return this.openIssuesCount;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getOpenMergeRequestsCount() {
		return this.openMergeRequestsCount;
	}
	
	public String getClosedIssuesCount() {
		return this.closedIssuesCount;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getPriority() {
		return this.priority;
	}
	
	@Override
	public String toString() {
		return "Label [ "
			+ "subscribed = " + this.subscribed + ", "
			+ "color = " + this.color + ", "
			+ "openIssuesCount = " + this.openIssuesCount + ", "
			+ "name = " + this.name + ", "
			+ "openMergeRequestsCount = " + this.openMergeRequestsCount + ", "
			+ "closedIssuesCount = " + this.closedIssuesCount + ", "
			+ "description = " + this.description + ", "
			+ "id = " + this.id + ", "
			+ "priority = " + this.priority + ", "
			+ "]"; 
	}	
}	
