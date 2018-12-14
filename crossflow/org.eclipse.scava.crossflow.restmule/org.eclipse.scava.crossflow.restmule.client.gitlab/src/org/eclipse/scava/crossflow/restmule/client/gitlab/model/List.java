package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class List {

	public List(){}

	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("label") 
	private Object label;
	
	@JsonProperty("position") 
	private String position;
	
	public String getId() {
		return this.id;
	}
	
	public Object getLabel() {
		return this.label;
	}
	
	public String getPosition() {
		return this.position;
	}
	
	@Override
	public String toString() {
		return "List [ "
			+ "id = " + this.id + ", "
			+ "label = " + this.label + ", "
			+ "position = " + this.position + ", "
			+ "]"; 
	}	
}	
