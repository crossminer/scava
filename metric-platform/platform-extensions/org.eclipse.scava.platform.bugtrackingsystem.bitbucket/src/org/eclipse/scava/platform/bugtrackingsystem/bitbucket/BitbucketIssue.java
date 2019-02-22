/*******************************************************************************
 * Copyright (c) 2019 University of Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bitbucket;

import java.util.Date;

import org.eclipse.scava.platform.bugtrackingsystem.bitbucket.model.issue.Issue;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.repository.model.bitbucket.BitbucketBugTrackingSystem;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

public class BitbucketIssue extends BugTrackingSystemBug {

	private static final long serialVersionUID = 1L;

	Date editedOn;

	public BitbucketIssue(Issue issue, BitbucketBugTrackingSystem bitbucketTracker ) {
		this.bugTrackingSystem = bitbucketTracker;
		this.bugId = issue.getId();
		this.creator = issue.getReporter().getUsername();
		this.creationTime = convertStringToDate(issue.getCreatedOn());
		this.status = issue.getState();
		this.summary = issue.getTitle();
		this.editedOn = convertStringToDate(issue.getUpdatedOn());
		
	}
	
	public Date getEditedOn() {
		return editedOn;
	}

	public void setEditedOn(Date editedOn) {
		this.editedOn = editedOn;
	}

	public static Date convertStringToDate(String isoDate) {

		isoDate = isoDate.replaceAll("\"", "");
		DateTimeFormatter parser = ISODateTimeFormat.dateTimeParser();
		DateTime date = parser.parseDateTime(isoDate);
		return date.toDate();
	}
}
