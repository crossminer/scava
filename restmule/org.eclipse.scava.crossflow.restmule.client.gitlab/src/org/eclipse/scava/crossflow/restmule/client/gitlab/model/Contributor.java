package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Contributor {

	public Contributor(){}

	@JsonProperty("additions") 
	private String additions;
	
	@JsonProperty("deletions") 
	private String deletions;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("commits") 
	private String commits;
	
	@JsonProperty("email") 
	private String email;
	
	public String getAdditions() {
		return this.additions;
	}
	
	public String getDeletions() {
		return this.deletions;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCommits() {
		return this.commits;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	@Override
	public String toString() {
		return "Contributor [ "
			+ "additions = " + this.additions + ", "
			+ "deletions = " + this.deletions + ", "
			+ "name = " + this.name + ", "
			+ "commits = " + this.commits + ", "
			+ "email = " + this.email + ", "
			+ "]"; 
	}	
}	
