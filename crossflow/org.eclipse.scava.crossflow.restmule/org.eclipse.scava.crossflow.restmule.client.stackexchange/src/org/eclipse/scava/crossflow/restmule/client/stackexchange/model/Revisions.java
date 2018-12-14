package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Revisions {

	public Revisions(){}

	@JsonProperty("revision_number") 
	private Integer revisionNumber;
	
	@JsonProperty("creation_date") 
	private Integer creationDate;
	
	@JsonProperty("revision_guid") 
	private String revisionGuid;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("is_rollback") 
	private Boolean isRollback;
	
	@JsonProperty("title") 
	private String title;
	
	@JsonProperty("revision_type") 
	private String revisionType;
	
	@JsonProperty("last_body") 
	private String lastBody;
	
	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("comment") 
	private String comment;
	
	@JsonProperty("post_type") 
	private String postType;
	
	@JsonProperty("last_title") 
	private String lastTitle;
	
	@JsonProperty("set_community_wiki") 
	private Boolean setCommunityWiki;
	
	@JsonProperty("user") 
	private User user;
	
	@JsonProperty("last_tags") 
	private List<Object> lastTags = new ArrayList<Object>();
	
	@JsonProperty("tags") 
	private List<Object> tags = new ArrayList<Object>();
	
	public Integer getRevisionNumber() {
		return this.revisionNumber;
	}
	
	public Integer getCreationDate() {
		return this.creationDate;
	}
	
	public String getRevisionGuid() {
		return this.revisionGuid;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public Boolean getIsRollback() {
		return this.isRollback;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public String getRevisionType() {
		return this.revisionType;
	}
	
	public String getLastBody() {
		return this.lastBody;
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
	
	public String getLastTitle() {
		return this.lastTitle;
	}
	
	public Boolean getSetCommunityWiki() {
		return this.setCommunityWiki;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public List<Object> getLastTags() {
		return this.lastTags;
	}
	
	public List<Object> getTags() {
		return this.tags;
	}
	
	@Override
	public String toString() {
		return "Revisions [ "
			+ "revisionNumber = " + this.revisionNumber + ", "
			+ "creationDate = " + this.creationDate + ", "
			+ "revisionGuid = " + this.revisionGuid + ", "
			+ "body = " + this.body + ", "
			+ "isRollback = " + this.isRollback + ", "
			+ "title = " + this.title + ", "
			+ "revisionType = " + this.revisionType + ", "
			+ "lastBody = " + this.lastBody + ", "
			+ "postId = " + this.postId + ", "
			+ "comment = " + this.comment + ", "
			+ "postType = " + this.postType + ", "
			+ "lastTitle = " + this.lastTitle + ", "
			+ "setCommunityWiki = " + this.setCommunityWiki + ", "
			+ "user = " + this.user + ", "
			+ "lastTags = " + this.lastTags + ", "
			+ "tags = " + this.tags + ", "
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
