package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkUsers {

	public NetworkUsers(){}

	@JsonProperty("last_access_date") 
	private Integer lastAccessDate;
	
	@JsonProperty("reputation") 
	private Integer reputation;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("answer_count") 
	private Integer answerCount;
	
	@JsonProperty("question_count") 
	private Integer questionCount;
	
	@JsonProperty("site_name") 
	private String siteName;
	
	@JsonProperty("account_id") 
	private Integer accountId;
	
	@JsonProperty("user_type") 
	private String userType;
	
	@JsonProperty("site_url") 
	private String siteUrl;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("badge_counts") 
	private BadgeCounts badgeCounts;
	
	@JsonProperty("top_questions") 
	private List<Object> topQuestions = new ArrayList<Object>();
	
	@JsonProperty("top_answers") 
	private List<Object> topAnswers = new ArrayList<Object>();
	
	public Integer getLastAccessDate() {
		return this.lastAccessDate;
	}
	
	public Integer getReputation() {
		return this.reputation;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public Integer getAnswerCount() {
		return this.answerCount;
	}
	
	public Integer getQuestionCount() {
		return this.questionCount;
	}
	
	public String getSiteName() {
		return this.siteName;
	}
	
	public Integer getAccountId() {
		return this.accountId;
	}
	
	public String getUserType() {
		return this.userType;
	}
	
	public String getSiteUrl() {
		return this.siteUrl;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public BadgeCounts getBadgeCounts() {
		return this.badgeCounts;
	}
	
	public List<Object> getTopQuestions() {
		return this.topQuestions;
	}
	
	public List<Object> getTopAnswers() {
		return this.topAnswers;
	}
	
	@Override
	public String toString() {
		return "NetworkUsers [ "
			+ "lastAccessDate = " + this.lastAccessDate + ", "
			+ "reputation = " + this.reputation + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "answerCount = " + this.answerCount + ", "
			+ "questionCount = " + this.questionCount + ", "
			+ "siteName = " + this.siteName + ", "
			+ "accountId = " + this.accountId + ", "
			+ "userType = " + this.userType + ", "
			+ "siteUrl = " + this.siteUrl + ", "
			+ "userId = " + this.userId + ", "
			+ "badgeCounts = " + this.badgeCounts + ", "
			+ "topQuestions = " + this.topQuestions + ", "
			+ "topAnswers = " + this.topAnswers + ", "
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
