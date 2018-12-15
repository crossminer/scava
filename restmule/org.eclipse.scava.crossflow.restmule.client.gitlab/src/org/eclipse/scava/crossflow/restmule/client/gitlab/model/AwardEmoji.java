package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AwardEmoji {

	public AwardEmoji(){}

	@JsonProperty("updated_at") 
	private String updatedAt;
	
	@JsonProperty("awardable_type") 
	private String awardableType;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("awardable_id") 
	private String awardableId;
	
	@JsonProperty("created_at") 
	private String createdAt;
	
	@JsonProperty("id") 
	private String id;
	
	@JsonProperty("user") 
	private Object user;
	
	public String getUpdatedAt() {
		return this.updatedAt;
	}
	
	public String getAwardableType() {
		return this.awardableType;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getAwardableId() {
		return this.awardableId;
	}
	
	public String getCreatedAt() {
		return this.createdAt;
	}
	
	public String getId() {
		return this.id;
	}
	
	public Object getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "AwardEmoji [ "
			+ "updatedAt = " + this.updatedAt + ", "
			+ "awardableType = " + this.awardableType + ", "
			+ "name = " + this.name + ", "
			+ "awardableId = " + this.awardableId + ", "
			+ "createdAt = " + this.createdAt + ", "
			+ "id = " + this.id + ", "
			+ "user = " + this.user + ", "
			+ "]"; 
	}	
}	
