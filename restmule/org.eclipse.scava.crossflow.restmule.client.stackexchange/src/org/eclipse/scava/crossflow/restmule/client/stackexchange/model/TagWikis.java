package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TagWikis {

	public TagWikis(){}

	@JsonProperty("tag_name") 
	private String tagName;
	
	@JsonProperty("body") 
	private String body;
	
	@JsonProperty("excerpt") 
	private String excerpt;
	
	@JsonProperty("excerpt_last_edit_date") 
	private Integer excerptLastEditDate;
	
	@JsonProperty("body_last_edit_date") 
	private Integer bodyLastEditDate;
	
	@JsonProperty("last_body_editor") 
	private LastBodyEditor lastBodyEditor;
	
	@JsonProperty("last_excerpt_editor") 
	private LastExcerptEditor lastExcerptEditor;
	
	public String getTagName() {
		return this.tagName;
	}
	
	public String getBody() {
		return this.body;
	}
	
	public String getExcerpt() {
		return this.excerpt;
	}
	
	public Integer getExcerptLastEditDate() {
		return this.excerptLastEditDate;
	}
	
	public Integer getBodyLastEditDate() {
		return this.bodyLastEditDate;
	}
	
	public LastBodyEditor getLastBodyEditor() {
		return this.lastBodyEditor;
	}
	
	public LastExcerptEditor getLastExcerptEditor() {
		return this.lastExcerptEditor;
	}
	
	@Override
	public String toString() {
		return "TagWikis [ "
			+ "tagName = " + this.tagName + ", "
			+ "body = " + this.body + ", "
			+ "excerpt = " + this.excerpt + ", "
			+ "excerptLastEditDate = " + this.excerptLastEditDate + ", "
			+ "bodyLastEditDate = " + this.bodyLastEditDate + ", "
			+ "lastBodyEditor = " + this.lastBodyEditor + ", "
			+ "lastExcerptEditor = " + this.lastExcerptEditor + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class LastBodyEditor {
	
		public LastBodyEditor(){}
	
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
			return "LastBodyEditor [ "
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
	public static class LastExcerptEditor {
	
		public LastExcerptEditor(){}
	
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
			return "LastExcerptEditor [ "
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
