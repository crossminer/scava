/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.metricprovider.trans.requestreplyclassification;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.eclipse.crossmeter.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.eclipse.crossmeter.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.eclipse.crossmeter.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.crossmeter.platform.Date;
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
import org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.crossmeter.requestreplyclassifier.opennlptartarus.libsvm.Classifier;

import com.mongodb.DB;

public class RequestReplyClassificationTransMetricProvider  implements ITransientMetricProvider<RequestReplyClassificationTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return RequestReplyClassificationTransMetricProvider.class.getCanonicalName();
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
	public RequestReplyClassificationTransMetric adapt(DB db) {
		return new RequestReplyClassificationTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, RequestReplyClassificationTransMetric db) {
		final long startTime = System.currentTimeMillis();
		long previousTime = startTime;
//		System.err.println("Started " + getIdentifier());

		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();
		clearDB(db);
    	Classifier classifier = new Classifier();
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
				BugTrackerComments bugTrackerComments = findBugTrackerComment(db, bugTracker, comment);
				if (bugTrackerComments == null) {
					bugTrackerComments = new BugTrackerComments();
					bugTrackerComments.setBugTrackerId(bugTracker.getOSSMeterId());
					bugTrackerComments.setBugId(comment.getBugId());
					bugTrackerComments.setCommentId(comment.getCommentId());
					bugTrackerComments.setDate(new Date(comment.getCreationTime()).toString());
					db.getBugTrackerComments().add(bugTrackerComments);
				} 
				prepareBugTrackerCommentInstance(classifier, bugTracker, comment);
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
				NewsgroupArticles newsgroupArticles = 
						findNewsgroupArticle(db, communicationChannelName, article);
				if (newsgroupArticles == null) {
					newsgroupArticles = new NewsgroupArticles();
					newsgroupArticles.setNewsgroupName(communicationChannelName);
					newsgroupArticles.setArticleNumber(article.getArticleNumber());
					newsgroupArticles.setDate(new Date(article.getDate()).toString());
					db.getNewsgroupArticles().add(newsgroupArticles);
				} 
				prepareNewsgroupArticleInstance(classifier, communicationChannelName, article);
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
				BugTrackerComments bugTrackerComments = findBugTrackerComment(db, bugTracker, comment);
				String classificationResult = getBugTrackerCommentClass(classifier, bugTracker, comment);
				bugTrackerComments.setClassificationResult(classificationResult);
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
				NewsgroupArticles newsgroupArticles = 
						findNewsgroupArticle(db, communicationChannelName, article);
				String classificationResult = 
						getNewsgroupArticleClass(classifier, communicationChannelName, article);
				newsgroupArticles.setClassificationResult(classificationResult);
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

	private void prepareBugTrackerCommentInstance(Classifier classifier, BugTrackingSystem bugTracker,
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
        classifier.add(classificationInstance);
	}

	private String getBugTrackerCommentClass(Classifier classifier, BugTrackingSystem bugTracker,
			BugTrackingSystemComment comment) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setBugTrackerId(bugTracker.getOSSMeterId());
        classificationInstance.setBugId(comment.getBugId());
        classificationInstance.setCommentId(comment.getCommentId());
        return classifier.getClassificationResult(classificationInstance);
	}

	private void prepareNewsgroupArticleInstance(Classifier classifier,
						String communicationChannelName, CommunicationChannelArticle article) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setNewsgroupName(communicationChannelName);
        classificationInstance.setArticleNumber(article.getArticleNumber());
        classificationInstance.setSubject(article.getSubject());
        classificationInstance.setText(article.getText());
        classifier.add(classificationInstance);
	}

	private String getNewsgroupArticleClass(Classifier classifier, String communicationChannelName, 
			CommunicationChannelArticle article) {
    	ClassificationInstance classificationInstance = new ClassificationInstance();
        classificationInstance.setNewsgroupName(communicationChannelName);
        classificationInstance.setArticleNumber(article.getArticleNumber());
        classificationInstance.setSubject(article.getSubject());
        return classifier.getClassificationResult(classificationInstance);
	}

	private BugTrackerComments findBugTrackerComment(RequestReplyClassificationTransMetric db, 
									BugTrackingSystem bugTracker, BugTrackingSystemComment comment) {
		BugTrackerComments bugTrackerCommentsData = null;
		Iterable<BugTrackerComments> bugTrackerCommentsIt = 
				db.getBugTrackerComments().
						find(BugTrackerComments.BUGTRACKERID.eq(bugTracker.getOSSMeterId()), 
								BugTrackerComments.BUGID.eq(comment.getBugId()),
								BugTrackerComments.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerComments bcd:  bugTrackerCommentsIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}
	

	private NewsgroupArticles findNewsgroupArticle(RequestReplyClassificationTransMetric db, 
							String communicationChannelName, CommunicationChannelArticle article) {
		NewsgroupArticles newsgroupArticles = null;
		Iterable<NewsgroupArticles> newsgroupArticlesIt = 
				db.getNewsgroupArticles().
						find(NewsgroupArticles.NEWSGROUPNAME.eq(communicationChannelName), 
								NewsgroupArticles.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticles nad:  newsgroupArticlesIt) {
			newsgroupArticles = nad;
		}
		return newsgroupArticles;
	}

	private void clearDB(RequestReplyClassificationTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
	}

	@Override
	public String getShortIdentifier() {
		// TODO Auto-generated method stub
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
