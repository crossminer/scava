package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoTreeObject {

	public RepoTreeObject(){}

	@JsonProperty("mode") 
	private String mode;
	
	@JsonProperty("path") 
	private String path;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("type") 
	private String type;
	
	public String getMode() {
		return this.mode;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return "RepoTreeObject [ "
			+ "mode = " + this.mode + ", "
			+ "path = " + this.path + ", "
			+ "name = " + this.name + ", "
			+ "id = " + this.id + ", "
			+ "type = " + this.type + ", "
			+ "]"; 
	}	
}	
