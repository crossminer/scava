/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.plaintextprocessing;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.indexing.preparation.IndexPreparationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.indexing.preparation.model.IndexPrepTransMetric;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.BugTrackerCommentPlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.NewsgroupArticlePlainTextProcessing;
import org.eclipse.scava.metricprovider.trans.plaintextprocessing.model.PlainTextProcessingTransMetric;
import org.eclipse.scava.nlp.tools.plaintext.PlainTextObject;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextBugTrackersOthers;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextBugzilla;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextBugTrackerMarkdownBased;
import org.eclipse.scava.nlp.tools.plaintext.bugtrackers.PlainTextRedmine;
import org.eclipse.scava.nlp.tools.plaintext.communicationchannels.PlainTextEclipseForums;
import org.eclipse.scava.nlp.tools.plaintext.communicationchannels.PlainTextNewsgroups;
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
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class PlainTextProcessingTransMetricProvider implements ITransientMetricProvider<PlainTextProcessingTransMetric> {

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	protected PlatformCommunicationChannelManager communicationChannelManager;
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;

	@Override
	public String getIdentifier() {
		return PlainTextProcessingTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.plaintextprocessing";
	}

	@Override
	public String getFriendlyName() {
		return "Plain Text Processing";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric preprocess each bug comment, newsgroup article or forum post into a split plain text format.";
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel : project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup)
				return true;
			if (communicationChannel instanceof Discussion)
				return true;
			if (communicationChannel instanceof EclipseForum)
				return true;
			if (communicationChannel instanceof SympaMailingList)
				return true;
			if (communicationChannel instanceof Irc)
				return true;
			if (communicationChannel instanceof Mbox)
				return true;
		}
		return !project.getBugTrackingSystems().isEmpty();
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(IndexPreparationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
		
	}

	@Override
	public PlainTextProcessingTransMetric adapt(DB db) {
		return new PlainTextProcessingTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, PlainTextProcessingTransMetric db) {

		clearDB(db);
		
		//This is for the indexing
		IndexPrepTransMetric indexPrepTransMetric = ((IndexPreparationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));	
		indexPrepTransMetric.getExecutedMetricProviders().first().getMetricIdentifiers().add(getIdentifier());
		indexPrepTransMetric.sync();
		
		
		System.err.println("Started " + getIdentifier());

		PlainTextObject plainTextObject;

		BugTrackingSystemProjectDelta btspDelta = projectDelta.getBugTrackingSystemDelta();

		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas()) {

			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();

			for (BugTrackingSystemComment comment : bugTrackingSystemDelta.getComments()) {

				BugTrackerCommentPlainTextProcessing commentsData = findBugTrackerComment(db, comment);

				if (commentsData == null) {
					commentsData = new BugTrackerCommentPlainTextProcessing();
					commentsData.setBugTrackerId(bugTracker.getOSSMeterId());
					commentsData.setBugId(comment.getBugId());
					commentsData.setCommentId(comment.getCommentId());
					db.getBugTrackerComments().add(commentsData);
				}
				
				
				switch(bugTracker.getBugTrackerType())
				{
					case "gitlab":
					case "bitbucket":
					case "jira":
					case "github": plainTextObject=PlainTextBugTrackerMarkdownBased.process(comment.getText()); break;
					case "bugzilla": plainTextObject=PlainTextBugzilla.process(comment.getText()); break;
					case "redmine": plainTextObject=PlainTextRedmine.process(comment.getText()); break;
					//case "mantis":
					//case "sourceforge":
					default: plainTextObject=PlainTextBugTrackersOthers.process(comment.getText()); break;
				}
				commentsData.getPlainText().addAll(plainTextObject.getPlainTextAsList());
				commentsData.setHadReplies(plainTextObject.hadReplies());
				db.sync();
			}

		}

		CommunicationChannelProjectDelta ccpDelta = projectDelta.getCommunicationChannelDelta();
		for (CommunicationChannelDelta communicationChannelDelta : ccpDelta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();


			for (CommunicationChannelArticle article : communicationChannelDelta.getArticles()) {

				NewsgroupArticlePlainTextProcessing newsgroupArticlesData = findNewsgroupArticle(db, article);

				if (newsgroupArticlesData == null) {
					newsgroupArticlesData = new NewsgroupArticlePlainTextProcessing();
					newsgroupArticlesData.setNewsGroupName(communicationChannel.getOSSMeterId());
					newsgroupArticlesData.setArticleNumber(article.getArticleNumber());
					db.getNewsgroupArticles().add(newsgroupArticlesData);
				}

				if (communicationChannel instanceof EclipseForum) {

					plainTextObject = PlainTextEclipseForums.process(article.getText());

				} else {

					plainTextObject = PlainTextNewsgroups.process(article.getText());

				}
				
				newsgroupArticlesData.getPlainText().addAll(plainTextObject.getPlainTextAsList());
				newsgroupArticlesData.setHadReplies(plainTextObject.hadReplies());
				db.sync();
			}

		}
		
	}

	private void clearDB(PlainTextProcessingTransMetric db) {
		db.getBugTrackerComments().getDbCollection().drop();
		db.getNewsgroupArticles().getDbCollection().drop();
		db.sync();
	}

	private BugTrackerCommentPlainTextProcessing findBugTrackerComment(PlainTextProcessingTransMetric db,
			BugTrackingSystemComment comment) {
		BugTrackerCommentPlainTextProcessing bugTrackerCommentsData = null;
		Iterable<BugTrackerCommentPlainTextProcessing> bugTrackerCommentsDataIt = db.getBugTrackerComments().find(
				BugTrackerCommentPlainTextProcessing.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()),
				BugTrackerCommentPlainTextProcessing.BUGID.eq(comment.getBugId()),
				BugTrackerCommentPlainTextProcessing.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentPlainTextProcessing bcd : bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}

	private NewsgroupArticlePlainTextProcessing findNewsgroupArticle(PlainTextProcessingTransMetric db,
			CommunicationChannelArticle article) {
		
		NewsgroupArticlePlainTextProcessing newsgroupArticlesData = null;
		Iterable<NewsgroupArticlePlainTextProcessing> newsgroupArticlesDataIt = db.getNewsgroupArticles().find(
				NewsgroupArticlePlainTextProcessing.NEWSGROUPNAME.eq(article.getCommunicationChannel().getOSSMeterId()),
				NewsgroupArticlePlainTextProcessing.ARTICLENUMBER.eq(article.getArticleNumber()));
		for (NewsgroupArticlePlainTextProcessing nad : newsgroupArticlesDataIt) {
			newsgroupArticlesData = nad;
		}
		return newsgroupArticlesData;
	}
}
