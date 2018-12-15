package org.eclipse.scava.crossflow.restmule.client.stackexchange.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReputationResponse {

	public ReputationResponse(){}

	@JsonProperty("vote_type") 
	private String voteType;
	
	@JsonProperty("post_id") 
	private Integer postId;
	
	@JsonProperty("user_id") 
	private Integer userId;
	
	@JsonProperty("reputation_change") 
	private Integer reputationChange;
	
	@JsonProperty("post_type") 
	private String postType;
	
	@JsonProperty("on_date") 
	private Integer onDate;
	
	public String getVoteType() {
		return this.voteType;
	}
	
	public Integer getPostId() {
		return this.postId;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public Integer getReputationChange() {
		return this.reputationChange;
	}
	
	public String getPostType() {
		return this.postType;
	}
	
	public Integer getOnDate() {
		return this.onDate;
	}
	
	@Override
	public String toString() {
		return "ReputationResponse [ "
			+ "voteType = " + this.voteType + ", "
			+ "postId = " + this.postId + ", "
			+ "userId = " + this.userId + ", "
			+ "reputationChange = " + this.reputationChange + ", "
			+ "postType = " + this.postType + ", "
			+ "onDate = " + this.onDate + ", "
			+ "]"; 
	}	
}	
