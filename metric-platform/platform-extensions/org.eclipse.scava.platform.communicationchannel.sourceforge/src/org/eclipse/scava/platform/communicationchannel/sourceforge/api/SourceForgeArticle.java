/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.communicationchannel.sourceforge.api;

import java.util.Date;

import org.eclipse.scava.platform.communicationchannel.sourceforge.api.SourceForgeAttachment;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;

public class SourceForgeArticle extends CommunicationChannelArticle {

    private static final long serialVersionUID = 1L;

	private SourceForgeAttachment[] attachments;
	private Date updateDate;
	private int forumId;
	
	public SourceForgeArticle() {
	}

	public Date getUpdateDate() {
        return updateDate;
    }
	
	public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

	public int getForumId() {
		return forumId;
	}

	public void setForumId(int forumId) {
		this.forumId = forumId;
	}

	public SourceForgeAttachment[] getAttachments() {
		return attachments;
	}

	public void setAttachments(SourceForgeAttachment[] attachments) {
		this.attachments = attachments;
	}
	
	
}
