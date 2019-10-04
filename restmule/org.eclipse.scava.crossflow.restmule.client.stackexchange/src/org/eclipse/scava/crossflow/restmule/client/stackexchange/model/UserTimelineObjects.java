package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserTimelineObjects {

	public UserTimelineObjects(){}

	@JsonProperty("items") 
	private Items items;
	
	public Items getItems() {
		return this.items;
	}
	
	@Override
	public String toString() {
		return "UserTimelineObjects [ "
			+ "items = " + this.items + ", "
			+ "]"; 
	}	
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Items {
	
		public Items(){}
	
		@JsonProperty("post_id") 
		private Integer postId;
		
		@JsonProperty("user_id") 
		private Integer userId;
		
		@JsonProperty("badge_id") 
		private Integer badgeId;
		
		@JsonProperty("link") 
		private String link;
		
		@JsonProperty("post_type") 
		private String postType;
		
		@JsonProperty("creation_date") 
		private Integer creationDate;
		
		@JsonProperty("detail") 
		private String detail;
		
		@JsonProperty("comment_id") 
		private Integer commentId;
		
		@JsonProperty("title") 
		private String title;
		
		@JsonProperty("suggested_edit_id") 
		private Integer suggestedEditId;
		
		@JsonProperty("timeline_type") 
		private String timelineType;
		
		public Integer getPostId() {
			return this.postId;
		}
		
		public Integer getUserId() {
			return this.userId;
		}
		
		public Integer getBadgeId() {
			return this.badgeId;
		}
		
		public String getLink() {
			return this.link;
		}
		
		public String getPostType() {
			return this.postType;
		}
		
		public Integer getCreationDate() {
			return this.creationDate;
		}
		
		public String getDetail() {
			return this.detail;
		}
		
		public Integer getCommentId() {
			return this.commentId;
		}
		
		public String getTitle() {
			return this.title;
		}
		
		public Integer getSuggestedEditId() {
			return this.suggestedEditId;
		}
		
		public String getTimelineType() {
			return this.timelineType;
		}
		
		@Override
		public String toString() {
			return "Items [ "
				+ "postId = " + this.postId + ", "
				+ "userId = " + this.userId + ", "
				+ "badgeId = " + this.badgeId + ", "
				+ "link = " + this.link + ", "
				+ "postType = " + this.postType + ", "
				+ "creationDate = " + this.creationDate + ", "
				+ "detail = " + this.detail + ", "
				+ "commentId = " + this.commentId + ", "
				+ "title = " + this.title + ", "
				+ "suggestedEditId = " + this.suggestedEditId + ", "
				+ "timelineType = " + this.timelineType + ", "
				+ "]"; 
		}	
	}
	
}	
