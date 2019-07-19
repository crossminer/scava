package org.eclipse.scava.platform.questionanswering.stackexchange.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class StackExchangePost
{
	private Long id;
	private Integer postType;
	private Long parentId;
	private Long acceptedAnswerId;
	private Date creationDate;
	private Integer score;
	private Long viewCount;
	private String body;
	private Long ownerUserId;
	private Long lastEditorUserId;
	private String lastEditorDisplayName;
	private Date lastEditDate;
	private Date lastActivityDate;
	private Date communityOwnedDate;
	private Date closedDate;
	private String title;
	private List<String> tags;
	private Integer answerCount;
	private Integer commentCount;
	private Integer favoriteCount;
	
	public StackExchangePost(String id, String postTypeId) {
		this.id=Long.parseLong(id);
		this.postType=Integer.parseInt(postTypeId);
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = Long.parseLong(parentId);
	}

	public Long getAcceptedAnswerId() {
		return acceptedAnswerId;
	}

	public void setAcceptedAnswerId(String acceptedAnswerId) {
		if(acceptedAnswerId == null || acceptedAnswerId.isEmpty())
			this.acceptedAnswerId =null;
		else
			this.acceptedAnswerId = Long.parseLong(acceptedAnswerId);
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = Integer.parseInt(score);
	}

	public Long getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = Long.parseLong(viewCount);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Long getOwnerUserId() {
		return ownerUserId;
	}

	public void setOwnerUserId(String ownerUserId) {
		if(ownerUserId == null || ownerUserId.isEmpty())
			this.ownerUserId =null;
		else
			this.ownerUserId = Long.parseLong(ownerUserId);
	}

	public Long getLastEditorUserId() {
		return lastEditorUserId;
	}

	public void setLastEditorUserId(String lastEditorUserId) {
		if(lastEditorUserId == null || lastEditorUserId.isEmpty())
			this.lastEditorUserId =null;
		else
			this.lastEditorUserId = Long.parseLong(lastEditorUserId);
	}

	public String getLastEditorDisplayName() {
		return lastEditorDisplayName;
	}

	public void setLastEditorDisplayName(String lastEditorDisplayName) {
		this.lastEditorDisplayName = lastEditorDisplayName;
	}

	public Date getLastEditDate() {
		return lastEditDate;
	}

	public void setLastEditDate(Date lastEditDate) {
			this.lastEditDate = lastEditDate;
	}

	public Date getLastActivityDate() {
		return lastActivityDate;
	}

	public void setLastActivityDate(Date lastActivityDate) {
			this.lastActivityDate = lastActivityDate;
	}

	public Date getCommunityOwnedDate() {
		return communityOwnedDate;
	}

	public void setCommunityOwnedDate(Date communityOwnedDate) {
			this.communityOwnedDate = communityOwnedDate;
	}

	public Date getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(Date closedDate) {
			this.closedDate = closedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(String tags) {
		tags=tags.replaceAll("<", "");
		this.tags = new ArrayList<String>(Arrays.asList(tags.split(">")));
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(String answerCount) {
		this.answerCount = Integer.parseInt(answerCount);
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(String commentCount) {
		this.commentCount = Integer.parseInt(commentCount);
	}

	public Integer getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(String favoriteCount) {
		if(favoriteCount == null || favoriteCount.isEmpty())
			this.favoriteCount = 0;
		else
			this.favoriteCount = Integer.parseInt(favoriteCount);
	}

	public Long getId() {
		return id;
	}

	public Integer getPostType() {
		return postType;
	}
	
	
	
	
}
