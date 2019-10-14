/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
//Adrián was here
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;


public class ClassifierMessage {
	
	private String bugTrackerId;
	private String bugId;
	private String commentId;
	
	private String newsgroupName;
	private String threadId;
	private String articleId;
	private String subject;
	
	private String forumId;
	private String topicId;
	private String postId;

	private String text;
	private String composedId;
	
	public ClassifierMessage() {
	}
	
	public String getComposedId() {
		if (composedId==null) setComposedId();
		return composedId;
	}

	private void setComposedId() {
		if ((bugTrackerId!=null)&&(bugId!=null))
			composedId = bugTrackerId+"#"+bugId;
		else if ((newsgroupName!=null)&&(threadId!=null)) 
			composedId = newsgroupName+"#"+threadId;
		else if ((forumId != null) && (topicId != null))
			composedId = forumId + "#" + topicId;
		else {
			System.err.println("Unable to compose ID");
		}
		toString();
	}

	public String getBugTrackerId() {
		return bugTrackerId;
	}
	
	public void setBugTrackerId(String bugTrackerId) {
		this.bugTrackerId = bugTrackerId;
		if (composedId!=null) setComposedId();
	}
	
	public String getNewsgroupName() {
		return newsgroupName;
	}

	public void setNewsgroupName(String newsgroupName) {
		this.newsgroupName = newsgroupName;
		if (composedId!=null) setComposedId();
	}
	
	public String getForumId()
	{
		return forumId;
	}
	
	public void setForumId(String forumId)
	{
		this.forumId = forumId;
		if (composedId!=null) setComposedId();
	}
	
	public String getTopicId()
	{
		return topicId;
	}
	
	public void setTopicId(String topicId)
	{
		this.topicId = topicId;
		if (composedId!=null) setComposedId();
	}

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
		if (composedId!=null) setComposedId();
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
		if (composedId!=null) setComposedId();
	}
	
	public String getPostId()
	{
		return postId;
	}
	
	public void setPostId(String postId)
	{
		this.postId = postId;
		if (composedId!=null) setComposedId();
	}

	public String getThreadId() {
		return threadId;
	}
	
	public void setThreadId(String threadId) {
		this.threadId = threadId;
		if (composedId!=null) setComposedId();
	}
	
	public String getArticleId() {
		return articleId;
	}
	
	public void setArticleId(String articleId) {
		this.articleId = articleId;
		if (composedId!=null) setComposedId();
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		if (newsgroupName!=null)
			return "ClassifierMessage " + "[newsgroupName=" + newsgroupName + ", threadId=" + threadId + 
					", articleNumber=" + articleId + ", subject=" + subject + "]";
		else if(bugTrackerId!=null)
			return "ClassifierMessage "+ "[bugTrackerId=" + bugTrackerId + 
					", bugId=" + bugId + ", commentId=" + commentId + ", subject=" + subject + "]";
		else
			return "ClassifierMessage "+ "[forumId=" + forumId + 
					", topicId=" + topicId + ", postId=" + postId + ", subject=" + subject + "]";
			
	}

}
