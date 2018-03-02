/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.delta.bugtrackingsystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.repository.model.BugTrackingSystem;

public class BugTrackingSystemBug implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	protected String bugId;
	protected String status;
	protected String creator;
	protected String operatingSystem;
	protected String priority;
	protected String resolution;
	protected String severity;
	protected Date creationTime;
	protected String summary;
	transient protected BugTrackingSystem bugTrackingSystem;	
	protected List<BugTrackingSystemComment> comments = new ArrayList<BugTrackingSystemComment>();

	public String getBugId() {
		return bugId;
	}

	public void setBugId(String bugId) {
		this.bugId = bugId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public BugTrackingSystem getBugTrackingSystem() {
		return bugTrackingSystem;
	}

	public String getOperatingSystem() {
		return operatingSystem;
	}

	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public void setBugTrackingSystem(BugTrackingSystem bugTrackingSystem) {
		this.bugTrackingSystem = bugTrackingSystem;
	}
	
	public List<BugTrackingSystemComment> getComments() {
		return comments;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BugTrackingSystemBug) {
			if (this.bugId != ((BugTrackingSystemBug) obj).getBugId())
				return false;
			return true;
		}
		return false;
	}

	public boolean equals(int bugId) {
		return equals(Integer.toString(bugId));
	}

	public boolean equals(String bugId) {
		if (!this.bugId.equals(bugId))
			return false;
		return true;
	}


}
