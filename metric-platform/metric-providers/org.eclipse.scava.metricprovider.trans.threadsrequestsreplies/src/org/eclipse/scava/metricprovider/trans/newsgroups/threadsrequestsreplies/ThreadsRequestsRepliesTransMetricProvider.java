/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleDataComparator;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.NewsgroupsThreadsRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.NewsgroupArticles;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.communicationchannel.nntp.NntpUtil;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class ThreadsRequestsRepliesTransMetricProvider  implements 
		ITransientMetricProvider<NewsgroupsThreadsRequestsRepliesTransMetric>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ThreadsRequestsRepliesTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
		}
		return false;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsTransMetricProvider.class.getCanonicalName(),
				RequestReplyClassificationTransMetricProvider.class.getCanonicalName());

	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsThreadsRequestsRepliesTransMetric adapt(DB db) {
		return new NewsgroupsThreadsRequestsRepliesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, 
						NewsgroupsThreadsRequestsRepliesTransMetric db) {
//		final long startTime = System.currentTimeMillis();
		
		db.getThreads().getDbCollection().drop();
		db.sync();

		if (uses.size()!=2) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the three transient metrics it needs!");
			System.exit(-1);
		}

		NewsgroupsThreadsTransMetric usedThreads = 
				((ThreadsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		RequestReplyClassificationTransMetric usedClassifier = 
				((RequestReplyClassificationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
		Map<String, String> articleReplyRequest = new HashMap<String, String>();
		for (NewsgroupArticles article: usedClassifier.getNewsgroupArticles())
			articleReplyRequest.put(article.getNewsgroupName()+article.getArticleNumber(), 
										article.getClassificationResult());

		for (ThreadData thread: usedThreads.getThreads()) {

			SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
			sortedArticleSet.addAll(thread.getArticles());

			String firstMessageTime = null;
	        Iterator<ArticleData> iterator = sortedArticleSet.iterator();
			boolean first=true,
					noReplyFound=true,
					isFirstRequest=true;

			String lastNewsgroupName = "";
			ThreadStatistics threadStats = new ThreadStatistics();
			while (iterator.hasNext()) {
				ArticleData article = iterator.next();
				lastNewsgroupName = article.getNewsgroupName();
				String responseReply = articleReplyRequest.get(article.getNewsgroupName() + article.getArticleNumber());
				if (first)
					firstMessageTime = article.getDate();
				if ((first)&&(responseReply.equals("__label__Reply"))) isFirstRequest=false;
				if ((!first)&&(noReplyFound)&&(responseReply.equals("__label__Reply"))) {
					
					threadStats.setNewsgroupName(lastNewsgroupName);
					threadStats.setFirstRequest(isFirstRequest);
					threadStats.setThreadId(thread.getThreadId());
					threadStats.setAnswered(true);
					threadStats.setResponseDate(article.getDate());
					long duration = computeDurationInSeconds(firstMessageTime, article.getDate());
					threadStats.setResponseDurationSec(duration);
					db.getThreads().add(threadStats);
					
//					System.err.println("threadId: " + thread.getThreadId() + "\t" +
//							"firstMessageTime: " + firstMessageTime + "\t" +
//							"firstResponseTime: " + article.getDate() + "\t" + 
//							"duration: " + duration);
				}
				if (responseReply.equals("__label__Reply")) noReplyFound=false;
				first=false;
			}
			if (noReplyFound&&(!first)) {
				threadStats = new ThreadStatistics();
				threadStats.setNewsgroupName(lastNewsgroupName);
				threadStats.setFirstRequest(isFirstRequest);
				threadStats.setThreadId(thread.getThreadId());
				threadStats.setAnswered(false);
				db.getThreads().add(threadStats);
				
//				System.err.println("threadId: " + threadId + "\t" +
//						"firstMessageTime: " + firstMessageTime + "\t" +
//						"unanswered");
			}
		}
		db.sync();
		
	}

	private long computeDurationInSeconds(String firstMessageTimeString, String firstResponseTimeString) {
		java.util.Date javaFirstMessageTime = NntpUtil.parseDate(firstMessageTimeString);
		java.util.Date javaFirstResponseTime = NntpUtil.parseDate(firstResponseTimeString);
//		System.err.println(" --> firstMessageTime: "+ javaFirstMessageTime + "\t" + 
//							"firstResponseTime: " + javaFirstResponseTime);
		return Date.duration(javaFirstMessageTime, javaFirstResponseTime) / 1000;
	}

	@Override
	public String getShortIdentifier() {
		return "threadsrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Thread Statistics (answered?, answeredDuration)";
	}

	@Override
	public String getSummaryInformation() {
		return "The metric computed for each thread whether it is answered." +
				"If yes, it also computes the response time";
	}

}
