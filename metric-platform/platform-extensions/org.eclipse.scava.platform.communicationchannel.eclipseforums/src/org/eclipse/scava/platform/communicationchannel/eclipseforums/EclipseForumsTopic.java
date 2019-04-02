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


import java.util.Date;

import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelTopic;


public class EclipseForumsTopic extends CommunicationChannelTopic{
	
	private static final long serialVersionUID = 1L;


	private String topic_subject;
	private String topic_html_url;
	private String root_post_id;
	private int replies;
	private int views;
	private String first_post_unix_timestamp;
	private String last_post_unix_timestamp;
	private Date first_post_date;
	private Date last_post_date;
	
	

	/**
	 * @return the first_post_date
	 */
	public Date getFirst_post_date() {
		return first_post_date;
	}
	/**
	 * @return the last_post_date
	 */
	public Date getLast_post_date() {
		return last_post_date;
	}

	/**
	 * @param first_post_date the first_post_date to set
	 */
	public void setFirst_post_date(Date first_post_date) {
		this.first_post_date = first_post_date;
	}
	/**
	 * @param last_post_date the last_post_date to set
	 */
	public void setLast_post_date(Date last_post_date) {
		this.last_post_date = last_post_date;
	}
	/**
	 * @return the topic_subject
	 */
	public String getTopicSubject() {
		return topic_subject;
	}
	/**
	 * @return the topic_html_url
	 */
	public String getTopic_html_url() {
		return topic_html_url;
	}
	/**
	 * @return the root_post_id
	 */
	public String getRoot_post_id() {
		return root_post_id;
	}
	/**
	 * @return the replies
	 */
	public int getReplies() {
		return replies;
	}
	/**
	 * @return the views
	 */
	public int getViews() {
		return views;
	}
	/**
	 * @param topic_subject the topic_subject to set
	 */
	public void setTopic_subject(String topic_subject) {
		this.topic_subject = topic_subject;
	}
	/**
	 * @param topic_html_url the topic_html_url to set
	 */
	public void setTopic_html_url(String topic_html_url) {
		this.topic_html_url = topic_html_url;
	}
	/**
	 * @param root_post_id the root_post_id to set
	 */
	public void setRoot_post_id(String root_post_id) {
		this.root_post_id = root_post_id;
	}
	/**
	 * @param replies the replies to set
	 */
	public void setReplies(int replies) {
		this.replies = replies;
	}
	/**
	 * @param views the views to set
	 */
	public void setViews(int views) {
		this.views = views;
	}
	/**
	 * @return the first_post_unix_timestamp
	 */
	public String getFirst_post_unix_timestamp() {
		return first_post_unix_timestamp;
	}
	/**
	 * @return the last_post_unix_timestamp
	 */
	public String getLast_post_unix_timestamp() {
		return last_post_unix_timestamp;
	}
	/**
	 * @param first_post_unix_timestamp the first_post_unix_timestamp to set
	 */
	public void setFirst_post_unix_timestamp(String first_post_unix_timestamp) {
		this.first_post_unix_timestamp = first_post_unix_timestamp;
	}
	/**
	 * @param last_post_unix_timestamp the last_post_unix_timestamp to set
	 */
	public void setLast_post_unix_timestamp(String last_post_unix_timestamp) {
		this.last_post_unix_timestamp = last_post_unix_timestamp;
	}

}
