package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tags {

	public Tags(){}

	@JsonProperty("is_required") 
	private Boolean isRequired;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("count") 
	private Integer count;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("has_synonyms") 
	private Boolean hasSynonyms;
	
	@JsonProperty("is_moderator_only") 
	private Boolean isModeratorOnly;
	
	@JsonProperty("last_activity_date") 
	private Integer lastActivityDate;
	
	@JsonProperty("synonyms") 
	private List<Object> synonyms = new ArrayList<Object>();
	
	public Boolean getIsRequired() {
		return this.isRequired;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public Integer getCount() {
		return this.count;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Boolean getHasSynonyms() {
		return this.hasSynonyms;
	}
	
	public Boolean getIsModeratorOnly() {
		return this.isModeratorOnly;
	}
	
	public Integer getLastActivityDate() {
		return this.lastActivityDate;
	}
	
	public List<Object> getSynonyms() {
		return this.synonyms;
	}
	
	@Override
	public String toString() {
		return "Tags [ "
			+ "isRequired = " + this.isRequired + ", "
			+ "userId = " + this.userId + ", "
			+ "count = " + this.count + ", "
			+ "name = " + this.name + ", "
			+ "hasSynonyms = " + this.hasSynonyms + ", "
			+ "isModeratorOnly = " + this.isModeratorOnly + ", "
			+ "lastActivityDate = " + this.lastActivityDate + ", "
			+ "synonyms = " + this.synonyms + ", "
			+ "]"; 
	}	
}	
