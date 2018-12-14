package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Environment {

	public Environment(){}

	@JsonProperty("external_url") 
	private String externalUrl;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("project") 
	private Object project;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("slug") 
	private String slug;
	
	public String getExternalUrl() {
		return this.externalUrl;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Object getProject() {
		return this.project;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getSlug() {
		return this.slug;
	}
	
	@Override
	public String toString() {
		return "Environment [ "
			+ "externalUrl = " + this.externalUrl + ", "
			+ "name = " + this.name + ", "
			+ "project = " + this.project + ", "
			+ "id = " + this.id + ", "
			+ "slug = " + this.slug + ", "
			+ "]"; 
	}	
}	
