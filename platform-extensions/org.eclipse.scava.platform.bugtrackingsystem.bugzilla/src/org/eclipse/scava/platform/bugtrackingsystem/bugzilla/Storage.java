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
import java.util.Set;

import org.eclipse.scava.platform.Date;

import utils.Comment;

import com.j2bugzilla.base.Attachment;
import com.j2bugzilla.base.Bug;

public class Storage {

	private Map<Integer, Bug> bugId2Bug;
	private Map<Integer, CommentStore> bugId2CommentStore;
	private Map<Integer, AttachmentStore> bugId2AttachmentStore;
//	private Date earliestBugDate;
//	private Date latestBugDate;
	private Date latestCheckBugDate;

	
	public Storage() {
		super();
		System.out.println("Storage initialised!");
		bugId2Bug = new HashMap<Integer, Bug>();
		bugId2CommentStore = new HashMap<Integer, CommentStore>();
		bugId2AttachmentStore = new HashMap<Integer, AttachmentStore>();
//		latestBugDate = null;
//		earliestBugDate = null;
		latestCheckBugDate = null;
	}
	
	public void storeBug(Bug bug, java.util.Date bugDate) {
		bugId2Bug.put(bug.getID(), bug);
//		if ((earliestBugDate==null)||(earliestBugDate.compareTo(bugDate)>0))
//			earliestBugDate = bugDate;
//		if ((latestBugDate==null)||(latestBugDate.compareTo(bugDate)<0))
//			latestBugDate = bugDate;
//		System.out.println("earliestBugDate: " + earliestBugDate + 
//						   "\nlatestBugDate: " + latestBugDate);
		java.util.Date date = new java.util.Date();
		if ((latestCheckBugDate==null)||(latestCheckBugDate.compareTo(date)<0))
			latestCheckBugDate = new Date(date);
//		System.out.println("Bugs: latestCheckDate: " + latestCheckBugDate);
	}

	public void storeComment(Bug bug, java.util.Date bugDate, Comment comment) {
		storeBug(bug, bugDate);
		int bugId = comment.getBugId();
		if (!bugId2CommentStore.containsKey(bugId))
			bugId2CommentStore.put(bugId, new CommentStore());
		bugId2CommentStore.get(bugId).addComment(comment);
	}
			
	public void storeAttachment(Attachment attachment) {
		int bugId = attachment.getBugID();
		if (!bugId2AttachmentStore.containsKey(bugId))
			bugId2AttachmentStore.put(bugId, new AttachmentStore());
		bugId2AttachmentStore.get(bugId).addAttachment(attachment);
	}
			
//	public Boolean containsBug(int bugId) {
//		return bugId2Bug.containsKey(bugId);
//	}

//	public Boolean containsComments(String bugIdString) {
//		int bugId = Integer.parseInt(bugIdString);
//		return bugId2CommentStore.containsKey(bugId);
//	}

	public Boolean containsComments(int bugId) {
		return bugId2CommentStore.containsKey(bugId);
	}

	public Boolean containsAttachments(String bugIdString) {
		int bugId = Integer.parseInt(bugIdString);
		return bugId2AttachmentStore.containsKey(bugId);
	}

//	public Boolean containsAttachments(int bugId) {
//		return bugId2AttachmentStore.containsKey(bugId);
//	}

	public Date getLatestCheckBugDate() {
		return latestCheckBugDate;
	}

	public Bug getBug(int bugId) {
		if (bugId2Bug.containsKey(bugId))
			return bugId2Bug.get(bugId);
		return null;
	}
	
//	public Collection<Bug> getBugs() {
//		return bugId2Bug.values();
//	}
	
	public Set<Integer> getBugIds() {
		return bugId2Bug.keySet();
	}
	
//	public CommentStore getCommentStore(String bugIdString) {
//		int bugId = Integer.parseInt(bugIdString);
//		if (bugId2CommentStore.containsKey(bugId))
//			return bugId2CommentStore.get(bugId);
//		return null;
//	}
	
	public CommentStore getCommentStore(int bugId) {
		if (bugId2CommentStore.containsKey(bugId))
			return bugId2CommentStore.get(bugId);
		return null;
	}
	
	public int getCommentStoreSize() {
		return bugId2CommentStore.size();
	}
	
	public AttachmentStore getAttachmentStore(String bugIdString) {
		int bugId = Integer.parseInt(bugIdString);
		if (bugId2AttachmentStore.containsKey(bugId))
			return bugId2AttachmentStore.get(bugId);
		return null;
	}
	
//	public AttachmentStore getAttachmentStore(int bugId) {
//		if (bugId2AttachmentStore.containsKey(bugId))
//			return bugId2AttachmentStore.get(bugId);
//		return null;
//	}
	
//	public Set<Integer> getCommentIds(int bugId) {
//		if (bugId2CommentStore.containsKey(bugId))
//			return bugId2CommentStore.get(bugId).getCommentIds();
//		return null;
//	}

	public Collection<Comment> getComments(int bugId) {
		if (bugId2CommentStore.containsKey(bugId))
			return bugId2CommentStore.get(bugId).getComments();
		return null;
	}

//	public Set<Integer> getAttachmentIds(int bugId) {
//		if (bugId2Bug.containsKey(bugId))
//			return bugId2AttachmentStore.get(bugId).getAttachmentIds();
//		return null;
//	}

//	public Collection<Attachment> getAttachments(int bugId) {
//		if (bugId2Bug.containsKey(bugId))
//			return bugId2AttachmentStore.get(bugId).getAttachments();
//		return null;
//	}

	public Collection<Attachment> getAttachments(String bugIdString) {
		int bugId = Integer.parseInt(bugIdString);
		if (bugId2Bug.containsKey(bugId))
			return bugId2AttachmentStore.get(bugId).getAttachments();
		return null;
	}

}
