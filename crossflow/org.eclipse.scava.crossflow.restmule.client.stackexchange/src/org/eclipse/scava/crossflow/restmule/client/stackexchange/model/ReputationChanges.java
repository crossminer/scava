package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReputationChanges {

	public ReputationChanges(){}

	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("vote_type") 
	private String voteType;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("reputation_change") 
	private Integer reputationChange;
	
	@JsonProperty("link") 
	private String link;
	
	@JsonProperty("post_type") 
	private String postType;
	
	@JsonProperty("on_date") 
	private Integer onDate;
	
	@JsonProperty("title") 
	private String title;
	
	public Integer getPostId() {
		return this.postId;
	}
	
	public String getVoteType() {
		return this.voteType;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public Integer getReputationChange() {
		return this.reputationChange;
	}
	
	public String getLink() {
		return this.link;
	}
	
	public String getPostType() {
		return this.postType;
	}
	
	public Integer getOnDate() {
		return this.onDate;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	@Override
	public String toString() {
		return "ReputationChanges [ "
			+ "postId = " + this.postId + ", "
			+ "voteType = " + this.voteType + ", "
			+ "userId = " + this.userId + ", "
			+ "reputationChange = " + this.reputationChange + ", "
			+ "link = " + this.link + ", "
			+ "postType = " + this.postType + ", "
			+ "onDate = " + this.onDate + ", "
			+ "title = " + this.title + ", "
			+ "]"; 
	}	
}	
