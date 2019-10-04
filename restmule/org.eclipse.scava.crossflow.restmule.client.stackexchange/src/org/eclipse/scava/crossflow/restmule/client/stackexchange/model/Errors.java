package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Errors {

	public Errors(){}

	@JsonProperty("error_name") 
	private String errorName;
	
	@JsonProperty("error_id") 
	private Integer errorId;
	
	@JsonProperty("description") 
	private String description;
	
	public String getErrorName() {
		return this.errorName;
	}
	
	public Integer getErrorId() {
		return this.errorId;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	@Override
	public String toString() {
		return "Errors [ "
			+ "errorName = " + this.errorName + ", "
			+ "errorId = " + this.errorId + ", "
			+ "description = " + this.description + ", "
			+ "]"; 
	}	
}	
