/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.eclipseforums;

import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelForumPost;


public class EclipseForumsPost extends CommunicationChannelForumPost {

	private static final long serialVersionUID = 1L;
	
	public EclipseForumsPost() {

	}
	private EclipseForumsTopic topic;
	private EclipseForumsForum forum;
	private String html_url;
	private String user_url;

	/**
	 * @return the topic
	 */
	public EclipseForumsTopic getTopic() {
		return topic;
	}
	/**
	 * @return the html_url
	 */
	public String getHtml_url() {
		return html_url;
	}

	/**
	 * @return the user_url
	 */
	public String getUser_url() {
		return user_url;
	}
	/**
	 * @param topic the topic to set
	 */
	public void setTopic(EclipseForumsTopic topic) {
		this.topic = topic;
	}
	/**
	 * @param html_url the html_url to set
	 */
	public void setHtml_url(String html_url) {
		this.html_url = html_url;
	}

	/**
	 * @param user_url the user_url to set
	 */
	public void setUser_url(String user_url) {
		this.user_url = user_url;
	}
	/**
	 * @return the forum
	 */
	public EclipseForumsForum getForum() {
		return forum;
	}
	/**
	 * @param forum the forum to set
	 */
	public void setForum(EclipseForumsForum forum) {
		this.forum = forum;
	}

	

	

}
