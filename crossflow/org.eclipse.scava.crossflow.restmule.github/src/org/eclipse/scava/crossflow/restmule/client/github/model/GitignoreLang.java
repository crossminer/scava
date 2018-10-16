package org.eclipse.scava.crossflow.restmule.client.github.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GitignoreLang {

	public GitignoreLang(){}

	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("source") 
	private String source;
	
	public String getName() {
		return this.name;
	}
	
	public String getSource() {
		return this.source;
	}
	
	@Override
	public String toString() {
		return "GitignoreLang [ "
			+ "name = " + this.name + ", "
			+ "source = " + this.source + ", "
			+ "]"; 
	}	
}	
