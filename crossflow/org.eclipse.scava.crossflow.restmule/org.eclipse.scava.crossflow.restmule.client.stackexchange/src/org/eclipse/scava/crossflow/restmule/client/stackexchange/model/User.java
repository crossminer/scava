package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

	public User(){}

	@JsonProperty("reputation_change_quarter") 
	private Integer reputationChangeQuarter;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("last_modified_date") 
	private Long lastModifiedDate;
	
	@JsonProperty("last_access_date") 
	private Long lastAccessDate;
	
	@JsonProperty("reputation") 
	private Long reputation;
	
	@JsonProperty("creation_date") 
	private Long creationDate;
	
	@JsonProperty("display_name") 
	private String displayName;
	
	@JsonProperty("reputation_change_year") 
	private Integer reputationChangeYear;
	
	@JsonProperty("accept_rate") 
	private Integer acceptRate;
	
	@JsonProperty("is_employee") 
	private Boolean isEmployee;
	
	@JsonProperty("profile_image") 
	private String profileImage;
	
	@JsonProperty("account_id") 
	private Long accountId;
	
	@JsonProperty("user_type") 
	private String userType;
	
	@JsonProperty("website_url") 
	private String websiteUrl;
	
	@JsonProperty("reputation_change_week") 
	private Integer reputationChangeWeek;
	
	@JsonProperty("user_id") 
	private Long userId;
	
	@JsonProperty("reputation_change_day") 
	private Integer reputationChangeDay;
	
	@JsonProperty("location") 
	private String location;
	
	@JsonProperty("reputation_change_month") 
	private Integer reputationChangeMonth;
	
	@JsonProperty("badge_counts") 
	private BadgeCounts badgeCounts;
	
	public Integer getReputationChangeQuarter() {
		return this.reputationChangeQuarter;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Long getLastModifiedDate() {
		return this.lastModifiedDate;
	}
	
	public Long getLastAccessDate() {
		return this.lastAccessDate;
	}
	
	public Long getReputation() {
		return this.reputation;
	}
	
	public Long getCreationDate() {
		return this.creationDate;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public Integer getReputationChangeYear() {
		return this.reputationChangeYear;
	}
	
	public Integer getAcceptRate() {
		return this.acceptRate;
	}
	
	public Boolean getIsEmployee() {
		return this.isEmployee;
	}
	
	public String getProfileImage() {
		return this.profileImage;
	}
	
	public Long getAccountId() {
		return this.accountId;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	public String getWebsiteUrl() {
		return this.websiteUrl;
	}
	
	public Integer getReputationChangeWeek() {
		return this.reputationChangeWeek;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	
	public Integer getReputationChangeDay() {
		return this.reputationChangeDay;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public Integer getReputationChangeMonth() {
		return this.reputationChangeMonth;
	}
	
	public BadgeCounts getBadgeCounts() {
		return this.badgeCounts;
	}
	
	@Override
	public String toString() {
		return "User [ "
			+ "reputationChangeQuarter = " + this.reputationChangeQuarter + ", "
			+ "link = " + this.link + ", "
			+ "lastModifiedDate = " + this.lastModifiedDate + ", "
			+ "lastAccessDate = " + this.lastAccessDate + ", "
			+ "reputation = " + this.reputation + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "displayName = " + this.displayName + ", "
			+ "reputationChangeYear = " + this.reputationChangeYear + ", "
			+ "acceptRate = " + this.acceptRate + ", "
			+ "isEmployee = " + this.isEmployee + ", "
			+ "profileImage = " + this.profileImage + ", "
			+ "accountId = " + this.accountId + ", "
			+ "userType = " + this.userType + ", "
			+ "websiteUrl = " + this.websiteUrl + ", "
			+ "reputationChangeWeek = " + this.reputationChangeWeek + ", "
			+ "userId = " + this.userId + ", "
			+ "reputationChangeDay = " + this.reputationChangeDay + ", "
			+ "location = " + this.location + ", "
			+ "reputationChangeMonth = " + this.reputationChangeMonth + ", "
			+ "badgeCounts = " + this.badgeCounts + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class BadgeCounts {
	
		public BadgeCounts(){}
	
		@JsonProperty("gold") 
		private Integer gold;
		
		@JsonProperty("silver") 
		private Integer silver;
		
		@JsonProperty("bronze") 
		private Integer bronze;
		
		public Integer getGold() {
			return this.gold;
		}
		
		public Integer getSilver() {
			return this.silver;
		}
		
		public Integer getBronze() {
			return this.bronze;
		}
		
		@Override
		public String toString() {
			return "BadgeCounts [ "
				+ "gold = " + this.gold + ", "
				+ "silver = " + this.silver + ", "
				+ "bronze = " + this.bronze + ", "
				+ "]"; 
		}	
	}
	
}	
