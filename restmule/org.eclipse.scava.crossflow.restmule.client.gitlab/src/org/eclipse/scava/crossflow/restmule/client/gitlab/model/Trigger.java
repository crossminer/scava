package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Trigger {

	public Trigger(){}

	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("last_used") 
	private String lastUsed;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("deleted_at") 
	private String deletedAt;
	
	@JsonProperty("token") 
	private String token;
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getLastUsed() {
		return this.lastUsed;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getDeletedAt() {
		return this.deletedAt;
	}
	
	public String getToken() {
		return this.token;
	}
	
	@Override
	public String toString() {
		return "Trigger [ "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "lastUsed = " + this.lastUsed + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "deletedAt = " + this.deletedAt + ", "
			+ "token = " + this.token + ", "
			+ "]"; 
	}	
}	
