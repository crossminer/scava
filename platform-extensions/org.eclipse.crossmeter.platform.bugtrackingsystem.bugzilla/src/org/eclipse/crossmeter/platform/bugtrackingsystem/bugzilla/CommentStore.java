/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.platform.bugtrackingsystem.bugzilla;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.crossmeter.platform.Date;

import utils.Comment;

public class CommentStore {

	private Map<Integer, Comment> comments;
//	private Date earliestCommentDate;
//	private Date latestCommentDate;
	private Date latestCommentCheckDate;
	
	public CommentStore() {
		super();
		comments = new HashMap<Integer, Comment>();
//		earliestCommentDate = null;
//		latestCommentDate = null;
		latestCommentCheckDate = null;
	}

	public void addComment(Comment comment) {
		if (!comments.containsKey(comment.getId())) {
			comments.put(comment.getId(), comment);
//			if ((earliestCommentDate==null)||(earliestCommentDate.compareTo(comment.getTimestamp())>0))
//				earliestCommentDate = comment.getTimestamp();
//			if ((latestCommentDate==null)||(latestCommentDate.compareTo(comment.getTimestamp())<0))
//				latestCommentDate = comment.getTimestamp();
//			System.out.println("\t(" + comments.size() + " " + comment.getBugId() + ") earliestCommentDate: " + earliestCommentDate + 
//							   "\n\t(" + comments.size() + " " + comment.getBugId() + ")   latestCommentDate: " + latestCommentDate);
			java.util.Date date = new java.util.Date();
			if ((latestCommentCheckDate==null)||(latestCommentCheckDate.compareTo(date)<0))
				latestCommentCheckDate = new Date(date);
//			System.out.println("latestCommentCheckDate: " + latestCommentCheckDate);
		}
	}

//	public Comment getComment(int commentId) {
//		return comments.get(commentId);
//	}

//	public Set<Integer> getCommentIds() {
//		return comments.keySet();
//	}

	public Collection<Comment> getComments() {
		return comments.values();
	}

//	public Boolean containsComment(int commentId) {
//		return comments.containsKey(commentId);
//	}

//	public Date getEarliestCommentDate() {
//		return earliestCommentDate;
//	}

//	public Date getLatestCommentDate() {
//		return latestCommentDate;
//	}
	
	public Date getLatestCommentCheckDate() {
		return latestCommentCheckDate;
	}
	
}
