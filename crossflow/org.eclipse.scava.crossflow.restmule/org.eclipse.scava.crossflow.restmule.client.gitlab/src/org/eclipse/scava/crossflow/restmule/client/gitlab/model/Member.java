package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Member {

	public Member(){}

	@JsonProperty("access_level") 
	private String accessLevel;
	
	@JsonProperty("expires_at") 
	private String expiresAt;
	
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
	
	public String getAccessLevel() {
		return this.accessLevel;
	}
	
	public String getExpiresAt() {
		return this.expiresAt;
	}
	
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
		return "Member [ "
			+ "accessLevel = " + this.accessLevel + ", "
			+ "expiresAt = " + this.expiresAt + ", "
			+ "avatarUrl = " + this.avatarUrl + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "name = " + this.name + ", "
			+ "id = " + this.id + ", "
			+ "state = " + this.state + ", "
			+ "username = " + this.username + ", "
			+ "]"; 
	}	
}	
