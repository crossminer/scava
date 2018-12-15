package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Answers {

	public Answers(){}

	@JsonProperty("score") 
	private Integer score;
	
	@JsonProperty("is_accepted") 
	private Boolean isAccepted;
	
	@JsonProperty("last_activity_date") 
	private Integer lastActivityDate;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("question_id") 
	private Integer questionId;
	
	@JsonProperty("answer_id") 
	private Integer answerId;
	
	@JsonProperty("owner") 
	private Owner owner;
	
	public Integer getScore() {
		return this.score;
	}
	
	public Boolean getIsAccepted() {
		return this.isAccepted;
	}
	
	public Integer getLastActivityDate() {
		return this.lastActivityDate;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public Integer getQuestionId() {
		return this.questionId;
	}
	
	public Integer getAnswerId() {
		return this.answerId;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	@Override
	public String toString() {
		return "Answers [ "
			+ "score = " + this.score + ", "
			+ "isAccepted = " + this.isAccepted + ", "
			+ "lastActivityDate = " + this.lastActivityDate + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "questionId = " + this.questionId + ", "
			+ "answerId = " + this.answerId + ", "
			+ "owner = " + this.owner + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Owner {
	
		public Owner(){}
	
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
			return "Owner [ "
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
