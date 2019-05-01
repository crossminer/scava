/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.indexing.bugs.document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDocument {

	private String comment_id;
	private String body;
	// NLP
	private List<String> emotional_dimension = new ArrayList<>();
	private String sentiment;
	private String plain_text;
	private String request_reply_classification;
	private String content_class;
	private Boolean contains_code;
	private String bug_id;
	private String project_name;
	private String creator;
	private Date created_at;
	private String uid;

	public CommentDocument(String uid, String commentId, String bugId, String projectName, String body,
			String creator, Date createdAt) {
		this.uid = uid;
		this.comment_id = commentId;
		this.bug_id = bugId;
		this.project_name = projectName;
		this.body = body;
		this.creator = creator;
		this.created_at = createdAt;
	}

	public String getComment_Id() {
		return comment_id;
	}

	public String getComment_body() {
		return body;
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

	public String getBug_id() {
		return bug_id;
	}

	public String getProject_name() {
		return project_name;
	}

	public String getCreator() {
		return creator;
	}

	public Date getCreated_at() {
		return created_at;
	}

	public String getUid() {
		return uid;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public void setComment_body(String comment_body) {
		this.body = comment_body;
	}

	public void setEmotional_dimension(List<String> emotional_dimension) {
		this.emotional_dimension = emotional_dimension;
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

	public void setBug_id(String bug_id) {
		this.bug_id = bug_id;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

}
