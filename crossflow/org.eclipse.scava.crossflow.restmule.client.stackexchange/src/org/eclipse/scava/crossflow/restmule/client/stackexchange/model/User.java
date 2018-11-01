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
	
	@JsonProperty("last_access_date") 
	private Integer lastAccessDate;
	
	@JsonProperty("last_modified_date") 
	private Integer lastModifiedDate;
	
	@JsonProperty("reputation") 
	private Integer reputation;
	
	@JsonProperty("answer_count") 
	private Integer answerCount;
	
	@JsonProperty("about_me") 
	private String aboutMe;
	
	@JsonProperty("accept_rate") 
	private Integer acceptRate;
	
	@JsonProperty("reputation_change_year") 
	private Integer reputationChangeYear;
	
	@JsonProperty("profile_image") 
	private String profileImage;
	
	@JsonProperty("user_type") 
	private String userType;
	
	@JsonProperty("down_vote_count") 
	private Integer downVoteCount;
	
	@JsonProperty("reputation_change_day") 
	private Integer reputationChangeDay;
	
	@JsonProperty("reputation_change_month") 
	private Integer reputationChangeMonth;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("display_name") 
	private String displayName;
	
	@JsonProperty("is_employee") 
	private Boolean isEmployee;
	
	@JsonProperty("question_count") 
	private Integer questionCount;
	
	@JsonProperty("account_id") 
	private Integer accountId;
	
	@JsonProperty("website_url") 
	private String websiteUrl;
	
	@JsonProperty("reputation_change_week") 
	private Integer reputationChangeWeek;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("timed_penalty_date") 
	private Integer timedPenaltyDate;
	
	@JsonProperty("location") 
	private String location;
	
	@JsonProperty("age") 
	private Integer age;
	
	@JsonProperty("up_vote_count") 
	private Integer upVoteCount;
	
	@JsonProperty("view_count") 
	private Integer viewCount;
	
	@JsonProperty("badge_counts") 
	private BadgeCounts badgeCounts;
	
	public Integer getReputationChangeQuarter() {
		return this.reputationChangeQuarter;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Integer getLastAccessDate() {
		return this.lastAccessDate;
	}
	
	public Integer getLastModifiedDate() {
		return this.lastModifiedDate;
	}
	
	public Integer getReputation() {
		return this.reputation;
	}
	
	public Integer getAnswerCount() {
		return this.answerCount;
	}
	
	public String getAboutMe() {
		return this.aboutMe;
	}
	
	public Integer getAcceptRate() {
		return this.acceptRate;
	}
	
	public Integer getReputationChangeYear() {
		return this.reputationChangeYear;
	}
	
	public String getProfileImage() {
		return this.profileImage;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	public Integer getDownVoteCount() {
		return this.downVoteCount;
	}
	
	public Integer getReputationChangeDay() {
		return this.reputationChangeDay;
	}
	
	public Integer getReputationChangeMonth() {
		return this.reputationChangeMonth;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getDisplayName() {
		return this.displayName;
	}
	
	public Boolean getIsEmployee() {
		return this.isEmployee;
	}
	
	public Integer getQuestionCount() {
		return this.questionCount;
	}
	
	public Integer getAccountId() {
		return this.accountId;
	}
	
	public String getWebsiteUrl() {
		return this.websiteUrl;
	}
	
	public Integer getReputationChangeWeek() {
		return this.reputationChangeWeek;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public Integer getTimedPenaltyDate() {
		return this.timedPenaltyDate;
	}
	
	public String getLocation() {
		return this.location;
	}
	
	public Integer getAge() {
		return this.age;
	}
	
	public Integer getUpVoteCount() {
		return this.upVoteCount;
	}
	
	public Integer getViewCount() {
		return this.viewCount;
	}
	
	public BadgeCounts getBadgeCounts() {
		return this.badgeCounts;
	}
	
	@Override
	public String toString() {
		return "User [ "
			+ "reputationChangeQuarter = " + this.reputationChangeQuarter + ", "
			+ "link = " + this.link + ", "
			+ "lastAccessDate = " + this.lastAccessDate + ", "
			+ "lastModifiedDate = " + this.lastModifiedDate + ", "
			+ "reputation = " + this.reputation + ", "
			+ "answerCount = " + this.answerCount + ", "
			+ "aboutMe = " + this.aboutMe + ", "
			+ "acceptRate = " + this.acceptRate + ", "
			+ "reputationChangeYear = " + this.reputationChangeYear + ", "
			+ "profileImage = " + this.profileImage + ", "
			+ "userType = " + this.userType + ", "
			+ "downVoteCount = " + this.downVoteCount + ", "
			+ "reputationChangeDay = " + this.reputationChangeDay + ", "
			+ "reputationChangeMonth = " + this.reputationChangeMonth + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "displayName = " + this.displayName + ", "
			+ "isEmployee = " + this.isEmployee + ", "
			+ "questionCount = " + this.questionCount + ", "
			+ "accountId = " + this.accountId + ", "
			+ "websiteUrl = " + this.websiteUrl + ", "
			+ "reputationChangeWeek = " + this.reputationChangeWeek + ", "
			+ "userId = " + this.userId + ", "
			+ "timedPenaltyDate = " + this.timedPenaltyDate + ", "
			+ "location = " + this.location + ", "
			+ "age = " + this.age + ", "
			+ "upVoteCount = " + this.upVoteCount + ", "
			+ "viewCount = " + this.viewCount + ", "
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
