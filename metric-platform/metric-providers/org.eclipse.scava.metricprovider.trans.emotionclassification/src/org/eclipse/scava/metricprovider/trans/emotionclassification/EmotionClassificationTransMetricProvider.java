/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.emotionclassification;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.BugTrackerCommentsEmotionClassification;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.EmotionClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.ForumPostEmotionClassification;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification;
import org.eclipse.scava.nlp.emotion.EmotionClassifier;
import org.eclipse.scava.nlp.tools.predictions.multilabel.MultiLabelPredictionCollection;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class EmotionClassificationTransMetricProvider implements ITransientMetricProvider<EmotionClassificationTransMetric> {

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return EmotionClassificationTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "emotionclassifier";
	}

	@Override
	public String getFriendlyName() {
		return "Emotion Classifier";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the emotions present in each bug comment, newsgroup article or forum post";
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();	  
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses=uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DetectingCodeTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
		this.context = context;
	}

	@Override
	public EmotionClassificationTransMetric adapt(DB db) {
		return new EmotionClassificationTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, EmotionClassificationTransMetric db) {
		clearDB(db);
		System.err.println("Started " + getIdentifier());
		
		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		Iterable<BugTrackerCommentDetectingCode> commentsIt = detectingCodeMetric.getBugTrackerComments();
		
		MultiLabelPredictionCollection instancesCollection = new MultiLabelPredictionCollection();
		
		for(BugTrackerCommentDetectingCode comment : commentsIt)
		{
			BugTrackerCommentsEmotionClassification commentInEmotion = findBugTrackerComment(db, comment);
			if(commentInEmotion == null)
			{
				commentInEmotion = new BugTrackerCommentsEmotionClassification();
				commentInEmotion.setBugTrackerId(comment.getBugTrackerId());
				commentInEmotion.setBugId(comment.getBugId());
				commentInEmotion.setCommentId(comment.getCommentId());
				db.getBugTrackerComments().add(commentInEmotion);
			}
			db.sync();
			
			instancesCollection.addText(getBugTrackerCommentId(comment), comment.getNaturalLanguage());
		}
		
		
		Iterable<NewsgroupArticleDetectingCode> articlesIt = detectingCodeMetric.getNewsgroupArticles();
		
		for(NewsgroupArticleDetectingCode article : articlesIt)
		{
			NewsgroupArticlesEmotionClassification articleInEmotion = findNewsgroupArticle(db, article);
			if(articleInEmotion == null)
			{
				articleInEmotion = new NewsgroupArticlesEmotionClassification();
				articleInEmotion.setNewsGroupName(article.getNewsGroupName());
				articleInEmotion.setArticleNumber(article.getArticleNumber());
				db.getNewsgroupArticles().add(articleInEmotion);
			}
			db.sync();
			instancesCollection.addText(getNewsgroupArticleId(article), article.getNaturalLanguage());
		}
		
		Iterable<ForumPostDetectingCode> postsIt = detectingCodeMetric.getForumPosts();
		
		for(ForumPostDetectingCode post : postsIt)
		{
			ForumPostEmotionClassification postInEmotion = findForumPost(db, post);
			if(postInEmotion == null)
			{
				postInEmotion = new ForumPostEmotionClassification();
				postInEmotion.setForumId(post.getForumId());
				postInEmotion.setTopicId(post.getTopicId());
				postInEmotion.setPostId(post.getPostId());
				db.getForumPosts().add(postInEmotion);
			}
			db.sync();
			instancesCollection.addText(getForumPostId(post), post.getNaturalLanguage());
		}
		
		
		if(instancesCollection.size()!=0)
		{
			HashMap<Object, List<String>> predictions=null;
			try {
				predictions = EmotionClassifier.predict(instancesCollection).getIdsWithPredictedLabels();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		
			for(BugTrackerCommentDetectingCode comment : commentsIt)
			{
				
				BugTrackerCommentsEmotionClassification commentInSentiment = findBugTrackerComment(db, comment);
				commentInSentiment.setEmotions(predictions.get(getBugTrackerCommentId(comment)));
				db.sync();
			}
	
			for(NewsgroupArticleDetectingCode article : articlesIt)
			{
				NewsgroupArticlesEmotionClassification articleInSentiment = findNewsgroupArticle(db, article);
				articleInSentiment.setEmotions(predictions.get(getNewsgroupArticleId(article)));
				db.sync();
			}
			
			for(ForumPostDetectingCode post : postsIt)
			{
				ForumPostEmotionClassification postInSentiment = findForumPost(db, post);
				postInSentiment.setEmotions(predictions.get(getForumPostId(post)));
				db.sync();
			}
		}
		
	}
	
	private String getBugTrackerCommentId(BugTrackerCommentDetectingCode comment)
	{
		return "BUGTRACKER#"+comment.getBugTrackerId() + "#" + comment.getBugId() + "#" + comment.getCommentId();
	}
	
	private String getNewsgroupArticleId(NewsgroupArticleDetectingCode article)
	{
		return "NEWSGROUP#"+article.getNewsGroupName() + "#" + article.getArticleNumber();
	}
	
	private String getForumPostId(ForumPostDetectingCode post)
	{
		return "FORUM#"+post.getForumId() + "#" + post.getTopicId() + "#" + post.getPostId();
	}
	
	private BugTrackerCommentsEmotionClassification findBugTrackerComment(EmotionClassificationTransMetric db, BugTrackerCommentDetectingCode comment)
	{
		BugTrackerCommentsEmotionClassification bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentsEmotionClassification> bugTrackerCommentsDataIt = 
		db.getBugTrackerComments().
			find(BugTrackerCommentsEmotionClassification.BUGTRACKERID.eq(comment.getBugTrackerId()),
					BugTrackerCommentsEmotionClassification.BUGID.eq(comment.getBugId()),
					BugTrackerCommentsEmotionClassification.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentsEmotionClassification bcd:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}

	private NewsgroupArticlesEmotionClassification findNewsgroupArticle(EmotionClassificationTransMetric db, NewsgroupArticleDetectingCode article)
	{
		NewsgroupArticlesEmotionClassification newsgroupArticlesData = null;
		Iterable<NewsgroupArticlesEmotionClassification> newsgroupArticlesDataIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticlesEmotionClassification.NEWSGROUPNAME.eq(article.getNewsGroupName()), 
								NewsgroupArticlesEmotionClassification.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticlesEmotionClassification nad:  newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}
	
	private ForumPostEmotionClassification findForumPost(EmotionClassificationTransMetric db, ForumPostDetectingCode post) {
		ForumPostEmotionClassification forumPostData = null;
		Iterable<ForumPostEmotionClassification> forumPostsDataIt = 
				db.getForumPosts().
						find(ForumPostEmotionClassification.FORUMID.eq(post.getForumId()),
								ForumPostEmotionClassification.TOPICID.eq(post.getTopicId()), 
								ForumPostEmotionClassification.POSTID.eq(post.getPostId()));
		for (ForumPostEmotionClassification nad:  forumPostsDataIt) {
			forumPostData = nad;
		}
		return forumPostData;
	}

	//TODO: Check if this is valid
	//Do not delete the articles database, it is used in other metrics
	private void clearDB(EmotionClassificationTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
		db.sync();
	}
}
