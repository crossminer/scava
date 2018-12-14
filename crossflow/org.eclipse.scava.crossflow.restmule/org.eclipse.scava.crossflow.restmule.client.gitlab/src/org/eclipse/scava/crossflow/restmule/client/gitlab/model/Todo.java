package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Todo {

	public Todo(){}

	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("action_name") 
	private String actionName;
	
	@JsonProperty("target_url") 
	private String targetUrl;
	
	@JsonProperty("target_type") 
	private String targetType;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("project") 
	private Object project;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("target") 
	private String target;
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getActionName() {
		return this.actionName;
	}
	
	public String getTargetUrl() {
		return this.targetUrl;
	}
	
	public String getTargetType() {
		return this.targetType;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public Object getProject() {
		return this.project;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	@Override
	public String toString() {
		return "Todo [ "
			+ "author = " + this.author + ", "
			+ "actionName = " + this.actionName + ", "
			+ "targetUrl = " + this.targetUrl + ", "
			+ "targetType = " + this.targetType + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "project = " + this.project + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "body = " + this.body + ", "
			+ "target = " + this.target + ", "
			+ "]"; 
	}	
}	
