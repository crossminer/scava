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

import java.io.Serializable;
import java.util.Date;


public class EclipseForumsForum implements Serializable{
		
	
	private static final long serialVersionUID = 1L;
	String description;
	String forum_id;
	String name;
	String url;
	Date creation_date;
	int topic_count;
	int post_count;
	
	public int getTopic_count() {
		return topic_count;
	}
	public void setTopic_count(int topic_count) {
		this.topic_count = topic_count;
	}
	public int getPost_count() {
		return post_count;
	}
	public void setPost_count(int post_count) {
		this.post_count = post_count;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the forum_id
	 */
	public String getForum_id() {
		return forum_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @return the creation_date
	 */
	public Date getCreation_date() {
		return creation_date;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @param forum_id the forum_id to set
	 */
	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @param creation_date the creation_date to set
	 */
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

}
