package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RunnerDetails {

	public RunnerDetails(){}

	@JsonProperty("projects") 
	private Object projects;
	
	@JsonProperty("active") 
	private String active;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("contacted_at") 
	private String contactedAt;
	
	@JsonProperty("version") 
	private String version;
	
	@JsonProperty("platform") 
	private String platform;
	
	@JsonProperty("revision") 
	private String revision;
	
	@JsonProperty("token") 
	private String token;
	
	@JsonProperty("tag_list") 
	private String tagList;
	
	@JsonProperty("is_shared") 
	private String isShared;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("locked") 
	private String locked;
	
	@JsonProperty("run_untagged") 
	private String runUntagged;
	
	@JsonProperty("architecture") 
	private String architecture;
	
	public Object getProjects() {
		return this.projects;
	}
	
	public String getActive() {
		return this.active;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getContactedAt() {
		return this.contactedAt;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public String getPlatform() {
		return this.platform;
	}
	
	public String getRevision() {
		return this.revision;
	}
	
	public String getToken() {
		return this.token;
	}
	
	public String getTagList() {
		return this.tagList;
	}
	
	public String getIsShared() {
		return this.isShared;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getLocked() {
		return this.locked;
	}
	
	public String getRunUntagged() {
		return this.runUntagged;
	}
	
	public String getArchitecture() {
		return this.architecture;
	}
	
	@Override
	public String toString() {
		return "RunnerDetails [ "
			+ "projects = " + this.projects + ", "
			+ "active = " + this.active + ", "
			+ "description = " + this.description + ", "
			+ "contactedAt = " + this.contactedAt + ", "
			+ "version = " + this.version + ", "
			+ "platform = " + this.platform + ", "
			+ "revision = " + this.revision + ", "
			+ "token = " + this.token + ", "
			+ "tagList = " + this.tagList + ", "
			+ "isShared = " + this.isShared + ", "
			+ "name = " + this.name + ", "
			+ "id = " + this.id + ", "
			+ "locked = " + this.locked + ", "
			+ "runUntagged = " + this.runUntagged + ", "
			+ "architecture = " + this.architecture + ", "
			+ "]"; 
	}	
}	
