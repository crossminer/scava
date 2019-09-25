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

public class CommentDocument extends DocumentAbstract {

	private String comment_id;
	private String body;
	private String bug_id;
	private String project_name;
	private String creator;
	private Date created_at;
	// NLP
	private List<String> emotional_dimension = new ArrayList<>();
	private String sentiment;
	private String plain_text;
	private String request_reply_classification;
	private String content_class;
	private Boolean contains_code;
	private ReferringTo referring_to;
	

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
	
	public void addBugReference(String bugReference)
	{
		if(referring_to==null)
			referring_to=new ReferringTo();
		referring_to.addBug(bugReference);
	}
	
	public void addCommitReference(String commitReference)
	{
		if(referring_to==null)
			referring_to=new ReferringTo();
		referring_to.addCommit(commitReference);
	}
	
	public ReferringTo getReferring_to() {
		return referring_to;
	}
	
	public class ReferringTo
	{
		List<String> commits;
		List<String> bugs;
		
		public void addCommit(String commitReference)
		{
			if(commits==null)
				commits=new ArrayList<String>();
			commits.add(commitReference);
		}
		
		public void addBug(String bugReference)
		{
			if(bugs==null)
				bugs=new ArrayList<String>();
			bugs.add(bugReference);
		}
		
		public List<String> getBugs() {
			return bugs;
		}
		
		public List<String> getCommits() {
			return commits;
		}
	}

}
