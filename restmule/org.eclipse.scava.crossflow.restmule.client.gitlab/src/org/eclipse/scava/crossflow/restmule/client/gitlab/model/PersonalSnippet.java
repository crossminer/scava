package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonalSnippet {

	public PersonalSnippet(){}

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
	
	@JsonProperty("raw_url") 
	private String rawUrl;
	
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
	
	public String getRawUrl() {
		return this.rawUrl;
	}
	
	@Override
	public String toString() {
		return "PersonalSnippet [ "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "webUrl = " + this.webUrl + ", "
			+ "author = " + this.author + ", "
			+ "fileName = " + this.fileName + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "title = " + this.title + ", "
			+ "rawUrl = " + this.rawUrl + ", "
			+ "]"; 
	}	
}	
