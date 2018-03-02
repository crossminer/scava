/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.severityclassifier.opennlptartarus.libsvm;


public class ClassifierMessage {
	
	private String bugTrackerId;
	private String bugId;
	private String commentId;
	
	private String newsgroupName;
	private int threadId;
	private int articleNumber;
	private String subject;

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
		else if ((newsgroupName!=null)&&(threadId!=0)) 
			composedId = newsgroupName+"#"+threadId;
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

	public int getThreadId() {
		return threadId;
	}
	
	public void setThreadId(int threadId) {
		this.threadId = threadId;
		if (composedId!=null) setComposedId();
	}
	
	public int getArticleNumber() {
		return articleNumber;
	}
	
	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
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
					", articleNumber=" + articleNumber + ", subject=" + subject + "]";
		else
			return "ClassifierMessage "+ "[bugTrackerId=" + bugTrackerId + 
					", bugId=" + bugId + ", commentId=" + commentId + ", subject=" + subject + "]";
			
	}

}
