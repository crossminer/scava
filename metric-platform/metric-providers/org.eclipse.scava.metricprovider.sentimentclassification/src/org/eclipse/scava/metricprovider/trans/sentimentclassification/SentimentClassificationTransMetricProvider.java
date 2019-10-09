/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.scava.metricprovider.trans.sentimentclassification;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesSentimentClassification;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
import org.eclipse.scava.nlp.classifiers.sentimentanalyzer.SentimentAnalyzer;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;
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

public class SentimentClassificationTransMetricProvider  implements ITransientMetricProvider<SentimentClassificationTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;

	@Override
	public String getIdentifier() {
		return SentimentClassificationTransMetricProvider.class.getCanonicalName();
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
		return Arrays.asList(DetectingCodeTransMetricProvider.class.getCanonicalName(),IndexPreparationTransMetricProvider.class.getCanonicalName() );
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
		this.context = context;
	}

	@Override
	public SentimentClassificationTransMetric adapt(DB db) {
		return new SentimentClassificationTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, SentimentClassificationTransMetric db) {
		clearDB(db);
		System.err.println("Started " + getIdentifier());
		
		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		//This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		
		Iterable<BugTrackerCommentDetectingCode> commentsIt = detectingCodeMetric.getBugTrackerComments();
		
		SingleLabelPredictionCollection instancesCollection = new SingleLabelPredictionCollection();
		
		for(BugTrackerCommentDetectingCode comment : commentsIt)
		{
			BugTrackerCommentsSentimentClassification commentInSentiment = findBugTrackerComment(db, comment);
			if(commentInSentiment == null)
			{
				commentInSentiment = new BugTrackerCommentsSentimentClassification();
				commentInSentiment.setBugTrackerId(comment.getBugTrackerId());
				commentInSentiment.setBugId(comment.getBugId());
				commentInSentiment.setCommentId(comment.getCommentId());
				db.getBugTrackerComments().add(commentInSentiment);
			}
			db.sync();
			
			instancesCollection.addText(getBugTrackerCommentId(comment), comment.getNaturalLanguage());
		}
		
		Iterable<NewsgroupArticleDetectingCode> articlesIt = detectingCodeMetric.getNewsgroupArticles();
		
		for(NewsgroupArticleDetectingCode article : articlesIt)
		{
			NewsgroupArticlesSentimentClassification articleInSentiment = findNewsgroupArticle(db, article);
			if(articleInSentiment == null)
			{
				articleInSentiment = new NewsgroupArticlesSentimentClassification();
				articleInSentiment.setNewsGroupName(article.getNewsGroupName());
				articleInSentiment.setArticleId(article.getArticleId());
				db.getNewsgroupArticles().add(articleInSentiment);
			}
			db.sync();
			instancesCollection.addText(getNewsgroupArticleId(article), article.getNaturalLanguage());
		}
		
		
		if(instancesCollection.size()!=0)
		{
			HashMap<Object, String> predictions=null;
			
			try {
				predictions = SentimentAnalyzer.predict(instancesCollection).getIdsWithPredictedLabel();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}

			for(BugTrackerCommentDetectingCode comment : commentsIt)
			{
				
				BugTrackerCommentsSentimentClassification commentInSentiment = findBugTrackerComment(db, comment);
				commentInSentiment.setPolarity(predictions.get(getBugTrackerCommentId(comment)));
				db.sync();
			}
	
			for(NewsgroupArticleDetectingCode article : articlesIt)
			{
				NewsgroupArticlesSentimentClassification articleInSentiment = findNewsgroupArticle(db, article);
				articleInSentiment.setPolarity(predictions.get(getNewsgroupArticleId(article)));
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
	
	
	private BugTrackerCommentsSentimentClassification findBugTrackerComment(SentimentClassificationTransMetric db, BugTrackerCommentDetectingCode comment)
	{
		BugTrackerCommentsSentimentClassification bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentsSentimentClassification> bugTrackerCommentsDataIt = 
		db.getBugTrackerComments().
			find(BugTrackerCommentsSentimentClassification.BUGTRACKERID.eq(comment.getBugTrackerId()),
					BugTrackerCommentsSentimentClassification.BUGID.eq(comment.getBugId()),
					BugTrackerCommentsSentimentClassification.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentsSentimentClassification bcd:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}
	

	private NewsgroupArticlesSentimentClassification findNewsgroupArticle(SentimentClassificationTransMetric db, NewsgroupArticleDetectingCode article)
	{
		NewsgroupArticlesSentimentClassification newsgroupArticlesData = null;
		Iterable<NewsgroupArticlesSentimentClassification> newsgroupArticlesDataIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticlesSentimentClassification.NEWSGROUPNAME.eq(article.getNewsGroupName()), 
								NewsgroupArticlesSentimentClassification.ARTICLEID.eq(article.getArticleId()));
		for (NewsgroupArticlesSentimentClassification nad:  newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}
	

	//TODO: Check if this is valid
	//Do not delete the articles database, it is used in other metrics
	private void clearDB(SentimentClassificationTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
		db.sync();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.sentimentclassification";
	}

	@Override
	public String getFriendlyName() {
		return "Sentiment Classification";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the sentiment of each bug comment, newsgroup article or forum post. "
				+ "Sentiment can be -1 (negative sentiment), 0 (neutral sentiment) or 1 (positive sentiment)";
	}

}
