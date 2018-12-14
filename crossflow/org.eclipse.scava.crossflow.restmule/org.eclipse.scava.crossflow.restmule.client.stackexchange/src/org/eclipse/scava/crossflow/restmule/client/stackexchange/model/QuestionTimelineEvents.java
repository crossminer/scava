package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionTimelineEvents {

	public QuestionTimelineEvents(){}

	@JsonProperty("down_vote_count") 
	private Integer downVoteCount;
	
	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("revision_guid") 
	private String revisionGuid;
	
	@JsonProperty("comment_id") 
	private Integer commentId;
	
	@JsonProperty("question_id") 
	private Integer questionId;
	
	@JsonProperty("up_vote_count") 
	private Integer upVoteCount;
	
	@JsonProperty("timeline_type") 
	private String timelineType;
	
	@JsonProperty("owner") 
	private Owner owner;
	
	@JsonProperty("user") 
	private User user;
	
	public Integer getDownVoteCount() {
		return this.downVoteCount;
	}
	
	public Integer getPostId() {
		return this.postId;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getRevisionGuid() {
		return this.revisionGuid;
	}
	
	public Integer getCommentId() {
		return this.commentId;
	}
	
	public Integer getQuestionId() {
		return this.questionId;
	}
	
	public Integer getUpVoteCount() {
		return this.upVoteCount;
	}
	
	public String getTimelineType() {
		return this.timelineType;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public User getUser() {
		return this.user;
	}
	
	@Override
	public String toString() {
		return "QuestionTimelineEvents [ "
			+ "downVoteCount = " + this.downVoteCount + ", "
			+ "postId = " + this.postId + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "revisionGuid = " + this.revisionGuid + ", "
			+ "commentId = " + this.commentId + ", "
			+ "questionId = " + this.questionId + ", "
			+ "upVoteCount = " + this.upVoteCount + ", "
			+ "timelineType = " + this.timelineType + ", "
			+ "owner = " + this.owner + ", "
			+ "user = " + this.user + ", "
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
		private BadgeCountsInner badgeCountsInner;
		
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
		
		public BadgeCountsInner getBadgeCountsInner() {
			return this.badgeCountsInner;
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
				+ "badgeCountsInner = " + this.badgeCountsInner + ", "
				+ "]"; 
		}	
		@JsonIgnoreProperties(ignoreUnknown = true)
		public static class BadgeCountsInner {
		
			public BadgeCountsInner(){}
		
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
				return "BadgeCountsInner [ "
					+ "gold = " + this.gold + ", "
					+ "silver = " + this.silver + ", "
					+ "bronze = " + this.bronze + ", "
					+ "]"; 
			}	
		}
		
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
