/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 * Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.communicationchannel.sourceforge.api;

import java.util.Date;

import org.eclipse.crossmeter.platform.communicationchannel.sourceforge.api.SourceForgeAttachment;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;

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
