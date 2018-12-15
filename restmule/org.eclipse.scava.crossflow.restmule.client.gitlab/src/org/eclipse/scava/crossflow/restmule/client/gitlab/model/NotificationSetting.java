package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NotificationSetting {

	public NotificationSetting(){}

	@JsonProperty("level") 
	private String level;
	
	@JsonProperty("events") 
	private String events;
	
	public String getLevel() {
		return this.level;
	}
	
	public String getEvents() {
		return this.events;
	}
	
	@Override
	public String toString() {
		return "NotificationSetting [ "
			+ "level = " + this.level + ", "
			+ "events = " + this.events + ", "
			+ "]"; 
	}	
}	
