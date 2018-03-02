/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.topics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.ProcessingResult;
import org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerCommentsData;
import org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic;
import org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupArticlesData;
import org.eclipse.scava.metricprovider.trans.topics.model.NewsgroupTopic;
import org.eclipse.scava.metricprovider.trans.topics.model.TopicsTransMetric;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.communicationchannel.nntp.NntpUtil;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelArticle;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class TopicsTransMetricProvider  implements ITransientMetricProvider<TopicsTransMetric>{

	int STEP = 30;	//	DAYS
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return TopicsTransMetricProvider.class.getCanonicalName();
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
		this.bugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public TopicsTransMetric adapt(DB db) {
		return new TopicsTransMetric(db);
	}
	
	@Override
	public void measure(Project project, ProjectDelta projectDelta, TopicsTransMetric db) {
		System.err.println("Started " + getIdentifier());
		
		CommunicationChannelProjectDelta communicationChannelDelta = projectDelta.getCommunicationChannelDelta();
		if ( communicationChannelDelta.getCommunicationChannelSystemDeltas().size() > 0 ) {
			cleanNewsgroups(projectDelta.getDate(), db);
			for ( CommunicationChannelDelta ccpDelta: communicationChannelDelta.getCommunicationChannelSystemDeltas()) {
				processNewsgroups(project, ccpDelta, db);
				List<Cluster> newsgroupTopics = produceNewsgroupTopics(db);
				System.err.println("newsgroupTopics.size(): " + newsgroupTopics.size());
				storeNewsgroupTopics(newsgroupTopics, ccpDelta, db);
			}
		}

		BugTrackingSystemProjectDelta bugTrackingSystemDelta = projectDelta.getBugTrackingSystemDelta();
		if ( bugTrackingSystemDelta.getBugTrackingSystemDeltas().size() > 0 ) {
			cleanBugTrackers(projectDelta.getDate(), db);
			for (BugTrackingSystemDelta btspDelta : bugTrackingSystemDelta.getBugTrackingSystemDeltas()) {
				processBugTrackers(project, btspDelta, db);
				List<Cluster> bugTrackerTopics = produceBugTrackerTopics(db);
				System.err.println("bugTrackerTopics.size(): " + bugTrackerTopics.size());
				storeBugTrackerTopics(bugTrackerTopics, btspDelta, db);
			}
		}

//		previousTime = printTimeMessage(startTime, previousTime, classifier.instanceListSize(),
//		"stored classified newsgroup articles");
	}

	private void storeNewsgroupTopics(List<Cluster> newsgroupTopics, CommunicationChannelDelta ccpDelta, TopicsTransMetric db) {
		db.getNewsgroupTopics().getDbCollection().drop();
		for (Cluster cluster: newsgroupTopics) {
			NewsgroupTopic newsgroupTopic = new NewsgroupTopic();
			db.getNewsgroupTopics().add(newsgroupTopic);
			CommunicationChannel communicationChannel = ccpDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = ccpDelta.getCommunicationChannel().getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			newsgroupTopic.setNewsgroupName(communicationChannelName);
			newsgroupTopic.setLabel(cluster.getLabel());
			newsgroupTopic.setNumberOfDocuments(cluster.getAllDocuments().size());
		}
		db.sync();
	}

	private void storeBugTrackerTopics(List<Cluster> bugTrackerTopics, BugTrackingSystemDelta btspDelta, TopicsTransMetric db) {
		db.getBugTrackerTopics().getDbCollection().drop();
		for (Cluster cluster: bugTrackerTopics) {
			BugTrackerTopic bugTrackerTopic = new BugTrackerTopic();
			db.getBugTrackerTopics().add(bugTrackerTopic);
			bugTrackerTopic.setBugTrackerId(btspDelta.getBugTrackingSystem().getOSSMeterId());
			bugTrackerTopic.setLabel(cluster.getLabel());
			bugTrackerTopic.setNumberOfDocuments(cluster.getAllDocuments().size());
		}
		db.sync();
	}
	
	private void cleanNewsgroups(Date projectDate, TopicsTransMetric db) {

		Set<NewsgroupArticlesData> toBeRemoved = new HashSet<NewsgroupArticlesData>();
		
		for ( NewsgroupArticlesData article: db.getNewsgroupArticles() ) {
			java.util.Date javaDate = NntpUtil.parseDate(article.getDate());
			if (javaDate!=null) {
				Date date = new Date(javaDate);
				if ( projectDate.compareTo(date.addDays(STEP)) > 0 )
					toBeRemoved.add(article);
			}
		}

		for ( NewsgroupArticlesData article: toBeRemoved)
			db.getNewsgroupArticles().remove(article);
		
		db.sync();

	}

	private void cleanBugTrackers(Date projectDate, TopicsTransMetric db) {

		Set<BugTrackerCommentsData> toBeRemoved = new HashSet<BugTrackerCommentsData>();
				
		for ( BugTrackerCommentsData comment: db.getBugTrackerComments() ) {
			java.util.Date javaDate = NntpUtil.parseDate(comment.getDate());
			if (javaDate!=null) {
				Date date = new Date(javaDate);
				if ( projectDate.compareTo(date.addDays(STEP)) > 0 )
					toBeRemoved.add(comment);
			}
		}

		for ( BugTrackerCommentsData comment: toBeRemoved)
			db.getBugTrackerComments().remove(comment);
		
		db.sync();
	}
	
	private void processNewsgroups(Project project, CommunicationChannelDelta ccpDelta, TopicsTransMetric db) {
		CommunicationChannel communicationChannel = ccpDelta.getCommunicationChannel();
		String communicationChannelName;
		if (!(communicationChannel instanceof NntpNewsGroup))
			communicationChannelName = ccpDelta.getCommunicationChannel().getUrl();
		else {
			NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
			communicationChannelName = newsgroup.getNewsGroupName();
		}
		for (CommunicationChannelArticle article: ccpDelta.getArticles()) {
			NewsgroupArticlesData newsgroupArticlesData = 
					findNewsgroupArticle(db, communicationChannelName, article);
			if (newsgroupArticlesData == null) {
				newsgroupArticlesData = new NewsgroupArticlesData();
				newsgroupArticlesData.setNewsgroupName(communicationChannelName);
				newsgroupArticlesData.setArticleNumber(article.getArticleNumber());
				newsgroupArticlesData.setDate(new Date(article.getDate()).toString());
				newsgroupArticlesData.setSubject(article.getSubject());
				newsgroupArticlesData.setText(article.getText());
				db.getNewsgroupArticles().add(newsgroupArticlesData);
			} 
		}
		db.sync();
	}

	private void processBugTrackers(Project project,
			BugTrackingSystemDelta btspDelta, TopicsTransMetric db) {
		BugTrackingSystem bugTracker = btspDelta.getBugTrackingSystem();
		for (BugTrackingSystemComment comment: btspDelta.getComments()) {
			BugTrackerCommentsData bugTrackerCommentsData = findBugTrackerComment(db, bugTracker, comment);
			if (bugTrackerCommentsData == null) {
				bugTrackerCommentsData = new BugTrackerCommentsData();
				bugTrackerCommentsData.setBugTrackerId(bugTracker.getOSSMeterId());
				bugTrackerCommentsData.setBugId(comment.getBugId());
				bugTrackerCommentsData.setCommentId(comment.getCommentId());
				bugTrackerCommentsData.setSubject(retrieveSubject(btspDelta, comment.getBugId()));
				bugTrackerCommentsData.setText(comment.getText());
				bugTrackerCommentsData.setDate(new Date(comment.getCreationTime()).toString());
				db.getBugTrackerComments().add(bugTrackerCommentsData);
			} 
		}
		db.sync();
	}

	private String retrieveSubject(	BugTrackingSystemDelta btspDelta, String bugId) {
		for (BugTrackingSystemBug bug: btspDelta.getNewBugs())
			if (bug.getBugId().equals(bugId))
				return bug.getSummary();
		for (BugTrackingSystemBug bug: btspDelta.getNewBugs())
			if (bug.getBugId().equals(bugId))
				return bug.getSummary();
		return "";
	}

	private List<Cluster> produceNewsgroupTopics(TopicsTransMetric db) {
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (NewsgroupArticlesData article: db.getNewsgroupArticles())
			documents.add(new Document(article.getSubject(), article.getText()));
		return produceTopics(documents);
	}

	private List<Cluster> produceBugTrackerTopics(TopicsTransMetric db) {
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (BugTrackerCommentsData comment: db.getBugTrackerComments())
			documents.add(new Document(comment.getSubject(), comment.getText()));
		return produceTopics(documents);
	}
	
	private List<Cluster> produceTopics(ArrayList<Document> documents) {
		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();

		/* Perform clustering by topic using the Lingo algorithm. Lingo can 
		 * take advantage of the original query, so we provide it along with the documents. */
		final ProcessingResult byTopicClusters = controller.process(documents, "data mining",
				LingoClusteringAlgorithm.class);
		final List<Cluster> clustersByTopic = byTopicClusters.getClusters();

		return clustersByTopic;
	}

	private BugTrackerCommentsData findBugTrackerComment(TopicsTransMetric db, 
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
	

	private NewsgroupArticlesData findNewsgroupArticle(TopicsTransMetric db, 
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
