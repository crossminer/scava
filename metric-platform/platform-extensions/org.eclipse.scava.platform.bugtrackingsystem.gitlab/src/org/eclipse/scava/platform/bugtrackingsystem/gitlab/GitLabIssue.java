/*******************************************************************************
* Copyright (c) 2019 Edge Hill University
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
* 
* SPDX-License-Identifier: EPL-2.0
******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.gitlab;

import java.util.ArrayList;
import java.util.Date;

import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Assignee;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Author;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Issue;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Milestone;
import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Time_stats;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


public class GitLabIssue extends BugTrackingSystemBug {
	
	private static final long serialVersionUID = 1L;

		private String iid;
		private String project_id;
		private String description;
		private Date closed_at = null;
		private Date updated_at = null;
		private String closed_by = null;
		ArrayList < Object > labels = new ArrayList < Object > ();
		Milestone MilestoneObject;
		ArrayList < Object > assignees = new ArrayList < Object > ();
		Author AuthorObject;
		Assignee AssigneeObject;
		private int user_notes_count;
		private int upvotes;
		private int downvotes;
		private String due_date;
		private boolean confidential;
		private String discussion_locked = null;
		private String web_url;
		Time_stats Time_statsObject;
		private String weight = null;
		

		public GitLabIssue(Issue issue, GitLabTracker gitlabTracker) {
			this.bugId = issue.getId();
			this.status = issue.getState();
			this.creator = issue.getAuthor().getUsername();
			this.creationTime = convertStringToDate(issue.getCreated_at());
			this.summary = issue.getTitle();
			this.bugTrackingSystem = gitlabTracker;
		}
	
	/**
	 * @return the iid
	 */
	public String getIid() {
		return iid;
	}

	/**
	 * @return the project_id
	 */
	public String getProject_id() {
		return project_id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the closed_at
	 */
	public Date getClosed_at() {
		return closed_at;
	}

	/**
	 * @return the updated_at
	 */
	public Date getUpdated_at() {
		return updated_at;
	}

	/**
	 * @return the closed_by
	 */
	public String getClosed_by() {
		return closed_by;
	}

	/**
	 * @return the labels
	 */
	public ArrayList<Object> getLabels() {
		return labels;
	}

	/**
	 * @return the milestoneObject
	 */
	public Milestone getMilestoneObject() {
		return MilestoneObject;
	}

	/**
	 * @return the assignees
	 */
	public ArrayList<Object> getAssignees() {
		return assignees;
	}

	/**
	 * @return the authorObject
	 */
	public Author getAuthorObject() {
		return AuthorObject;
	}

	/**
	 * @return the assigneeObject
	 */
	public Assignee getAssigneeObject() {
		return AssigneeObject;
	}

	/**
	 * @return the user_notes_count
	 */
	public int getUser_notes_count() {
		return user_notes_count;
	}

	/**
	 * @return the upvotes
	 */
	public int getUpvotes() {
		return upvotes;
	}

	/**
	 * @return the downvotes
	 */
	public int getDownvotes() {
		return downvotes;
	}

	/**
	 * @return the due_date
	 */
	public String getDue_date() {
		return due_date;
	}

	/**
	 * @return the confidential
	 */
	public boolean isConfidential() {
		return confidential;
	}

	/**
	 * @return the discussion_locked
	 */
	public String getDiscussion_locked() {
		return discussion_locked;
	}

	/**
	 * @return the web_url
	 */
	public String getWeb_url() {
		return web_url;
	}

	/**
	 * @return the time_statsObject
	 */
	public Time_stats getTime_statsObject() {
		return Time_statsObject;
	}

	/**
	 * @return the weight
	 */
	public String getWeight() {
		return weight;
	}

	/**
	 * @param iid the iid to set
	 */
	public void setIid(String iid) {
		this.iid = iid;
	}

	/**
	 * @param project_id the project_id to set
	 */
	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param closed_at the closed_at to set
	 */
	public void setClosed_at(String closed_at) {
		this.closed_at = convertStringToDate(closed_at);
	}

	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		
		
		this.updated_at = convertStringToDate(updated_at);
	}

	/**
	 * @param closed_by the closed_by to set
	 */
	public void setClosed_by(String closed_by) {
		this.closed_by = closed_by;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(ArrayList<Object> labels) {
		this.labels = labels;
	}

	/**
	 * @param milestoneObject the milestoneObject to set
	 */
	public void setMilestoneObject(Milestone milestoneObject) {
		MilestoneObject = milestoneObject;
	}

	/**
	 * @param assignees the assignees to set
	 */
	public void setAssignees(ArrayList<Object> assignees) {
		this.assignees = assignees;
	}

	/**
	 * @param authorObject the authorObject to set
	 */
	public void setAuthorObject(Author authorObject) {
		AuthorObject = authorObject;
	}

	/**
	 * @param assigneeObject the assigneeObject to set
	 */
	public void setAssigneeObject(Assignee assigneeObject) {
		AssigneeObject = assigneeObject;
	}

	/**
	 * @param user_notes_count the user_notes_count to set
	 */
	public void setUser_notes_count(int user_notes_count) {
		this.user_notes_count = user_notes_count;
	}

	/**
	 * @param upvotes the upvotes to set
	 */
	public void setUpvotes(int upvotes) {
		this.upvotes = upvotes;
	}

	/**
	 * @param downvotes the downvotes to set
	 */
	public void setDownvotes(int downvotes) {
		this.downvotes = downvotes;
	}

	/**
	 * @param due_date the due_date to set
	 */
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	/**
	 * @param confidential the confidential to set
	 */
	public void setConfidential(boolean confidential) {
		this.confidential = confidential;
	}

	/**
	 * @param discussion_locked the discussion_locked to set
	 */
	public void setDiscussion_locked(String discussion_locked) {
		this.discussion_locked = discussion_locked;
	}

	/**
	 * @param web_url the web_url to set
	 */
	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}

	/**
	 * @param time_statsObject the time_statsObject to set
	 */
	public void setTime_statsObject(Time_stats time_statsObject) {
		Time_statsObject = time_statsObject;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	
	//Utility Method
	
	private static java.util.Date convertStringToDate(String isoDate) {
		
		isoDate = isoDate.replaceAll("\"", "");
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}

}
