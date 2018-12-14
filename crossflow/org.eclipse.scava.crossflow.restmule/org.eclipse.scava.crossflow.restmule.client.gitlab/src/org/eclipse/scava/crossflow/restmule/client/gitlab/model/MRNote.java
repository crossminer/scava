package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MRNote {

	public MRNote(){}

	@JsonProperty("note") 
	private String note;
	
	@JsonProperty("author") 
	private Object author;
	
	public String getNote() {
		return this.note;
	}
	
	public Object getAuthor() {
		return this.author;
	}
	
	@Override
	public String toString() {
		return "MRNote [ "
			+ "note = " + this.note + ", "
			+ "author = " + this.author + ", "
			+ "]"; 
	}	
}	
