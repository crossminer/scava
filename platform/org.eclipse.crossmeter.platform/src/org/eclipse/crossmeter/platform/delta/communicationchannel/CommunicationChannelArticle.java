/*******************************************************************************
 * Copyright (c) 2014 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.crossmeter.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.Date;

import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;

public class CommunicationChannelArticle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String articleId;
	private int articleNumber;
	transient private NntpNewsGroup newsgroup;
	private String messageThreadId;
	private String subject;
	private String text;
	private String user;
	private Date date;
	
	private String[] references;
	
	
	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

	public NntpNewsGroup getCommunicationChannel() {
		return newsgroup;
	}

	public void setNewsgroup(NntpNewsGroup communicationChannel) {
		this.newsgroup = communicationChannel;
	}

	public String getMessageThreadId() {
		return messageThreadId;
	}

	public void setMessageThreadId(String messageThreadId) {
		this.messageThreadId = messageThreadId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getUser() {
		return user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String[] getReferences() {
		return references;
	}

	public void setReferences(String[] references) {
		this.references = references;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommunicationChannelArticle) {
//			if (!this.newsgroup.equals(((CommunicationChannelArticle) obj).getCommunicationChannel())) {
//				return false;
//			} 
			if (this.articleNumber != ((CommunicationChannelArticle) obj).getArticleNumber()) {
				return false;
			}
			return true;
		}
		
		return false;
	}

}
