package org.eclipse.scava.platform.bugtrackingsystem.github;

import org.eclipse.scava.platform.Date;

public class IssueDateRange {
	
	String issueId;
	Date created;
	Date modified;
	
	public IssueDateRange(String issueId, Date created, Date modified) {

		this.issueId = issueId;
		this.created = created;
		this.modified = modified;
	}
		
	public String getIssueId() {
		return issueId;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public Date getModified() {
		return modified;
	}
	
	public void updateModifiedDate(Date modified) {
		this.modified = modified;
	}
}
