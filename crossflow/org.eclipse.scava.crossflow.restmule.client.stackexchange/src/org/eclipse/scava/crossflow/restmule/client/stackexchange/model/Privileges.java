package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Privileges {

	public Privileges(){}

	@JsonProperty("short_description") 
	private String shortDescription;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("reputation") 
	private Integer reputation;
	
	public String getShortDescription() {
		return this.shortDescription;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Integer getReputation() {
		return this.reputation;
	}
	
	@Override
	public String toString() {
		return "Privileges [ "
			+ "shortDescription = " + this.shortDescription + ", "
			+ "description = " + this.description + ", "
			+ "reputation = " + this.reputation + ", "
			+ "]"; 
	}	
}	
