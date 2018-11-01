package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WritePermissions {

	public WritePermissions(){}

	@JsonProperty("min_seconds_between_actions") 
	private Integer minSecondsBetweenActions;
	
	@JsonProperty("max_daily_actions") 
	private Integer maxDailyActions;
	
	@JsonProperty("object_type") 
	private String objectType;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("can_delete") 
	private Boolean canDelete;
	
	@JsonProperty("can_edit") 
	private Boolean canEdit;
	
	@JsonProperty("can_add") 
	private Boolean canAdd;
	
	public Integer getMinSecondsBetweenActions() {
		return this.minSecondsBetweenActions;
	}
	
	public Integer getMaxDailyActions() {
		return this.maxDailyActions;
	}
	
	public String getObjectType() {
		return this.objectType;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public Boolean getCanDelete() {
		return this.canDelete;
	}
	
	public Boolean getCanEdit() {
		return this.canEdit;
	}
	
	public Boolean getCanAdd() {
		return this.canAdd;
	}
	
	@Override
	public String toString() {
		return "WritePermissions [ "
			+ "minSecondsBetweenActions = " + this.minSecondsBetweenActions + ", "
			+ "maxDailyActions = " + this.maxDailyActions + ", "
			+ "objectType = " + this.objectType + ", "
			+ "userId = " + this.userId + ", "
			+ "canDelete = " + this.canDelete + ", "
			+ "canEdit = " + this.canEdit + ", "
			+ "canAdd = " + this.canAdd + ", "
			+ "]"; 
	}	
}	
