package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Error {

	public Error(){}

	@JsonProperty("error_message") 
	private String errorMessage;
	
	@JsonProperty("error_name") 
	private String errorName;
	
	@JsonProperty("error_id") 
	private Float errorId;
	
	public String getErrorMessage() {
		return this.errorMessage;
	}
	
	public String getErrorName() {
		return this.errorName;
	}
	
	public Float getErrorId() {
		return this.errorId;
	}
	
	@Override
	public String toString() {
		return "Error [ "
			+ "errorMessage = " + this.errorMessage + ", "
			+ "errorName = " + this.errorName + ", "
			+ "errorId = " + this.errorId + ", "
			+ "]"; 
	}	
}	
