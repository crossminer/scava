package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Answers {

	public Answers(){}

	@JsonProperty("comment_count") 
	private Integer commentCount;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("locked_date") 
	private Integer lockedDate;
	
	@JsonProperty("can_flag") 
	private Boolean canFlag;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("answer_id") 
	private Integer answerId;
	
	@JsonProperty("community_owned_date") 
	private Integer communityOwnedDate;
	
	@JsonProperty("score") 
	private Integer score;
	
	@JsonProperty("downvoted") 
	private Boolean downvoted;
	
	@JsonProperty("down_vote_count") 
	private Integer downVoteCount;
	
	@JsonProperty("awarded_bounty_amount") 
	private Integer awardedBountyAmount;
	
	@JsonProperty("is_accepted") 
	private Boolean isAccepted;
	
	@JsonProperty("accepted") 
	private Boolean accepted;
	
	@JsonProperty("last_activity_date") 
	private Integer lastActivityDate;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("question_id") 
	private Integer questionId;
	
	@JsonProperty("share_link") 
	private String shareLink;
	
	@JsonProperty("body_markdown") 
	private String bodyMarkdown;
	
	@JsonProperty("upvoted") 
	private Boolean upvoted;
	
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
	
	@JsonProperty("awarded_bounty_users") 
	private List<Object> awardedBountyUsers = new ArrayList<Object>();
	
	@JsonProperty("tags") 
	private List<Object> tags = new ArrayList<Object>();
	
	public Integer getCommentCount() {
		return this.commentCount;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public Integer getLockedDate() {
		return this.lockedDate;
	}
	
	public Boolean getCanFlag() {
		return this.canFlag;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Integer getAnswerId() {
		return this.answerId;
	}
	
	public Integer getCommunityOwnedDate() {
		return this.communityOwnedDate;
	}
	
	public Integer getScore() {
		return this.score;
	}
	
	public Boolean getDownvoted() {
		return this.downvoted;
	}
	
	public Integer getDownVoteCount() {
		return this.downVoteCount;
	}
	
	public Integer getAwardedBountyAmount() {
		return this.awardedBountyAmount;
	}
	
	public Boolean getIsAccepted() {
		return this.isAccepted;
	}
	
	public Boolean getAccepted() {
		return this.accepted;
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
	
	public String getShareLink() {
		return this.shareLink;
	}
	
	public String getBodyMarkdown() {
		return this.bodyMarkdown;
	}
	
	public Boolean getUpvoted() {
		return this.upvoted;
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
	
	public List<Object> getAwardedBountyUsers() {
		return this.awardedBountyUsers;
	}
	
	public List<Object> getTags() {
		return this.tags;
	}
	
	@Override
	public String toString() {
		return "Answers [ "
			+ "commentCount = " + this.commentCount + ", "
			+ "link = " + this.link + ", "
			+ "lockedDate = " + this.lockedDate + ", "
			+ "canFlag = " + this.canFlag + ", "
			+ "body = " + this.body + ", "
			+ "title = " + this.title + ", "
			+ "answerId = " + this.answerId + ", "
			+ "communityOwnedDate = " + this.communityOwnedDate + ", "
			+ "score = " + this.score + ", "
			+ "downvoted = " + this.downvoted + ", "
			+ "downVoteCount = " + this.downVoteCount + ", "
			+ "awardedBountyAmount = " + this.awardedBountyAmount + ", "
			+ "isAccepted = " + this.isAccepted + ", "
			+ "accepted = " + this.accepted + ", "
			+ "lastActivityDate = " + this.lastActivityDate + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "questionId = " + this.questionId + ", "
			+ "shareLink = " + this.shareLink + ", "
			+ "bodyMarkdown = " + this.bodyMarkdown + ", "
			+ "upvoted = " + this.upvoted + ", "
			+ "upVoteCount = " + this.upVoteCount + ", "
			+ "lastEditDate = " + this.lastEditDate + ", "
			+ "owner = " + this.owner + ", "
			+ "lastEditor = " + this.lastEditor + ", "
			+ "comments = " + this.comments + ", "
			+ "awardedBountyUsers = " + this.awardedBountyUsers + ", "
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
			return "LastEditor [ "
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
