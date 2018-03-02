/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.sourceforge.api;

import java.util.Date;

import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

public class SourceForgeComment extends BugTrackingSystemComment {

    private static final long serialVersionUID = 1L;
    
    private String subject;
	private SourceForgeAttachment[] attachments;
	private Date updateDate;
	
	public SourceForgeComment() {
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Date getUpdateDate() {
        return updateDate;
    }
	
	public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
	
	public SourceForgeAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(SourceForgeAttachment[] attachments) {
		this.attachments = attachments;
	}
	
	
}
