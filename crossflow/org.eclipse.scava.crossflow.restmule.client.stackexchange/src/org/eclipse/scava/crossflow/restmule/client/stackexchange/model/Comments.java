package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Comments {

	public Comments(){}

	@JsonProperty("edited") 
	private Boolean edited;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("can_flag") 
	private Boolean canFlag;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("comment_id") 
	private Integer commentId;
	
	@JsonProperty("score") 
	private Integer score;
	
	@JsonProperty("body_markdown") 
	private String bodyMarkdown;
	
	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("upvoted") 
	private Boolean upvoted;
	
	@JsonProperty("post_type") 
	private String postType;
	
	@JsonProperty("owner") 
	private Owner owner;
	
	@JsonProperty("reply_to_user") 
	private ReplyToUser replyToUser;
	
	public Boolean getEdited() {
		return this.edited;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Boolean getCanFlag() {
		return this.canFlag;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public Integer getCommentId() {
		return this.commentId;
	}
	
	public Integer getScore() {
		return this.score;
	}
	
	public String getBodyMarkdown() {
		return this.bodyMarkdown;
	}
	
	public Integer getPostId() {
		return this.postId;
	}
	
	public Boolean getUpvoted() {
		return this.upvoted;
	}
	
	public String getPostType() {
		return this.postType;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public ReplyToUser getReplyToUser() {
		return this.replyToUser;
	}
	
	@Override
	public String toString() {
		return "Comments [ "
			+ "edited = " + this.edited + ", "
			+ "link = " + this.link + ", "
			+ "canFlag = " + this.canFlag + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "body = " + this.body + ", "
			+ "commentId = " + this.commentId + ", "
			+ "score = " + this.score + ", "
			+ "bodyMarkdown = " + this.bodyMarkdown + ", "
			+ "postId = " + this.postId + ", "
			+ "upvoted = " + this.upvoted + ", "
			+ "postType = " + this.postType + ", "
			+ "owner = " + this.owner + ", "
			+ "replyToUser = " + this.replyToUser + ", "
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
	public static class ReplyToUser {
	
		public ReplyToUser(){}
	
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
			return "ReplyToUser [ "
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
