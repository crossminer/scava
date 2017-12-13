/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Jacob Carter - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.sourceforge.api;

import java.util.Date;

import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;

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
