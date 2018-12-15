package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Badges {

	public Badges(){}

	@JsonProperty("badge_id") 
	private Integer badgeId;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("name") 
	private String name;
	
	@JsonProperty("description") 
	private String description;
	
	@JsonProperty("rank") 
	private String rank;
	
	@JsonProperty("badge_type") 
	private String badgeType;
	
	@JsonProperty("award_count") 
	private Integer awardCount;
	
	@JsonProperty("user") 
	private User user;
	
	public Integer getBadgeId() {
		return this.badgeId;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getRank() {
		return this.rank;
	}
	
	public String getBadgeType() {
		return this.badgeType;
	}
	
	public Integer getAwardCount() {
		return this.awardCount;
	}
	
	public User getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "Badges [ "
			+ "badgeId = " + this.badgeId + ", "
			+ "link = " + this.link + ", "
			+ "name = " + this.name + ", "
			+ "description = " + this.description + ", "
			+ "rank = " + this.rank + ", "
			+ "badgeType = " + this.badgeType + ", "
			+ "awardCount = " + this.awardCount + ", "
			+ "user = " + this.user + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class User {
	
		public User(){}
	
		@JsonProperty("profile_image") 
		private String profileImage;
		
		@JsonProperty("user_type") 
		private String userType;
		
		@JsonProperty("user_id") 
		private Integer userId;
		
		@JsonProperty("link") 
		private String link;
		
		@JsonProperty("reputation") 
		private Integer reputation;
		
		@JsonProperty("display_name") 
		private String displayName;
		
		@JsonProperty("accept_rate") 
		private Integer acceptRate;
		
		@JsonProperty("badge_counts") 
		private BadgeCounts badgeCounts;
		
		public String getProfileImage() {
			return this.profileImage;
		}
		
		public String getUserType() {
			return this.userType;
		}
		
		public Integer getUserId() {
			return this.userId;
		}
		
		public String getLink() {
			return this.link;
		}
		
		public Integer getReputation() {
			return this.reputation;
		}
		
		public String getDisplayName() {
			return this.displayName;
		}
		
		public Integer getAcceptRate() {
			return this.acceptRate;
		}
		
		public BadgeCounts getBadgeCounts() {
			return this.badgeCounts;
		}
		
		@Override
		public String toString() {
			return "User [ "
				+ "profileImage = " + this.profileImage + ", "
				+ "userType = " + this.userType + ", "
				+ "userId = " + this.userId + ", "
				+ "link = " + this.link + ", "
				+ "reputation = " + this.reputation + ", "
				+ "displayName = " + this.displayName + ", "
				+ "acceptRate = " + this.acceptRate + ", "
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
	
}	
