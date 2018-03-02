/*******************************************************************************
 * Copyright (c) 2018 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.platform.bugtrackingsystem.bugzilla;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.scava.platform.Date;

import com.j2bugzilla.base.Attachment;

public class AttachmentStore {

	private Map<Integer, Attachment> attachments;
	private Date latestAttachmentCheckDate;
	
	public AttachmentStore() {
		super();
		attachments = new HashMap<Integer, Attachment>();
		latestAttachmentCheckDate = null;
	}

	public void addAttachment(Attachment attachment) {
		if (!attachments.containsKey(attachment.getAttachmentID()))
			attachments.put(attachment.getAttachmentID(), attachment);
		java.util.Date date = new java.util.Date();
		if ((latestAttachmentCheckDate==null)||(latestAttachmentCheckDate.compareTo(date)<0))
			latestAttachmentCheckDate = new Date(date);
//		System.out.println("latestAttachmentCheckDate: " + latestAttachmentCheckDate);
	}

//	public Attachment getAttachment(int attachmentId) {
//		return attachments.get(attachmentId);
//	}

//	public Set<Integer> getAttachmentIds() {
//		return attachments.keySet();
//	}

	public Collection<Attachment> getAttachments() {
		return attachments.values();
	}

//	public Boolean containsAttachment(int attachmentId) {
//		return attachments.containsKey(attachmentId);
//	}

	public Date getLatestAttachmentCheckDate() {
		return latestAttachmentCheckDate;
	}
	
}
