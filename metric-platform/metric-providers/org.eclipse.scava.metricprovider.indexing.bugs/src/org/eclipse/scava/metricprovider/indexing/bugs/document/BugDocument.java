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

public class BugDocument {

	private Date created_at;
	private String bug_summary;
	private String severity;
	private String bug_id;
	private String project_name;
	private String creator;
	private String uid;

	public BugDocument(String uid, String bugID, String projectName, String body, Date created_at, String creator) {
		this.uid = uid;
		this.bug_id = bugID;
		this.project_name = projectName;
		this.bug_summary = body;
		this.created_at = created_at;
		this.creator = creator;
	}

	public String getProjectName() {
		return project_name;
	}

	public void setProjectName(String project) {
		this.project_name = project;
	}

	/**
	 * @return the body
	 */
	public String getBody() {
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

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body) {
		this.bug_summary = body;
	}

	/**
	 * @param creator
	 *            the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @param created_at
	 *            the created_at to set
	 */
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
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

	public String getUid() {
		return uid;
	}

	public void setBug_id(String bug_id) {
		this.bug_id = bug_id;
	}

	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
