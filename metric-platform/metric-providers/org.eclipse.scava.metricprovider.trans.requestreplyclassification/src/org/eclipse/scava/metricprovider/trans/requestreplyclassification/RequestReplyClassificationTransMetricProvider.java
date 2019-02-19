/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.scava.metricprovider.trans.requestreplyclassification;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.ForumPostDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.PlainTextProcessingTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.ForumPostPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.ForumsPosts;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.nlp.requestreply.RequestReplyClassifier;
import org.eclipse.scava.nlp.requestreply.RequestReplyExternalExtraFeatures;
import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.communicationchannels.PlainTextNewsgroupsSubject;
import org.eclipse.scava.nlp.tools.predictions.singlelabel.SingleLabelPredictionCollection;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelForumPost;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class RequestReplyClassificationTransMetricProvider  implements ITransientMetricProvider<RequestReplyClassificationTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;

	@Override
	public String getIdentifier() {
		return RequestReplyClassificationTransMetricProvider.class.getCanonicalName();
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
		return Arrays.asList(PlainTextProcessingTransMetricProvider.class.getCanonicalName(),
								DetectingCodeTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context=context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public RequestReplyClassificationTransMetric adapt(DB db) {
		return new RequestReplyClassificationTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, RequestReplyClassificationTransMetric db)
	{
		
		clearDB(db);
		PlainTextProcessingTransMetric plainTextMetric = ((PlainTextProcessingTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
		SingleLabelPredictionCollection instancesCollection = new SingleLabelPredictionCollection();
		PlainTextObject plainTextObject;
		boolean hasCode;
		RequestReplyExternalExtraFeatures classifierExtraFeatures;
		HashMap<Object, String> predictions=null;
		
		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas())
		{
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments())
			{
				BugTrackerComments commentInRequestReply = findBugTrackerComment(db, comment);
				if(commentInRequestReply == null)
				{
					commentInRequestReply = new BugTrackerComments();
					commentInRequestReply.setBugTrackerId(bugTracker.getOSSMeterId());
					commentInRequestReply.setBugId(comment.getBugId());
					commentInRequestReply.setCommentId(comment.getCommentId());
					commentInRequestReply.setDate(new Date(comment.getCreationTime()).toString());
					db.getBugTrackerComments().add(commentInRequestReply);
				}
				db.sync();
				
				plainTextObject=getPlainTextObject(plainTextMetric, commentInRequestReply);
				hasCode=hasCode(detectingCodeMetric, commentInRequestReply);
				classifierExtraFeatures = new RequestReplyExternalExtraFeatures(hasCode, plainTextObject.hadReplies());
				instancesCollection.addText(getBugTrackerCommentId(commentInRequestReply), plainTextObject.getPlainTextAsString(), classifierExtraFeatures);				
			}
			
			if(instancesCollection.size()>0)
			{
				predictions=classify(instancesCollection);
				for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments())
				{
					BugTrackerComments commentInRequestReply = findBugTrackerComment(db, comment);
					commentInRequestReply.setClassificationResult(predictions.get(getBugTrackerCommentId(commentInRequestReply)));
					db.sync();
				}
			}
			
			
		}
		
		
		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas())
		{
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			String plainText;
			String subject;
			//Process for forums
			if(communicationChannel instanceof EclipseForum)
			{
				for(CommunicationChannelForumPost post : communicationChannelDelta.getPosts())
				{
					ForumsPosts postsInRequestReply = findForumPost(db, post);
					if(postsInRequestReply == null)
					{
						postsInRequestReply = new ForumsPosts();
						postsInRequestReply.setForumId(communicationChannel.getOSSMeterId());
						postsInRequestReply.setTopicId(post.getTopicId());
						postsInRequestReply.setPostId(post.getPostId());
						db.getForumPosts().add(postsInRequestReply);
					}
					db.sync();
					
					plainTextObject=getPlainTextObject(plainTextMetric, postsInRequestReply);
					hasCode=hasCode(detectingCodeMetric, postsInRequestReply);
					classifierExtraFeatures = new RequestReplyExternalExtraFeatures(hasCode, plainTextObject.hadReplies());
					plainText=plainTextObject.getPlainTextAsString();
					subject=PlainTextNewsgroupsSubject.process(post.getSubject());
					instancesCollection.addText(getForumPostId(postsInRequestReply), subject + " " + plainText , classifierExtraFeatures);
				}
				if(instancesCollection.size()>0)
				{
					predictions=classify(instancesCollection);
					for (CommunicationChannelForumPost post : communicationChannelDelta.getPosts())
					{
						ForumsPosts postsInRequestReply = findForumPost(db, post);
						postsInRequestReply.setClassificationResult(predictions.get(getForumPostId(postsInRequestReply)));
						db.sync();
					}
				}
			}
			else
			{
				for (CommunicationChannelArticle article: communicationChannelDelta.getArticles())
				{
					NewsgroupArticles articleInRequestReply = findNewsgroupArticle(db, article);
					if(articleInRequestReply == null)
					{
						articleInRequestReply = new NewsgroupArticles();
						articleInRequestReply.setNewsgroupName(communicationChannel.getOSSMeterId());
						articleInRequestReply.setArticleNumber(article.getArticleNumber());
						articleInRequestReply.setDate(new Date(article.getDate()).toString());
						db.getNewsgroupArticles().add(articleInRequestReply);
					}
					db.sync();
					
					plainTextObject=getPlainTextObject(plainTextMetric, articleInRequestReply);
					hasCode=hasCode(detectingCodeMetric, articleInRequestReply);
					classifierExtraFeatures = new RequestReplyExternalExtraFeatures(hasCode, plainTextObject.hadReplies());
					subject=PlainTextNewsgroupsSubject.process(article.getSubject());
					plainText=plainTextObject.getPlainTextAsString();
					instancesCollection.addText(getNewsgroupArticleId(articleInRequestReply), subject + " " + plainText , classifierExtraFeatures);
				}
				
				if(instancesCollection.size()>0)
				{
					predictions=classify(instancesCollection);
					for (CommunicationChannelArticle article: communicationChannelDelta.getArticles())
					{
						NewsgroupArticles articleInRequestReply = findNewsgroupArticle(db, article);
						articleInRequestReply.setClassificationResult(predictions.get(getNewsgroupArticleId(articleInRequestReply)));
						db.sync();
					}
				}
			}
		}
		
 	}
	
	private HashMap<Object, String> classify(SingleLabelPredictionCollection instancesCollection)
	{

		try {
			return RequestReplyClassifier.predict(instancesCollection).getIdsWithPredictedLabel();
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getBugTrackerCommentId(BugTrackerComments comment)
	{
		return "BUGTRACKER#"+comment.getBugTrackerId() + "#" + comment.getBugId() + "#" + comment.getCommentId();
	}
	
	private PlainTextObject getPlainTextObject(PlainTextProcessingTransMetric db, BugTrackerComments comment)
	{
		BugTrackerCommentPlainTextProcessing bugtrackerCommentInPlainText = null;
		Iterable<BugTrackerCommentPlainTextProcessing> bugtrackerCommentIt = db.getBugTrackerComments().
				find(BugTrackerCommentPlainTextProcessing.BUGTRACKERID.eq(comment.getBugTrackerId()),
						BugTrackerCommentPlainTextProcessing.BUGID.eq(comment.getBugId()),
						BugTrackerCommentPlainTextProcessing.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentPlainTextProcessing btcptp:  bugtrackerCommentIt) {
			bugtrackerCommentInPlainText = btcptp;
		}
		return new PlainTextObject(bugtrackerCommentInPlainText.getPlainText(), bugtrackerCommentInPlainText.getHadReplies());
	}
	
	

	private boolean hasCode(DetectingCodeTransMetric db, BugTrackerComments comment) {
		BugTrackerCommentDetectingCode bugtrackerCommentInDetectionCode = null;
		Iterable<BugTrackerCommentDetectingCode> bugtrackerCommentIt = db.getBugTrackerComments().
				find(BugTrackerCommentDetectingCode.BUGTRACKERID.eq(comment.getBugTrackerId()),
						BugTrackerCommentDetectingCode.BUGID.eq(comment.getBugId()),
						BugTrackerCommentDetectingCode.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentDetectingCode btcdc:  bugtrackerCommentIt) {
			bugtrackerCommentInDetectionCode = btcdc;
		}
		if(bugtrackerCommentInDetectionCode.getCode() == null)
			return false;
		else
			return true;
	}
	
	private String getNewsgroupArticleId(NewsgroupArticles article)
	{
		return "NEWSGROUP#"+article.getNewsgroupName() + "#" + article.getArticleNumber();
	}
	
	private PlainTextObject getPlainTextObject(PlainTextProcessingTransMetric db, NewsgroupArticles article) {
		NewsgroupArticlePlainTextProcessing newsgroupArticleInPlainText = null;
		Iterable<NewsgroupArticlePlainTextProcessing> newsgroupArticleIt = db.getNewsgroupArticles().
				find(NewsgroupArticlePlainTextProcessing.NEWSGROUPNAME.eq(article.getNewsgroupName()),
						NewsgroupArticlePlainTextProcessing.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticlePlainTextProcessing nadc:  newsgroupArticleIt) {
			newsgroupArticleInPlainText = nadc;
		}
		return new PlainTextObject(newsgroupArticleInPlainText.getPlainText(), newsgroupArticleInPlainText.getHadReplies());
	}
	
	private boolean hasCode(DetectingCodeTransMetric db, NewsgroupArticles article) {
		NewsgroupArticleDetectingCode newsgroupArticleInDetectionCode = null;
		Iterable<NewsgroupArticleDetectingCode> newsgroupArticleIt = db.getNewsgroupArticles().
				find(NewsgroupArticleDetectingCode.NEWSGROUPNAME.eq(article.getNewsgroupName()),
						NewsgroupArticleDetectingCode.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticleDetectingCode nadc:  newsgroupArticleIt) {
			newsgroupArticleInDetectionCode = nadc;
		}
		if(newsgroupArticleInDetectionCode.getCode() == null)
			return false;
		else
			return true;
	}
	
	private String getForumPostId(ForumsPosts post)
	{
		return "FORUM#"+post.getForumId() + "#" + post.getTopicId() + "#" + post.getPostId();
	}
	
	
	private PlainTextObject getPlainTextObject(PlainTextProcessingTransMetric db, ForumsPosts post) {
		ForumPostPlainTextProcessing forumPostInPlainText = null;
		Iterable<ForumPostPlainTextProcessing> forumPostIt = db.getForumPosts().
				find(ForumPostPlainTextProcessing.FORUMID.eq(post.getForumId()),
						ForumPostPlainTextProcessing.TOPICID.eq(post.getTopicId()),
						ForumPostPlainTextProcessing.POSTID.eq(post.getPostId()));
		for (ForumPostPlainTextProcessing fpptp:  forumPostIt) {
			forumPostInPlainText = fpptp;
		}
		return new PlainTextObject(forumPostInPlainText.getPlainText(), forumPostInPlainText.getHadReplies());
	}
	
	private boolean hasCode(DetectingCodeTransMetric db, ForumsPosts post) {
		ForumPostDetectingCode forumPostInDetectionCode = null;
		Iterable<ForumPostDetectingCode> forumPostIt = db.getForumPosts().
				find(ForumPostDetectingCode.FORUMID.eq(post.getForumId()),
						ForumPostDetectingCode.TOPICID.eq(post.getTopicId()),
						ForumPostDetectingCode.POSTID.eq(post.getPostId()));
		for (ForumPostDetectingCode fpdc:  forumPostIt) {
			forumPostInDetectionCode = fpdc;
		}
		if(forumPostInDetectionCode.getCode() == null)
			return false;
		else
			return true;
	}
	
	
	private BugTrackerComments findBugTrackerComment(RequestReplyClassificationTransMetric db, BugTrackingSystemComment comment)
	{
		BugTrackerComments bugTrackerCommentsData = null;
		Iterable<BugTrackerComments> bugTrackerCommentsDataIt = 
		db.getBugTrackerComments().
			find(BugTrackerComments.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()),
					BugTrackerComments.BUGID.eq(comment.getBugId()),
					BugTrackerComments.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerComments bcrr:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcrr;
		}
		return bugTrackerCommentsData;
	}
	
	private NewsgroupArticles findNewsgroupArticle(RequestReplyClassificationTransMetric db, CommunicationChannelArticle article) {
		
		NewsgroupArticles newsgroupArticles = null;
		Iterable<NewsgroupArticles> newsgroupArticlesIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticles.NEWSGROUPNAME.eq(article.getCommunicationChannel().getOSSMeterId()), 
								NewsgroupArticles.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticles narr:  newsgroupArticlesIt)
		{
			newsgroupArticles = narr;
		}
		return newsgroupArticles;
	}
	
	private ForumsPosts findForumPost(RequestReplyClassificationTransMetric db, CommunicationChannelForumPost post) {
		ForumsPosts forumPosts = null;
		Iterable<ForumsPosts> forumPostsIt = 
				db.getForumPosts().
						find(ForumsPosts.FORUMID.eq(post.getCommunicationChannel().getOSSMeterId()),
								ForumsPosts.TOPICID.eq(post.getTopicId()), 
								ForumsPosts.POSTID.eq(post.getPostId()));
		for (ForumsPosts fprr:  forumPostsIt)
		{
			forumPosts = fprr;
		}
		return forumPosts;
	}

	private void clearDB(RequestReplyClassificationTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
	}

	@Override
	public String getShortIdentifier() {
		return "requestreplyclassification";
	}

	@Override
	public String getFriendlyName() {
		return "Request Reply Classification";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes if each bug comment or newsgroup article is a " +
				"request of a reply.";
	}

}
