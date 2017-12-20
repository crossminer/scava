/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.sentimentclassification;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsData;
import org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData;
import org.eclipse.crossmeter.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.ITransientMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.platform.delta.ProjectDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.crossmeter.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.crossmeter.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.crossmeter.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.crossmeter.repository.model.BugTrackingSystem;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.crossmeter.repository.model.sourceforge.Discussion;
import org.eclipse.crossmeter.sentimentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.crossmeter.sentimentclassifier.opennlptartarus.libsvm.Classifier;
import org.eclipse.crossmeter.sentimentclassifier.opennlptartarus.libsvm.EmotionalDimensions;

import com.mongodb.DB;

public class SentimentClassificationTransMetricProvider  implements ITransientMetricProvider<SentimentClassificationTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return SentimentClassificationTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
		}
		return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public SentimentClassificationTransMetric adapt(DB db) {
		return new SentimentClassificationTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, 
							SentimentClassificationTransMetric db) {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
		System.err.println("Started " + getIdentifier());

		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		clearDB(db);
    	Classifier classifier = new Classifier();
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugTrackerCommentsData commentsData = findBugTrackerComment(db, bugTracker, comment);
				if (commentsData == null) {
					commentsData = new BugTrackerCommentsData();
					commentsData.setBugTrackerId(bugTracker.getOSSMeterId());
					commentsData.setBugId(comment.getBugId());
					commentsData.setCommentId(comment.getCommentId());
					db.getBugTrackerComments().add(commentsData);
				} 
				ClassificationInstance classificationInstance = prepareBugTrackerCommentInstance(bugTracker, comment);
		        classifier.add(classificationInstance);
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"prepared bug comments");
		
		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = communicationChannel.getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			for (CommunicationChannelArticle article: communicationChannelDelta.getArticles()) {
				NewsgroupArticlesData newsgroupArticlesData = 
						findNewsgroupArticle(db, communicationChannelName, article);
				if (newsgroupArticlesData == null) {
					newsgroupArticlesData = new NewsgroupArticlesData();
					newsgroupArticlesData.setNewsGroupName(communicationChannelName);
					newsgroupArticlesData.setArticleNumber(article.getArticleNumber());
					db.getNewsgroupArticles().add(newsgroupArticlesData);
				} 
				ClassificationInstance classificationInstance = 
						prepareNewsgroupArticleInstance(communicationChannelName, article);
				classifier.add(classificationInstance);
			}
			db.sync();
		}
		
		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"prepared newsgroup articles");

		classifier.classify();

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"classifier.classify() finished");

		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugTrackerCommentsData bugTrackerCommentsData = 
						findBugTrackerComment(db, bugTracker, comment);
				ClassificationInstance classificationInstance = 
						prepareBugTrackerCommentInstance(bugTracker, comment);
				String classificationResult = classifier.getClassificationResult(classificationInstance);
				bugTrackerCommentsData.setClassificationResult(classificationResult);
				String emotionalDimensions = EmotionalDimensions.getDimensions(classificationInstance);
				bugTrackerCommentsData.setEmotionalDimensions(emotionalDimensions);
			}
			db.sync();
		}

		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
										"stored classified bug comments");

		for ( CommunicationChannelDelta communicationChannelDelta: ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = communicationChannel.getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			for (CommunicationChannelArticle article: communicationChannelDelta.getArticles()) {
				NewsgroupArticlesData newsgroupArticlesData = 
						findNewsgroupArticle(db, communicationChannelName, article);
				ClassificationInstance classificationInstance = 
						prepareNewsgroupArticleInstance(communicationChannelName, article);
				String classificationResult = classifier.getClassificationResult(classificationInstance);
				newsgroupArticlesData.setClassificationResult(classificationResult);
				String emotionalDimensions = EmotionalDimensions.getDimensions(classificationInstance);
				newsgroupArticlesData.setEmotionalDimensions(emotionalDimensions);
			}
			db.sync();
		}

//		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
//										"stored classified newsgroup articles");
 	}
	
	private long printTimeMessage(long startTime, long previousTime, int size, String message) {
		long currentTime = System.currentTimeMillis();
		System.err.println(time(currentTime - previousTime) + "\t" +
						   time(currentTime - startTime) + "\t" +
						   size + "\t" + message);
		return currentTime;
	}

	private String time(long timeInMS) {
		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
	}

	private ClassificationInstance prepareBugTrackerCommentInstance(BugTrackingSystem bugTracker,
			BugTrackingSystemComment comment) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setBugTrackerId(bugTracker.getOSSMeterId());
        classificationInstance.setBugId(comment.getBugId());
        classificationInstance.setCommentId(comment.getCommentId());
        
        if (comment.getText() == null) {
        	classificationInstance.setText("");
        } else {
        	classificationInstance.setText(comment.getText());
        }
        
        return classificationInstance;
	}

	private ClassificationInstance prepareNewsgroupArticleInstance(
			String communicationChannelName, CommunicationChannelArticle article) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setNewsgroupName(communicationChannelName);
        classificationInstance.setArticleNumber(article.getArticleNumber());
        classificationInstance.setSubject(article.getSubject());
        classificationInstance.setText(article.getText());
        return classificationInstance;
	}

	private BugTrackerCommentsData findBugTrackerComment(SentimentClassificationTransMetric db, 
								BugTrackingSystem bugTracker, BugTrackingSystemComment comment) {
		BugTrackerCommentsData bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentsData> bugTrackerCommentsDataIt = 
				db.getBugTrackerComments().
						find(BugTrackerCommentsData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
								BugTrackerCommentsData.BUGID.eq(comment.getBugId()),
								BugTrackerCommentsData.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentsData bcd:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}
	

	private NewsgroupArticlesData findNewsgroupArticle(SentimentClassificationTransMetric db, 
							String communicationChannelName, CommunicationChannelArticle article) {
		NewsgroupArticlesData newsgroupArticlesData = null;
		Iterable<NewsgroupArticlesData> newsgroupArticlesDataIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticlesData.NEWSGROUPNAME.eq(communicationChannelName), 
								NewsgroupArticlesData.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticlesData nad:  newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}

	private void clearDB(SentimentClassificationTransMetric db) {
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
