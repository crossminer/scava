package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TemplatesList {

	public TemplatesList(){}

	@JsonProperty("name") 
	private String name;
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "TemplatesList [ "
			+ "name = " + this.name + ", "
			+ "]"; 
	}	
}	
