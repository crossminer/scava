package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagSynonyms {

	public TagSynonyms(){}

	@JsonProperty("from_tag") 
	private String fromTag;
	
	@JsonProperty("last_applied_date") 
	private Integer lastAppliedDate;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("to_tag") 
	private String toTag;
	
	@JsonProperty("applied_count") 
	private Integer appliedCount;
	
	public String getFromTag() {
		return this.fromTag;
	}
	
	public Integer getLastAppliedDate() {
		return this.lastAppliedDate;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getToTag() {
		return this.toTag;
	}
	
	public Integer getAppliedCount() {
		return this.appliedCount;
	}
	
	@Override
	public String toString() {
		return "TagSynonyms [ "
			+ "fromTag = " + this.fromTag + ", "
			+ "lastAppliedDate = " + this.lastAppliedDate + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "toTag = " + this.toTag + ", "
			+ "appliedCount = " + this.appliedCount + ", "
			+ "]"; 
	}	
}	
