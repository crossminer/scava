package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Board {

	public Board(){}

	@JsonProperty("lists") 
	private Object lists;
	
	@JsonProperty("id") 
	private String id;
	
	public Object getLists() {
		return this.lists;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Board [ "
			+ "lists = " + this.lists + ", "
			+ "id = " + this.id + ", "
			+ "]"; 
	}	
}	
