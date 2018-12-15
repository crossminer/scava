package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Namespace {

	public Namespace(){}

	@JsonProperty("path") 
	private String path;
	
	@JsonProperty("kind") 
	private String kind;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("id") 
	private String id;
	
	public String getPath() {
		return this.path;
	}
	
	public String getKind() {
		return this.kind;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public String toString() {
		return "Namespace [ "
			+ "path = " + this.path + ", "
			+ "kind = " + this.kind + ", "
			+ "name = " + this.name + ", "
			+ "id = " + this.id + ", "
			+ "]"; 
	}	
}	
