package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBasic {

	public UserBasic(){}

	@JsonProperty("avatar_url") 
	private String avatarUrl;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("state") 
	private String state;
	
	@JsonProperty("username") 
	private String username;
	
	public String getAvatarUrl() {
		return this.avatarUrl;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String toString() {
		return "UserBasic [ "
			+ "avatarUrl = " + this.avatarUrl + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "name = " + this.name + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "username = " + this.username + ", "
			+ "]"; 
	}	
}	
