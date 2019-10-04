package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoBranch {

	public RepoBranch(){}

	@JsonProperty("protected") 
	private String protectedSanitized;
	
	@JsonProperty("developers_can_push") 
	private String developersCanPush;
	
	@JsonProperty("developers_can_merge") 
	private String developersCanMerge;
	
	@JsonProperty("commit") 
	private String commit;
	
	@JsonProperty("merged") 
	private String merged;
	
	@JsonProperty("name") 
	private String name;
	
	public String getProtectedSanitized() {
		return this.protectedSanitized;
	}
	
	public String getDevelopersCanPush() {
		return this.developersCanPush;
	}
	
	public String getDevelopersCanMerge() {
		return this.developersCanMerge;
	}
	
	public String getCommit() {
		return this.commit;
	}
	
	public String getMerged() {
		return this.merged;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return "RepoBranch [ "
			+ "protectedSanitized = " + this.protectedSanitized + ", "
			+ "developersCanPush = " + this.developersCanPush + ", "
			+ "developersCanMerge = " + this.developersCanMerge + ", "
			+ "commit = " + this.commit + ", "
			+ "merged = " + this.merged + ", "
			+ "name = " + this.name + ", "
			+ "]"; 
	}	
}	
