/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.detectingcode;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.nlp.codedetector.CodeDetector;
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
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class DetectingCodeTransMetricProvider implements ITransientMetricProvider<DetectingCodeTransMetric> {

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return DetectingCodeTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "codedetection";
	}

	@Override
	public String getFriendlyName() {
		return "Code Detection";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric determines which parts of a bug comment or a newsgroup article are code and which natural language";
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
		return Arrays.asList(PlainTextProcessingTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.communicationChannelManager= context.getPlatformCommunicationChannelManager();
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.context=context;
	}

	@Override
	public DetectingCodeTransMetric adapt(DB db) {
		return new DetectingCodeTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, DetectingCodeTransMetric db) {
		System.err.println("Started " + getIdentifier());
		clearDB(db);
		
		PlainTextProcessingTransMetric plainTextProcessor = ((PlainTextProcessingTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		//We obtain all the comments preprocessed by the Textpreprocessing Trans Metric
		Iterable<BugTrackerCommentPlainTextProcessing> commentsIt = plainTextProcessor.getBugTrackerComments();
		
		for (BugTrackerCommentPlainTextProcessing comment : commentsIt)
		{
			
			BugTrackerCommentDetectingCode commentDataInDC = findBugTrackerComment(db, comment);
			
			if(commentDataInDC == null)
			{
				commentDataInDC = new BugTrackerCommentDetectingCode();
				commentDataInDC.setBugTrackerId(comment.getBugTrackerId());
				commentDataInDC.setBugId(comment.getBugId());
				commentDataInDC.setCommentId(comment.getCommentId());
				db.getBugTrackerComments().add(commentDataInDC);
			}
			applyCodeDetector(comment.getPlainText(), commentDataInDC);
			db.sync();
		}
		
		Iterable<NewsgroupArticlePlainTextProcessing> articlesIt = plainTextProcessor.getNewsgroupArticles();
		
		for (NewsgroupArticlePlainTextProcessing article : articlesIt)
		{
			NewsgroupArticleDetectingCode articleDataInDC = findNewsgroupArticle(db, article);
			if(articleDataInDC == null)
			{
				articleDataInDC = new NewsgroupArticleDetectingCode();
				articleDataInDC.setNewsGroupName(article.getNewsGroupName());
				articleDataInDC.setArticleNumber(article.getArticleNumber());
				db.getNewsgroupArticles().add(articleDataInDC);
			}
			applyCodeDetector(article.getPlainText(), articleDataInDC);
			db.sync();
		}
		
		Iterable<ForumPostPlainTextProcessing> postsIt = plainTextProcessor.getForumPosts();
		for (ForumPostPlainTextProcessing post : postsIt)
		{
			ForumPostDetectingCode postDataInDC = findForumPost(db, post);
			if(postDataInDC == null)
			{
				postDataInDC = new ForumPostDetectingCode();
				postDataInDC.setForumId(post.getForumId());
				postDataInDC.setTopicId(post.getTopicId());
				postDataInDC.setPostId(post.getPostId());
				db.getForumPosts().add(postDataInDC);
			}
			applyCodeDetector(post.getPlainText(), postDataInDC);
			db.sync();
		}
	}
	
	private void applyCodeDetector(List<String> plainText, BugTrackerCommentDetectingCode comment)
	{
		String[] output = applyCodeDetector(plainText);
		comment.setCode(output[0]);
		comment.setNaturalLanguage(output[1]);
	}
	
	private void applyCodeDetector(List<String> plainText, NewsgroupArticleDetectingCode article)
	{
		String[] output = applyCodeDetector(plainText);
		article.setCode(output[0]);
		article.setNaturalLanguage(output[1]);
	}
	
	private void applyCodeDetector(List<String> plainText, ForumPostDetectingCode post)
	{
		String[] output = applyCodeDetector(plainText);
		post.setCode(output[0]);
		post.setNaturalLanguage(output[1]);
	}
	
	private String[] applyCodeDetector(List<String> plainText)
	{
		SingleLabelPredictionCollection predictions = CodeDetector.predict(plainText);
		String[] output = new String[2];
		output[0] = String.join("\n", predictions.getTextsPredictedWithLabel( "__label__Code"));
		output[1] = String.join(" ", predictions.getTextsPredictedWithLabel("__label__English"));
		return output;
	}
	
	//See if in the database, we have already analyzed this comment (for CodeDetection)
	private BugTrackerCommentDetectingCode findBugTrackerComment(DetectingCodeTransMetric db, BugTrackerCommentPlainTextProcessing comment)
	{
		BugTrackerCommentDetectingCode btcdc = null;
		Iterable<BugTrackerCommentDetectingCode> btcdcIt = 
				db.getBugTrackerComments().
					find(BugTrackerCommentDetectingCode.BUGTRACKERID.eq(comment.getBugTrackerId()),
							BugTrackerCommentDetectingCode.BUGID.eq(comment.getBugId()),
							BugTrackerCommentDetectingCode.COMMENTID.eq(comment.getCommentId()));
		for(BugTrackerCommentDetectingCode bcd : btcdcIt)
		{
			btcdc = bcd;
		}
		return btcdc;
	}
	
	private NewsgroupArticleDetectingCode findNewsgroupArticle(DetectingCodeTransMetric db, NewsgroupArticlePlainTextProcessing article)
	{
		NewsgroupArticleDetectingCode nadc = null;
		Iterable<NewsgroupArticleDetectingCode> nadcIt = 
				db.getNewsgroupArticles().
					find(NewsgroupArticleDetectingCode.NEWSGROUPNAME.eq(article.getNewsGroupName()),
							NewsgroupArticleDetectingCode.ARTICLENUMBER.eq(article.getArticleNumber()));
		for(NewsgroupArticleDetectingCode nad : nadcIt)
		{
			nadc = nad;
		}
		return nadc;
	}
	
	private ForumPostDetectingCode findForumPost(DetectingCodeTransMetric db, ForumPostPlainTextProcessing post)
	{
		ForumPostDetectingCode fpdc = null;
		Iterable<ForumPostDetectingCode> fpdcIt = 
				db.getForumPosts().
					find(ForumPostDetectingCode.FORUMID.eq(post.getForumId()),
							ForumPostDetectingCode.TOPICID.eq(post.getTopicId()),
							ForumPostDetectingCode.POSTID.eq(post.getPostId()));
		for(ForumPostDetectingCode fpd : fpdcIt)
		{
			fpdc = fpd;
		}
		return fpdc;
	}
	
	private void clearDB(DetectingCodeTransMetric db)
	{
		db.getBugTrackerComments().getDbCollection().drop();
		db.getNewsgroupArticles().getDbCollection().drop();
		db.getForumPosts().getDbCollection().drop();
		db.sync();
	}

}
