package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Hook {

	public Hook(){}

	@JsonProperty("push_events") 
	private String pushEvents;
	
	@JsonProperty("tag_push_events") 
	private String tagPushEvents;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("enable_ssl_verification") 
	private String enableSslVerification;
	
	@JsonProperty("url") 
	private String url;
	
	public String getPushEvents() {
		return this.pushEvents;
	}
	
	public String getTagPushEvents() {
		return this.tagPushEvents;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getEnableSslVerification() {
		return this.enableSslVerification;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	@Override
	public String toString() {
		return "Hook [ "
			+ "pushEvents = " + this.pushEvents + ", "
			+ "tagPushEvents = " + this.tagPushEvents + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "enableSslVerification = " + this.enableSslVerification + ", "
			+ "url = " + this.url + ", "
			+ "]"; 
	}	
}	
