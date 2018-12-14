package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoLicense {

	public RepoLicense(){}

	@JsonProperty("permissions") 
	private String permissions;
	
	@JsonProperty("html_url") 
	private String htmlUrl;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("nickname") 
	private String nickname;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("conditions") 
	private String conditions;
	
	@JsonProperty("popular") 
	private String popular;
	
	@JsonProperty("content") 
	private String content;
	
	@JsonProperty("key") 
	private String key;
	
	@JsonProperty("source_url") 
	private String sourceUrl;
	
	@JsonProperty("limitations") 
	private String limitations;
	
	public String getPermissions() {
		return this.permissions;
	}
	
	public String getHtmlUrl() {
		return this.htmlUrl;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getConditions() {
		return this.conditions;
	}
	
	public String getPopular() {
		return this.popular;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public String getKey() {
		return this.key;
	}
	
	public String getSourceUrl() {
		return this.sourceUrl;
	}
	
	public String getLimitations() {
		return this.limitations;
	}
	
	@Override
	public String toString() {
		return "RepoLicense [ "
			+ "permissions = " + this.permissions + ", "
			+ "htmlUrl = " + this.htmlUrl + ", "
			+ "name = " + this.name + ", "
			+ "nickname = " + this.nickname + ", "
			+ "description = " + this.description + ", "
			+ "conditions = " + this.conditions + ", "
			+ "popular = " + this.popular + ", "
			+ "content = " + this.content + ", "
			+ "key = " + this.key + ", "
			+ "sourceUrl = " + this.sourceUrl + ", "
			+ "limitations = " + this.limitations + ", "
			+ "]"; 
	}	
}	
