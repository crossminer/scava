/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.communicationchannels.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Document{
	 String uid;
	 String project_name;
	 String message_body;
	 String subject;
	 String creator;
	 Date created_at;
	
	// NLP
	 private List<String> emotional_dimension = new ArrayList<>();
	 private String sentiment;
	 private String plain_text;
	 private String request_reply_classification;
	 private String content_class;
	 private Boolean contains_code;
	
	public String getUid() {
		return uid;
	}
	public String getProject_name() {
		return project_name;
	}
	public String getMessage_body() {
		return message_body;
	}
	public String getCreator() {
		return creator;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setUiid(String uid) {
		this.uid = uid;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public List<String> getEmotional_dimension() {
		return emotional_dimension;
	}
	public String getSentiment() {
		return sentiment;
	}
	public String getPlain_text() {
		return plain_text;
	}
	public String getRequest_reply_classification() {
		return request_reply_classification;
	}
	public String getContent_class() {
		return content_class;
	}
	public Boolean getContains_code() {
		return contains_code;
	}
	public void addEmotional_dimension(String emotional_dimension) {
		this.emotional_dimension.add(emotional_dimension);
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public void setPlain_text(String plain_text) {
		this.plain_text = plain_text;
	}
	public void setRequest_reply_classification(String request_reply_classification) {
		this.request_reply_classification = request_reply_classification;
	}
	public void setContent_class(String content_class) {
		this.content_class = content_class;
	}
	public void setContains_code(Boolean contains_code) {
		this.contains_code = contains_code;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}