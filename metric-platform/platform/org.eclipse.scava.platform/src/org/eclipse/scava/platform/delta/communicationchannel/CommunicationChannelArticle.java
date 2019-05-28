/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.communicationchannel;

import java.io.Serializable;
import java.util.Date;

import org.eclipse.scava.repository.model.CommunicationChannel;

public class CommunicationChannelArticle implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String articleId;
	private long articleNumber;
	transient private CommunicationChannel communicationChannel;
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

	public long getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(long articleNumber) {
		this.articleNumber = articleNumber;
	}

	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}

	public void setCommunicationChannel(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
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
