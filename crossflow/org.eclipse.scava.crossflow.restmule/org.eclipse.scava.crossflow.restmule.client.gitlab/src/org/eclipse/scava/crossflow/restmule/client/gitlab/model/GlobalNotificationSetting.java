package org.eclipse.scava.crossflow.restmule.client.gitlab.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalNotificationSetting {

	public GlobalNotificationSetting(){}

	@JsonProperty("notification_email") 
	private String notificationEmail;
	
	@JsonProperty("level") 
	private String level;
	
	@JsonProperty("events") 
	private String events;
	
	public String getNotificationEmail() {
		return this.notificationEmail;
	}
	
	public String getLevel() {
		return this.level;
	}
	
	public String getEvents() {
		return this.events;
	}
	
	@Override
	public String toString() {
		return "GlobalNotificationSetting [ "
			+ "notificationEmail = " + this.notificationEmail + ", "
			+ "level = " + this.level + ", "
			+ "events = " + this.events + ", "
			+ "]"; 
	}	
}	
