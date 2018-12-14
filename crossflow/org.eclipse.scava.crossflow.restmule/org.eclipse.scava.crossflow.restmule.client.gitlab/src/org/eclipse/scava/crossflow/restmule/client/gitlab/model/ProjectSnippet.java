package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectSnippet {

	public ProjectSnippet(){}

	@JsonProperty("expires_at") 
	private String expiresAt;
	
	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("web_url") 
	private String webUrl;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("file_name") 
	private String fileName;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("title") 
	private String title;
	
	public String getExpiresAt() {
		return this.expiresAt;
	}
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getWebUrl() {
		return this.webUrl;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return "ProjectSnippet [ "
			+ "expiresAt = " + this.expiresAt + ", "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "author = " + this.author + ", "
			+ "fileName = " + this.fileName + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "title = " + this.title + ", "
			+ "]"; 
	}	
}	
