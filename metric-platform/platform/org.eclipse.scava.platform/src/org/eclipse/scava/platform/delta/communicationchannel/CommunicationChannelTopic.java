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

public class CommunicationChannelTopic implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String forum_id;
	private String topic_id;
	private String name;
	private String description;
	private String url;
	private Date creation_date;
	

	transient private CommunicationChannel communicationChannel;


	public String getFourum_id() {
		return forum_id;
	}


	public void setForum_id(String forum_id) {
		this.forum_id = forum_id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public Date getCreation_date() {
		return creation_date;
	}


	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}


	public CommunicationChannel getCommunicationChannel() {
		return communicationChannel;
	}


	public void setCommunicationChannel(CommunicationChannel communicationChannel) {
		this.communicationChannel = communicationChannel;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	/**
	 * @return the forum_id
	 */
	public String getForum_id() {
		return forum_id;
	}


	/**
	 * @return the topic_id
	 */
	public String getTopic_id() {
		return topic_id;
	}


	/**
	 * @param topic_id the topic_id to set
	 */
	public void setTopic_id(String topic_id) {
		this.topic_id = topic_id;
	}
	
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CommunicationChannelTopic) {

			if (this.topic_id != ((CommunicationChannelTopic) obj).getTopic_id()) {
				return false;
			}
			return true;
		}
		
		return false;
	}
	

}
