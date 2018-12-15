package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Template {

	public Template(){}

	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("content") 
	private String content;
	
	public String getName() {
		return this.name;
	}
	
	public String getContent() {
		return this.content;
	}
	
	@Override
	public String toString() {
		return "Template [ "
			+ "name = " + this.name + ", "
			+ "content = " + this.content + ", "
			+ "]"; 
	}	
}	
