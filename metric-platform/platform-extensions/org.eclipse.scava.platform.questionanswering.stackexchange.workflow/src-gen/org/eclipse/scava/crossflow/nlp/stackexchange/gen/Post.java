package org.eclipse.scava.crossflow.nlp.stackexchange.gen;

import java.util.Date;
import java.util.List;

import org.eclipse.scava.crossflow.runtime.Job;

public class Post extends Job {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -227775123840975597L;

	public Post() {}
	
	public Post(Long postId, String repository, String project_name, Integer postType, Long parentId, Long acceptedAnswerId, Date creationDate, Integer score, Long viewCount, String body, Long ownerUserId, Long lastEditorUserId, String lastEditorDisplayName, Date lastEditDate, Date lastActivityDate, Date communityOwnedDate, Date closedDate, String title, List<String> tags, Integer answerCount, Integer commentCount, Integer favoriteCount, String plainText, boolean hasCode) {
		this.postId = postId;
		this.repository = repository;
		this.project_name = project_name;
		this.postType = postType;
		this.parentId = parentId;
		this.acceptedAnswerId = acceptedAnswerId;
		this.creationDate = creationDate;
		this.score = score;
		this.viewCount = viewCount;
		this.body = body;
		this.ownerUserId = ownerUserId;
		this.lastEditorUserId = lastEditorUserId;
		this.lastEditorDisplayName = lastEditorDisplayName;
		this.lastEditDate = lastEditDate;
		this.lastActivityDate = lastActivityDate;
		this.communityOwnedDate = communityOwnedDate;
		this.closedDate = closedDate;
		this.title = title;
		this.tags = tags;
		this.answerCount = answerCount;
		this.commentCount = commentCount;
		this.favoriteCount = favoriteCount;
		this.plainText = plainText;
		this.hasCode = hasCode;
	}
	
	public Post(Long postId, String repository, String project_name, Integer postType, Long parentId, Long acceptedAnswerId, Date creationDate, Integer score, Long viewCount, String body, Long ownerUserId, Long lastEditorUserId, String lastEditorDisplayName, Date lastEditDate, Date lastActivityDate, Date communityOwnedDate, Date closedDate, String title, List<String> tags, Integer answerCount, Integer commentCount, Integer favoriteCount, String plainText, boolean hasCode, Job correlation) {
		this.postId = postId;
		this.repository = repository;
		this.project_name = project_name;
		this.postType = postType;
		this.parentId = parentId;
		this.acceptedAnswerId = acceptedAnswerId;
		this.creationDate = creationDate;
		this.score = score;
		this.viewCount = viewCount;
		this.body = body;
		this.ownerUserId = ownerUserId;
		this.lastEditorUserId = lastEditorUserId;
		this.lastEditorDisplayName = lastEditorDisplayName;
		this.lastEditDate = lastEditDate;
		this.lastActivityDate = lastActivityDate;
		this.communityOwnedDate = communityOwnedDate;
		this.closedDate = closedDate;
		this.title = title;
		this.tags = tags;
		this.answerCount = answerCount;
		this.commentCount = commentCount;
		this.favoriteCount = favoriteCount;
		this.plainText = plainText;
		this.hasCode = hasCode;
		this.correlationId = correlation.getId();
	}
		
	protected Long postId;
	
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	
	public Long getPostId() {
		return postId;
	}
	
	protected String repository;
	
	public void setRepository(String repository) {
		this.repository = repository;
	}
	
	public String getRepository() {
		return repository;
	}
	
	protected String project_name;
	
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	
	public String getProject_name() {
		return project_name;
	}
	
	protected Integer postType;
	
	public void setPostType(Integer postType) {
		this.postType = postType;
	}
	
	public Integer getPostType() {
		return postType;
	}
	
	protected Long parentId;
	
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
	public Long getParentId() {
		return parentId;
	}
	
	protected Long acceptedAnswerId;
	
	public void setAcceptedAnswerId(Long acceptedAnswerId) {
		this.acceptedAnswerId = acceptedAnswerId;
	}
	
	public Long getAcceptedAnswerId() {
		return acceptedAnswerId;
	}
	
	protected Date creationDate;
	
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}
	
	protected Integer score;
	
	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Integer getScore() {
		return score;
	}
	
	protected Long viewCount;
	
	public void setViewCount(Long viewCount) {
		this.viewCount = viewCount;
	}
	
	public Long getViewCount() {
		return viewCount;
	}
	
	protected String body;
	
	public void setBody(String body) {
		this.body = body;
	}
	
	public String getBody() {
		return body;
	}
	
	protected Long ownerUserId;
	
	public void setOwnerUserId(Long ownerUserId) {
		this.ownerUserId = ownerUserId;
	}
	
	public Long getOwnerUserId() {
		return ownerUserId;
	}
	
	protected Long lastEditorUserId;
	
	public void setLastEditorUserId(Long lastEditorUserId) {
		this.lastEditorUserId = lastEditorUserId;
	}
	
	public Long getLastEditorUserId() {
		return lastEditorUserId;
	}
	
	protected String lastEditorDisplayName;
	
	public void setLastEditorDisplayName(String lastEditorDisplayName) {
		this.lastEditorDisplayName = lastEditorDisplayName;
	}
	
	public String getLastEditorDisplayName() {
		return lastEditorDisplayName;
	}
	
	protected Date lastEditDate;
	
	public void setLastEditDate(Date lastEditDate) {
		this.lastEditDate = lastEditDate;
	}
	
	public Date getLastEditDate() {
		return lastEditDate;
	}
	
	protected Date lastActivityDate;
	
	public void setLastActivityDate(Date lastActivityDate) {
		this.lastActivityDate = lastActivityDate;
	}
	
	public Date getLastActivityDate() {
		return lastActivityDate;
	}
	
	protected Date communityOwnedDate;
	
	public void setCommunityOwnedDate(Date communityOwnedDate) {
		this.communityOwnedDate = communityOwnedDate;
	}
	
	public Date getCommunityOwnedDate() {
		return communityOwnedDate;
	}
	
	protected Date closedDate;
	
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	
	public Date getClosedDate() {
		return closedDate;
	}
	
	protected String title;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	protected List<String> tags;
	
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	public List<String> getTags() {
		return tags;
	}
	
	protected Integer answerCount;
	
	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}
	
	public Integer getAnswerCount() {
		return answerCount;
	}
	
	protected Integer commentCount;
	
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	public Integer getCommentCount() {
		return commentCount;
	}
	
	protected Integer favoriteCount;
	
	public void setFavoriteCount(Integer favoriteCount) {
		this.favoriteCount = favoriteCount;
	}
	
	public Integer getFavoriteCount() {
		return favoriteCount;
	}
	
	protected String plainText;
	
	public void setPlainText(String plainText) {
		this.plainText = plainText;
	}
	
	public String getPlainText() {
		return plainText;
	}
	
	protected boolean hasCode;
	
	public void setHasCode(boolean hasCode) {
		this.hasCode = hasCode;
	}
	
	public boolean getHasCode() {
		return hasCode;
	}
	
	
	public Object[] toObjectArray() {
		Object[] ret = new Object[24];
	 	ret[0] = getPostId();
	 	ret[1] = getRepository();
	 	ret[2] = getProject_name();
	 	ret[3] = getPostType();
	 	ret[4] = getParentId();
	 	ret[5] = getAcceptedAnswerId();
	 	ret[6] = getCreationDate();
	 	ret[7] = getScore();
	 	ret[8] = getViewCount();
	 	ret[9] = getBody();
	 	ret[10] = getOwnerUserId();
	 	ret[11] = getLastEditorUserId();
	 	ret[12] = getLastEditorDisplayName();
	 	ret[13] = getLastEditDate();
	 	ret[14] = getLastActivityDate();
	 	ret[15] = getCommunityOwnedDate();
	 	ret[16] = getClosedDate();
	 	ret[17] = getTitle();
	 	ret[18] = getTags();
	 	ret[19] = getAnswerCount();
	 	ret[20] = getCommentCount();
	 	ret[21] = getFavoriteCount();
	 	ret[22] = getPlainText();
	 	ret[23] = getHasCode();
		return ret;
	}
	
	public String toString() {
		return "Post (" + " postId=" + postId + " repository=" + repository + " project_name=" + project_name + " postType=" + postType + " parentId=" + parentId + " acceptedAnswerId=" + acceptedAnswerId + " creationDate=" + creationDate + " score=" + score + " viewCount=" + viewCount + " body=" + body + " ownerUserId=" + ownerUserId + " lastEditorUserId=" + lastEditorUserId + " lastEditorDisplayName=" + lastEditorDisplayName + " lastEditDate=" + lastEditDate + " lastActivityDate=" + lastActivityDate + " communityOwnedDate=" + communityOwnedDate + " closedDate=" + closedDate + " title=" + title + " tags=" + tags + " answerCount=" + answerCount + " commentCount=" + commentCount + " favoriteCount=" + favoriteCount + " plainText=" + plainText + " hasCode=" + hasCode + " id=" + id + " correlationId=" + correlationId + " destination=" + destination + ")";
	}
	
}

