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

import java.util.Date;

public class BugDocument extends DocumentAbstract {

	private Date created_at;
	private String bug_summary;
	private String severity;
	private String bug_id;
	private String project_name;
	private String creator;

	public BugDocument(String uid, String bugID, String projectName, String summary, Date created_at, String creator) {
		this.uid = uid;
		this.bug_id = bugID;
		this.project_name = projectName;
		this.bug_summary = summary;
		this.created_at = created_at;
		this.creator = creator;
	}

	public String getProjectName() {
		return project_name;
	}

	/**
	 * @return the summary
	 */
	public String getSummary() {
		return bug_summary;
	}

	/**
	 * @return the creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @return the created_at
	 */
	public Date getCreated_at() {
		return created_at;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getBug_id() {
		return bug_id;
	}

	public String getProject_name() {
		return project_name;
	}
}
