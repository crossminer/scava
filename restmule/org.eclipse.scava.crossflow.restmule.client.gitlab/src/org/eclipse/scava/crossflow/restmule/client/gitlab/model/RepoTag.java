package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoTag {

	public RepoTag(){}

	@JsonProperty("release") 
	private Object release;
	
	@JsonProperty("commit") 
	private String commit;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("message") 
	private String message;
	
	public Object getRelease() {
		return this.release;
	}
	
	public String getCommit() {
		return this.commit;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getMessage() {
		return this.message;
	}
	
	@Override
	public String toString() {
		return "RepoTag [ "
			+ "release = " + this.release + ", "
			+ "commit = " + this.commit + ", "
			+ "name = " + this.name + ", "
			+ "message = " + this.message + ", "
			+ "]"; 
	}	
}	
