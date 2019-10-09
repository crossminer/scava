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
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.BugTrackerCommentsEmotionClassification;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.EmotionClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.emotionclassification.model.NewsgroupArticlesEmotionClassification;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.nlp.classifiers.emotionclassifier.EmotionClassifier;
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
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
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
		return "trans.emotionclassification";
	}

	@Override
	public String getFriendlyName() {
		return "Emotion Classifier";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the emotions present in each bug comment, newsgroup article or forum post. "
				+ "There are 6 emotion labels (anger, fear, joy, sadness, love, surprise).";
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
			if (communicationChannel instanceof SympaMailingList) return true;
			if (communicationChannel instanceof Irc) return true;
			if (communicationChannel instanceof Mbox) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();	  
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses=uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DetectingCodeTransMetricProvider.class.getCanonicalName(), IndexPreparationTransMetricProvider.class.getCanonicalName());
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
		
		
		//This is for the indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		//Then we add it back to the database
		indexPrepTransMetric.sync();
		
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
				articleInEmotion.setArticleId(article.getArticleId());
				db.getNewsgroupArticles().add(articleInEmotion);
			}
			db.sync();
			instancesCollection.addText(getNewsgroupArticleId(article), article.getNaturalLanguage());
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
				commentInSentiment.getEmotions().addAll(predictions.get(getBugTrackerCommentId(comment)));
				db.sync();
			}
	
			for(NewsgroupArticleDetectingCode article : articlesIt)
			{
				NewsgroupArticlesEmotionClassification articleInSentiment = findNewsgroupArticle(db, article);
				articleInSentiment.getEmotions().addAll(predictions.get(getNewsgroupArticleId(article)));
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
		return "NEWSGROUP#"+article.getNewsGroupName() + "#" + article.getArticleId();
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
								NewsgroupArticlesEmotionClassification.ARTICLEID.eq(article.getArticleId()));
		for (NewsgroupArticlesEmotionClassification nad:  newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}
	

	//TODO: Check if this is valid
	//Do not delete the articles database, it is used in other metrics
	private void clearDB(EmotionClassificationTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
		db.sync();
	}
}
