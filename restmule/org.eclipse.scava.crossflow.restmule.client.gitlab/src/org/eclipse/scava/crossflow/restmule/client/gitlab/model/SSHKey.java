package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SSHKey {

	public SSHKey(){}

	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("can_push") 
	private String canPush;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("key") 
	private String key;
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getCanPush() {
		return this.canPush;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getKey() {
		return this.key;
	}
	
	@Override
	public String toString() {
		return "SSHKey [ "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "canPush = " + this.canPush + ", "
			+ "title = " + this.title + ", "
			+ "key = " + this.key + ", "
			+ "]"; 
	}	
}	
