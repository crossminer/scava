/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
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

public class CommunicationChannelForumPost implements Serializable {

	private static final long serialVersionUID = 1L;

	private String subject;
	private String text;
	private String user;
	private Date date;
	private String postId;
	private String forumId;
	private String topicId;

	transient private CommunicationChannel communicationChannel;

	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}
	public void setCommunicationChannel(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
	}
	
	public String getPostId() {
		return postId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getForumId() {
		return forumId;
	}
	public void setForumId(String forumId) {
		this.forumId = forumId;
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
	public String getUser() {
		return user;
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
	
	public String getTopicId() {
		return topicId;
	}
	public void setTopicId(String topicId) {
		this.topicId = topicId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommunicationChannelForumPost) {

			if (this.postId != ((CommunicationChannelForumPost) obj).getPostId()) {
				return false;
			}
			return true;
		}

		return false;
	}


}
