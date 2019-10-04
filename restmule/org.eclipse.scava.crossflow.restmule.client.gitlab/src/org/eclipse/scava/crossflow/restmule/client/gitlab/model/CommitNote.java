package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitNote {

	public CommitNote(){}

	@JsonProperty("note") 
	private String note;
	
	@JsonProperty("path") 
	private String path;
	
	@JsonProperty("author") 
	private Object author;
	
	@JsonProperty("line") 
	private String line;
	
	@JsonProperty("line_type") 
	private String lineType;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	public String getNote() {
		return this.note;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	public String getLine() {
		return this.line;
	}
	
	public String getLineType() {
		return this.lineType;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	@Override
	public String toString() {
		return "CommitNote [ "
			+ "note = " + this.note + ", "
			+ "path = " + this.path + ", "
			+ "author = " + this.author + ", "
			+ "line = " + this.line + ", "
			+ "lineType = " + this.lineType + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "]"; 
	}	
}	
