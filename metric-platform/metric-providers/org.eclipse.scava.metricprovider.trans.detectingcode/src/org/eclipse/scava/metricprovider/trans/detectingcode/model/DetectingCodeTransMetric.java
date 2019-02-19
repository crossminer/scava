/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.detectingcode.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class DetectingCodeTransMetric extends PongoDB {
	
	public DetectingCodeTransMetric() {}
	
	public DetectingCodeTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentDetectingCodeCollection bugTrackerComments = null;
	protected NewsgroupArticleDetectingCodeCollection newsgroupArticles = null;
	protected ForumPostDetectingCodeCollection forumPosts = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentDetectingCodeCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticleDetectingCodeCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public ForumPostDetectingCodeCollection getForumPosts() {
		return forumPosts;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentDetectingCodeCollection(db.getCollection("DetectingCodeTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticleDetectingCodeCollection(db.getCollection("DetectingCodeTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		forumPosts = new ForumPostDetectingCodeCollection(db.getCollection("DetectingCodeTransMetric.forumPosts"));
		pongoCollections.add(forumPosts);
	}
}