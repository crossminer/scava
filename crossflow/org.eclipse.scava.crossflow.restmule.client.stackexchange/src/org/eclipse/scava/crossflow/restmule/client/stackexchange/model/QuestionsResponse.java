package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionsResponse {

	public QuestionsResponse(){}

	@JsonProperty("score") 
	private Integer score;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("last_activity_date") 
	private Integer lastActivityDate;
	
	@JsonProperty("is_answered") 
	private Boolean isAnswered;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("answer_count") 
	private Integer answerCount;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("question_id") 
	private Integer questionId;
	
	@JsonProperty("view_count") 
	private Integer viewCount;
	
	@JsonProperty("owner") 
	private Owner owner;
	
	@JsonProperty("tags") 
	private List<String> tags = new ArrayList<String>();
	
	public Integer getScore() {
		return this.score;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Integer getLastActivityDate() {
		return this.lastActivityDate;
	}
	
	public Boolean getIsAnswered() {
		return this.isAnswered;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public Integer getAnswerCount() {
		return this.answerCount;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Integer getQuestionId() {
		return this.questionId;
	}
	
	public Integer getViewCount() {
		return this.viewCount;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public List<String> getTags() {
		return this.tags;
	}
	
	@Override
	public String toString() {
		return "QuestionsResponse [ "
			+ "score = " + this.score + ", "
			+ "link = " + this.link + ", "
			+ "lastActivityDate = " + this.lastActivityDate + ", "
			+ "isAnswered = " + this.isAnswered + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "answerCount = " + this.answerCount + ", "
			+ "title = " + this.title + ", "
			+ "questionId = " + this.questionId + ", "
			+ "viewCount = " + this.viewCount + ", "
			+ "owner = " + this.owner + ", "
			+ "tags = " + this.tags + ", "
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
		
		@Override
		public String toString() {
			return "Owner [ "
				+ "profileImage = " + this.profileImage + ", "
				+ "userType = " + this.userType + ", "
				+ "userId = " + this.userId + ", "
				+ "link = " + this.link + ", "
				+ "reputation = " + this.reputation + ", "
				+ "displayName = " + this.displayName + ", "
				+ "]"; 
		}	
	}
	
}	
