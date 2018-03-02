/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.sentiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.model.NewsgroupsSentimentTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ArticleDataComparator;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.ThreadData;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class SentimentTransMetricProvider  implements 
		ITransientMetricProvider<NewsgroupsSentimentTransMetric>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return SentimentTransMetricProvider.class.getCanonicalName();
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
				SentimentClassificationTransMetricProvider.class.getCanonicalName());

	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsSentimentTransMetric adapt(DB db) {
		return new NewsgroupsSentimentTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, 
						NewsgroupsSentimentTransMetric db) {
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

		SentimentClassificationTransMetric sentimentClassifier = 
				((SentimentClassificationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
		Map<String, String> articleSentiment = new HashMap<String, String>();
		for (org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData 
				article: sentimentClassifier.getNewsgroupArticles())
			articleSentiment.put(article.getNewsGroupName()+article.getArticleNumber(), 
										article.getClassificationResult());

		Map<String, String> articleEmotionalDimensions = new HashMap<String, String>();
		for (org.eclipse.scava.metricprovider.trans.sentimentclassification.model.NewsgroupArticlesData 
				article: sentimentClassifier.getNewsgroupArticles())
			articleEmotionalDimensions.put(article.getNewsGroupName()+article.getArticleNumber(), 
										article.getEmotionalDimensions());

		for (ThreadData thread: usedThreads.getThreads()) {

			SortedSet<ArticleData> sortedArticleSet = new TreeSet<ArticleData>(new ArticleDataComparator());
			sortedArticleSet.addAll(thread.getArticles());

	        Iterator<ArticleData> iterator = sortedArticleSet.iterator();

	        boolean first=true;
			int totalSentiment = 0;
			ThreadStatistics threadStats = new ThreadStatistics();
			db.getThreads().add(threadStats);
			while (iterator.hasNext()) {
				ArticleData article = iterator.next();
				String newsgroupName = article.getNewsgroupName();
				String sentiment = articleSentiment.get(article.getNewsgroupName()+article.getArticleNumber());

				threadStats.setNewsgroupName(newsgroupName);
				threadStats.setThreadId(thread.getThreadId());
				if (first) {
					threadStats.setStartSentiment(sentiment);
					first=false;
				}
				threadStats.setEndSentiment(sentiment);
				if (sentiment.equals("Positive")) 
					totalSentiment += 1;
				else if(sentiment.equals("Negative")) 
					totalSentiment -= 1;
//				String emotionalDimensions = articleEmotionalDimensions.get(article.getUrl_name()+article.getArticleNumber());

//				System.err.println("threadId: " + thread.getThreadId() + "\t" +
//								   "firstMessageTime: " + firstMessageTime + "\t" +
//								   "firstResponseTime: " + article.getDate());
			}
			threadStats.setAverageSentiment(((float)totalSentiment)/sortedArticleSet.size());
		}
		db.sync();
		
	}

	@Override
	public String getShortIdentifier() {
		return "threadssentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Thread Sentiment";
	}

	@Override
	public String getSummaryInformation() {
		return "The metric computes sentiment at the beggining of each thread, at its end, and on average.";
	}

}
