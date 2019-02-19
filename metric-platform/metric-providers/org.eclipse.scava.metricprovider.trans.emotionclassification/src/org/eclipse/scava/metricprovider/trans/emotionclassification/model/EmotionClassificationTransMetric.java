/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.emotionclassification.model;

import com.googlecode.pongo.runtime.*;
import com.mongodb.*;
// protected region custom-imports on begin
// protected region custom-imports end

public class EmotionClassificationTransMetric extends PongoDB {
	
	public EmotionClassificationTransMetric() {}
	
	public EmotionClassificationTransMetric(DB db) {
		setDb(db);
	}
	
	protected BugTrackerCommentsEmotionClassificationCollection bugTrackerComments = null;
	protected NewsgroupArticlesEmotionClassificationCollection newsgroupArticles = null;
	protected ForumPostEmotionClassificationCollection forumPosts = null;
	
	// protected region custom-fields-and-methods on begin
	// protected region custom-fields-and-methods end
	
	
	public BugTrackerCommentsEmotionClassificationCollection getBugTrackerComments() {
		return bugTrackerComments;
	}
	
	public NewsgroupArticlesEmotionClassificationCollection getNewsgroupArticles() {
		return newsgroupArticles;
	}
	
	public ForumPostEmotionClassificationCollection getForumPosts() {
		return forumPosts;
	}
	
	
	@Override
	public void setDb(DB db) {
		super.setDb(db);
		bugTrackerComments = new BugTrackerCommentsEmotionClassificationCollection(db.getCollection("EmotionClassificationTransMetric.bugTrackerComments"));
		pongoCollections.add(bugTrackerComments);
		newsgroupArticles = new NewsgroupArticlesEmotionClassificationCollection(db.getCollection("EmotionClassificationTransMetric.newsgroupArticles"));
		pongoCollections.add(newsgroupArticles);
		forumPosts = new ForumPostEmotionClassificationCollection(db.getCollection("EmotionClassificationTransMetric.forumPosts"));
		pongoCollections.add(forumPosts);
	}
}