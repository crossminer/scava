package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SuggestedEdits {

	public SuggestedEdits(){}

	@JsonProperty("rejection_date") 
	private Integer rejectionDate;
	
	@JsonProperty("approval_date") 
	private Integer approvalDate;
	
	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("comment") 
	private String comment;
	
	@JsonProperty("post_type") 
	private String postType;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("suggested_edit_id") 
	private Integer suggestedEditId;
	
	@JsonProperty("proposing_user") 
	private ProposingUser proposingUser;
	
	@JsonProperty("tags") 
	private List<Object> tags = new ArrayList<Object>();
	
	public Integer getRejectionDate() {
		return this.rejectionDate;
	}
	
	public Integer getApprovalDate() {
		return this.approvalDate;
	}
	
	public Integer getPostId() {
		return this.postId;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public String getPostType() {
		return this.postType;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Integer getSuggestedEditId() {
		return this.suggestedEditId;
	}
	
	public ProposingUser getProposingUser() {
		return this.proposingUser;
	}
	
	public List<Object> getTags() {
		return this.tags;
	}
	
	@Override
	public String toString() {
		return "SuggestedEdits [ "
			+ "rejectionDate = " + this.rejectionDate + ", "
			+ "approvalDate = " + this.approvalDate + ", "
			+ "postId = " + this.postId + ", "
			+ "comment = " + this.comment + ", "
			+ "postType = " + this.postType + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "body = " + this.body + ", "
			+ "title = " + this.title + ", "
			+ "suggestedEditId = " + this.suggestedEditId + ", "
			+ "proposingUser = " + this.proposingUser + ", "
			+ "tags = " + this.tags + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class ProposingUser {
	
		public ProposingUser(){}
	
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
			return "ProposingUser [ "
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
