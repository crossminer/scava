/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.topics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.carrot2.clustering.lingo.LingoClusteringAlgorithm;
import org.carrot2.core.Cluster;
import org.carrot2.core.Controller;
import org.carrot2.core.ControllerFactory;
import org.carrot2.core.Document;
import org.carrot2.core.LanguageCode;
import org.carrot2.core.ProcessingResult;
import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.NewsgroupArticleDetectingCode;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
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
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class TopicsTransMetricProvider implements ITransientMetricProvider<TopicsTransMetric> {

	int STEP = 30; // DAYS
	protected PlatformBugTrackingSystemManager bugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;

	@Override
	public String getIdentifier() {
		return TopicsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
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
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DetectingCodeTransMetricProvider.class.getCanonicalName(),
				IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.bugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
		this.context = context;
	}

	@Override
	public TopicsTransMetric adapt(DB db) {
		return new TopicsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, TopicsTransMetric db) {
		System.err.println("Started " + getIdentifier());

		// This is for indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider) uses.get(1)).adapt(context.getProjectDB(project));
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();

		BugTrackingSystemProjectDelta bugTrackingSystemDelta = projectDelta.getBugTrackingSystemDelta();
		if (bugTrackingSystemDelta.getBugTrackingSystemDeltas().size() > 0) {
			cleanBugTrackers(projectDelta.getDate(), db);
			for (BugTrackingSystemDelta btspDelta : bugTrackingSystemDelta.getBugTrackingSystemDeltas()) {
				processBugTrackers(project, btspDelta, db);
				List<Cluster> bugTrackerTopics = produceBugTrackerTopics(db);
				System.err.println("bugTrackerTopics.size(): " + bugTrackerTopics.size());
				storeBugTrackerTopics(bugTrackerTopics, btspDelta, db);
			}
		}

		CommunicationChannelProjectDelta communicationChannelDelta = projectDelta.getCommunicationChannelDelta();
		if (communicationChannelDelta.getCommunicationChannelSystemDeltas().size() > 0) {
			cleanCommunicationChannels(projectDelta.getDate(), db);
			for (CommunicationChannelDelta ccpDelta : communicationChannelDelta.getCommunicationChannelSystemDeltas()) {
				processCommunicationChannel(project, ccpDelta, db);
				List<Cluster> newsgroupTopics = produceNewsgroupTopics(db);
				System.err.println("newsgroupTopics.size(): " + newsgroupTopics.size());
				if (newsgroupTopics.size() > 0)
					storeNewsgroupTopics(newsgroupTopics, ccpDelta, db);
				
			}
		}

	}

	private void storeNewsgroupTopics(List<Cluster> newsgroupTopics, CommunicationChannelDelta ccpDelta,
			TopicsTransMetric db) {
		db.getNewsgroupTopics().getDbCollection().drop();
		for (Cluster cluster : newsgroupTopics) {
			NewsgroupTopic newsgroupTopic = new NewsgroupTopic();
			db.getNewsgroupTopics().add(newsgroupTopic);
			CommunicationChannel communicationChannel = ccpDelta.getCommunicationChannel();
			newsgroupTopic.setNewsgroupName(communicationChannel.getOSSMeterId());
			newsgroupTopic.getLabels().addAll(cluster.getPhrases());
			newsgroupTopic.setNumberOfDocuments(cluster.getAllDocuments().size());
			for(Document document : cluster.getAllDocuments())
			{
				String[] uid = document.getStringId().split("\t");
				newsgroupTopic.getArticlesId().add(uid[1]);
			}
		}
		db.sync();
	}


	private void storeBugTrackerTopics(List<Cluster> bugTrackerTopics, BugTrackingSystemDelta btspDelta,
			TopicsTransMetric db) {
		db.getBugTrackerTopics().getDbCollection().drop();
		for (Cluster cluster : bugTrackerTopics) {
			BugTrackerTopic bugTrackerTopic = new BugTrackerTopic();
			db.getBugTrackerTopics().add(bugTrackerTopic);
			bugTrackerTopic.setBugTrackerId(btspDelta.getBugTrackingSystem().getOSSMeterId());
			bugTrackerTopic.getLabels().addAll(cluster.getPhrases());
			bugTrackerTopic.setNumberOfDocuments(cluster.getAllDocuments().size());
			for(Document document : cluster.getAllDocuments())
			{
				String[] uid = document.getStringId().split("\t");
				bugTrackerTopic.getCommentsId().add(uid[1]+"\t"+uid[2]);
			}
		}
		db.sync();
	}

	private void cleanCommunicationChannels(Date projectDate, TopicsTransMetric db) {

		Set<NewsgroupArticlesData> toBeRemoved1 = new HashSet<NewsgroupArticlesData>();

		for (NewsgroupArticlesData article : db.getNewsgroupArticles()) {
			java.util.Date javaDate = NntpUtil.parseDate(article.getDate());
			if (javaDate != null) {
				Date date = new Date(javaDate);
				if (projectDate.compareTo(date.addDays(STEP)) > 0)
					toBeRemoved1.add(article);
			}
		}

		for (NewsgroupArticlesData article : toBeRemoved1)
			db.getNewsgroupArticles().remove(article);

		db.sync();

	}

	private void cleanBugTrackers(Date projectDate, TopicsTransMetric db) {

		Set<BugTrackerCommentsData> toBeRemoved = new HashSet<BugTrackerCommentsData>();

		for (BugTrackerCommentsData comment : db.getBugTrackerComments()) {
			java.util.Date javaDate = NntpUtil.parseDate(comment.getDate());
			if (javaDate != null) {
				Date date = new Date(javaDate);
				if (projectDate.compareTo(date.addDays(STEP)) > 0)
					toBeRemoved.add(comment);
			}
		}

		for (BugTrackerCommentsData comment : toBeRemoved)
			db.getBugTrackerComments().remove(comment);

		db.sync();
	}

	private void processCommunicationChannel(Project project, CommunicationChannelDelta ccpDelta,
			TopicsTransMetric db) {
		CommunicationChannel communicationChannel = ccpDelta.getCommunicationChannel();

		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));


		for (CommunicationChannelArticle article : ccpDelta.getArticles()) {
			NewsgroupArticlesData articleInTopic = findNewsgroupArticle(db, article);
			if (articleInTopic == null) {
				articleInTopic = new NewsgroupArticlesData();
				articleInTopic.setNewsgroupName(communicationChannel.getOSSMeterId());
				articleInTopic.setArticleId(article.getArticleId());
				articleInTopic.setDate(new Date(article.getDate()).toString());
				articleInTopic.setSubject(article.getSubject());
				articleInTopic.setText(articleNaturalLanguage(detectingCodeMetric, article));
				db.getNewsgroupArticles().add(articleInTopic);
			}
			db.sync();

		}
	}

	private void processBugTrackers(Project project, BugTrackingSystemDelta btspDelta, TopicsTransMetric db) {

		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider) uses.get(0))
				.adapt(context.getProjectDB(project));

		for (BugTrackingSystemComment comment : btspDelta.getComments()) {
			BugTrackerCommentsData commentInTopic = findBugTrackerComment(db, comment);
			if (commentInTopic == null) {
				commentInTopic = new BugTrackerCommentsData();
				commentInTopic.setBugTrackerId(comment.getBugTrackingSystem().getOSSMeterId());
				commentInTopic.setBugId(comment.getBugId());
				commentInTopic.setCommentId(comment.getCommentId());
				commentInTopic.setSubject(retrieveSubject(btspDelta, comment.getBugId()));
				commentInTopic.setText(bugNaturalLanguage(detectingCodeMetric, comment));
				commentInTopic.setDate(new Date(comment.getCreationTime()).toString());
				db.getBugTrackerComments().add(commentInTopic);

			}
		}
		db.sync();
	}
	
	private String produceUID(NewsgroupArticlesData article)
	{
		return article.getNewsgroupName()+"\t"+article.getArticleId();
	}
	
	private String produceUID(BugTrackerCommentsData comment)
	{
		return comment.getBugTrackerId()+"\t"+comment.getBugId()+"\t"+comment.getCommentId();
	}

	private String retrieveSubject(BugTrackingSystemDelta btspDelta, String bugId) {
		for (BugTrackingSystemBug bug : btspDelta.getNewBugs())
			if (bug.getBugId().equals(bugId))
				return bug.getSummary();
		for (BugTrackingSystemBug bug : btspDelta.getUpdatedBugs())
			if (bug.getBugId().equals(bugId))
				return bug.getSummary();
		return "";
	}

	private List<Cluster> produceNewsgroupTopics(TopicsTransMetric db) {
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (NewsgroupArticlesData article : db.getNewsgroupArticles())
			documents.add(new Document(article.getSubject(), article.getText(), "", LanguageCode.ENGLISH, produceUID(article)));
		return produceTopics(documents);
	}


	private List<Cluster> produceBugTrackerTopics(TopicsTransMetric db) {
		final ArrayList<Document> documents = new ArrayList<Document>();
		for (BugTrackerCommentsData comment : db.getBugTrackerComments())
			documents.add(new Document(comment.getSubject(), comment.getText(), "", LanguageCode.ENGLISH, produceUID(comment)));
		return produceTopics(documents);
	}

	private List<Cluster> produceTopics(ArrayList<Document> documents) {
		/* A controller to manage the processing pipeline. */
		final Controller controller = ControllerFactory.createSimple();

		/*
		 * Perform clustering by topic using the Lingo algorithm. Lingo can take
		 * advantage of the original query, so we provide it along with the documents.
		 */
		final ProcessingResult byTopicClusters = controller.process(documents, null,
				LingoClusteringAlgorithm.class);
		final List<Cluster> clustersByTopic = byTopicClusters.getClusters();

		return clustersByTopic;
	}

	private BugTrackerCommentsData findBugTrackerComment(TopicsTransMetric db, BugTrackingSystemComment comment) {
		BugTrackerCommentsData bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentsData> commentsIt = db.getBugTrackerComments().find(
				BugTrackerCommentsData.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()),
				BugTrackerCommentsData.BUGID.eq(comment.getBugId()),
				BugTrackerCommentsData.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentsData bcd : commentsIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}

	private String bugNaturalLanguage(DetectingCodeTransMetric db, BugTrackingSystemComment comment) {
		BugTrackerCommentDetectingCode bugTrackerComments = null;
		Iterable<BugTrackerCommentDetectingCode> commentsIt = db.getBugTrackerComments().find(
				BugTrackerCommentDetectingCode.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()),
				BugTrackerCommentDetectingCode.BUGID.eq(comment.getBugId()),
				BugTrackerCommentDetectingCode.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentDetectingCode bcd : commentsIt) {
			bugTrackerComments = bcd;
		}
		if(bugTrackerComments!=null)
			return bugTrackerComments.getNaturalLanguage();
		return "";
	}

	private NewsgroupArticlesData findNewsgroupArticle(TopicsTransMetric db, CommunicationChannelArticle article) {
		NewsgroupArticlesData newsgroupArticles = null;
		Iterable<NewsgroupArticlesData> articlesIt = db.getNewsgroupArticles().find(
				NewsgroupArticlesData.NEWSGROUPNAME.eq(article.getCommunicationChannel().getOSSMeterId()),
				NewsgroupArticlesData.ARTICLEID.eq(article.getArticleId()));
		for (NewsgroupArticlesData nad : articlesIt) {
			newsgroupArticles = nad;
		}
		return newsgroupArticles;
	}

	private String articleNaturalLanguage(DetectingCodeTransMetric db, CommunicationChannelArticle article) {
		NewsgroupArticleDetectingCode newsgroupArticles = null;
		Iterable<NewsgroupArticleDetectingCode> articlesIt = db.getNewsgroupArticles().find(
				NewsgroupArticleDetectingCode.NEWSGROUPNAME.eq(article.getCommunicationChannel().getOSSMeterId()),
				NewsgroupArticleDetectingCode.ARTICLEID.eq(article.getArticleId()));
		for (NewsgroupArticleDetectingCode nad : articlesIt) {
			newsgroupArticles = nad;
		}
		if(newsgroupArticles!=null)
			return newsgroupArticles.getNaturalLanguage();
		return "";
	}
	
	@Override
	public String getShortIdentifier() {
		return "trans.topics";
	}

	@Override
	public String getFriendlyName() {
		return "Topic Clustering";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes topic clusters for each bug comment, newsgroup article or forum post.";
	}

}
