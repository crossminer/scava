package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Variable {

	public Variable(){}

	@JsonProperty("value") 
	private String value;
	
	@JsonProperty("key") 
	private String key;
	
	public String getValue() {
		return this.value;
	}
	
	public String getKey() {
		return this.key;
	}
	
	@Override
	public String toString() {
		return "Variable [ "
			+ "value = " + this.value + ", "
			+ "key = " + this.key + ", "
			+ "]"; 
	}	
}	
