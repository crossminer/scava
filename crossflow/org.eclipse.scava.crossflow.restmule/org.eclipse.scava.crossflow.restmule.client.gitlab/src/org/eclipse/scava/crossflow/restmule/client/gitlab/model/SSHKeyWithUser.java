package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SSHKeyWithUser {

	public SSHKeyWithUser(){}

	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("can_push") 
	private String canPush;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("user") 
	private Object user;
	
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
	
	public Object getUser() {
		return this.user;
	}
	
	public String getKey() {
		return this.key;
	}
	
	@Override
	public String toString() {
		return "SSHKeyWithUser [ "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "canPush = " + this.canPush + ", "
			+ "title = " + this.title + ", "
			+ "user = " + this.user + ", "
			+ "key = " + this.key + ", "
			+ "]"; 
	}	
}	
