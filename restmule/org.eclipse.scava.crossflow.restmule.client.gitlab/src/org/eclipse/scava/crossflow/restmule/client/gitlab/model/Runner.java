package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Runner {

	public Runner(){}

	@JsonProperty("is_shared") 
	private String isShared;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("active") 
	private String active;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("id") 
	private String id;
	
	public String getIsShared() {
		return this.isShared;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getActive() {
		return this.active;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Runner [ "
			+ "isShared = " + this.isShared + ", "
			+ "name = " + this.name + ", "
			+ "active = " + this.active + ", "
			+ "description = " + this.description + ", "
			+ "id = " + this.id + ", "
			+ "]"; 
	}	
}	
