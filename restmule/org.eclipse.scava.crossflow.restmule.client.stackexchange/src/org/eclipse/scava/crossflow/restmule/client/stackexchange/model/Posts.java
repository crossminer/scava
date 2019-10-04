package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Posts {

	public Posts(){}

	@JsonProperty("comment_count") 
	private Integer commentCount;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("last_activity_date") 
	private Integer lastActivityDate;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("share_link") 
	private String shareLink;
	
	@JsonProperty("score") 
	private Integer score;
	
	@JsonProperty("downvoted") 
	private Boolean downvoted;
	
	@JsonProperty("body_markdown") 
	private String bodyMarkdown;
	
	@JsonProperty("down_vote_count") 
	private Integer downVoteCount;
	
	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("upvoted") 
	private Boolean upvoted;
	
	@JsonProperty("post_type") 
	private String postType;
	
	@JsonProperty("up_vote_count") 
	private Integer upVoteCount;
	
	@JsonProperty("last_edit_date") 
	private Integer lastEditDate;
	
	@JsonProperty("owner") 
	private Owner owner;
	
	@JsonProperty("last_editor") 
	private LastEditor lastEditor;
	
	@JsonProperty("comments") 
	private List<Object> comments = new ArrayList<Object>();
	
	public Integer getCommentCount() {
		return this.commentCount;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Integer getLastActivityDate() {
		return this.lastActivityDate;
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
	
	public String getShareLink() {
		return this.shareLink;
	}
	
	public Integer getScore() {
		return this.score;
	}
	
	public Boolean getDownvoted() {
		return this.downvoted;
	}
	
	public String getBodyMarkdown() {
		return this.bodyMarkdown;
	}
	
	public Integer getDownVoteCount() {
		return this.downVoteCount;
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
	
	public Integer getUpVoteCount() {
		return this.upVoteCount;
	}
	
	public Integer getLastEditDate() {
		return this.lastEditDate;
	}
	
	public Owner getOwner() {
		return this.owner;
	}
	
	public LastEditor getLastEditor() {
		return this.lastEditor;
	}
	
	public List<Object> getComments() {
		return this.comments;
	}
	
	@Override
	public String toString() {
		return "Posts [ "
			+ "commentCount = " + this.commentCount + ", "
			+ "link = " + this.link + ", "
			+ "lastActivityDate = " + this.lastActivityDate + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "body = " + this.body + ", "
			+ "title = " + this.title + ", "
			+ "shareLink = " + this.shareLink + ", "
			+ "score = " + this.score + ", "
			+ "downvoted = " + this.downvoted + ", "
			+ "bodyMarkdown = " + this.bodyMarkdown + ", "
			+ "downVoteCount = " + this.downVoteCount + ", "
			+ "postId = " + this.postId + ", "
			+ "upvoted = " + this.upvoted + ", "
			+ "postType = " + this.postType + ", "
			+ "upVoteCount = " + this.upVoteCount + ", "
			+ "lastEditDate = " + this.lastEditDate + ", "
			+ "owner = " + this.owner + ", "
			+ "lastEditor = " + this.lastEditor + ", "
			+ "comments = " + this.comments + ", "
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
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LastEditor {
	
		public LastEditor(){}
	
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
			return "LastEditor [ "
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
	
}	
