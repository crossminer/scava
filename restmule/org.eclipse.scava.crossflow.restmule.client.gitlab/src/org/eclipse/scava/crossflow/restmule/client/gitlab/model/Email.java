package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Email {

	public Email(){}

	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("email") 
	private String email;
	
	public String getId() {
		return this.id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public String toString() {
		return "Email [ "
			+ "id = " + this.id + ", "
			+ "email = " + this.email + ", "
			+ "]"; 
	}	
}	
