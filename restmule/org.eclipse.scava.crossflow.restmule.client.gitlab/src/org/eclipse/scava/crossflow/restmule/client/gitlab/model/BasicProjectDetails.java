package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicProjectDetails {

	public BasicProjectDetails(){}

	@JsonProperty("path") 
	private String path;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("path_with_namespace") 
	private String pathWithNamespace;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("http_url_to_repo") 
	private String httpUrlToRepo;
	
	@JsonProperty("name_with_namespace") 
	private String nameWithNamespace;
	
	public String getPath() {
		return this.path;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public String getPathWithNamespace() {
		return this.pathWithNamespace;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getHttpUrlToRepo() {
		return this.httpUrlToRepo;
	}
	
	public String getNameWithNamespace() {
		return this.nameWithNamespace;
	}
	
	@Override
	public String toString() {
		return "BasicProjectDetails [ "
			+ "path = " + this.path + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "pathWithNamespace = " + this.pathWithNamespace + ", "
			+ "name = " + this.name + ", "
			+ "id = " + this.id + ", "
			+ "httpUrlToRepo = " + this.httpUrlToRepo + ", "
			+ "nameWithNamespace = " + this.nameWithNamespace + ", "
			+ "]"; 
	}	
}	
