/*******************************************************************************
* Copyright (c) 2019 Edge Hill University
* 
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
* 
* SPDX-License-Identifier: EPL-2.0
*******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.gitlab;

import java.util.Date;

import org.eclipse.scava.platform.bugtrackingsystem.gitlab.model.Comment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.repository.model.bts.gitlab.GitLabTracker;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;


public class GitLabComment extends BugTrackingSystemComment {

	private static final long serialVersionUID = 1L;
	
	private String bugIID;
	private Date updated_at;
		
	public GitLabComment (Comment comment, GitLabTracker gitLabTracker, String id, String iid) {
		this.commentId = comment.getId();
		this.creator = comment.getAuthor().getUsername();
		this.creationTime = convertStringToDate(comment.getCreated_at());
		this.text = comment.getBody();
		this.bugId = id;
		this.bugTrackingSystem = gitLabTracker;
		
	}
	//Utility Method
	private static java.util.Date convertStringToDate(String isoDate) {
		
		isoDate = isoDate.replaceAll("\"", "");
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}
	/**
	 * @return the bugIID
	 */
	public String getBugIID() {
		return bugIID;
	}
	/**
	 * @return the updated_at
	 */
	public Date getUpdated_at() {
		return updated_at;
	}
	/**
	 * @param bugIID the bugIID to set
	 */
	public void setBugIID(String bugIID) {
		this.bugIID = bugIID;
	}
	/**
	 * @param updated_at the updated_at to set
	 */
	public void setUpdated_at(String updated_at) {
		this.updated_at = convertStringToDate(updated_at);
	}


}
