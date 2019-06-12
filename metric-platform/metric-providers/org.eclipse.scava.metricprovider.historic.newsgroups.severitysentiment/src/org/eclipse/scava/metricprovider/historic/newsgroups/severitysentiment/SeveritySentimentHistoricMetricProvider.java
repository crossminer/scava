/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.model.NewsgroupsSeveritySentimentHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.severitysentiment.model.SeverityLevel;
import org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.SentimentTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.model.NewsgroupsSentimentTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.sentiment.model.ThreadStatistics;
import org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupThreadData;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class SeveritySentimentHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = SeveritySentimentHistoricMetricProvider.class.getCanonicalName();

	protected MetricProviderContext context;
	
	/**
	 * List of MPs that are used by this MP. These are MPs who have specified that 
	 * they 'provide' data for this MP.
	 */
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
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
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		NewsgroupsSeveritySentimentHistoricMetric metric = new NewsgroupsSeveritySentimentHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

			NewsgroupsSentimentTransMetric threadsRequestReplies = 
					((SentimentTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

			 Map<String, Integer> sentimentAtBeginning = new HashMap<String, Integer>(),
					 			  sentimentAtEnd = new HashMap<String, Integer>();
			 Map<String, Float> sentimentAverage = new HashMap<String, Float>();
		 			  
			 Map<String, Integer> severities = new HashMap<String, Integer>();
			 
			 for (NewsgroupThreadData newsgroupThreadData: severityClassifier.getNewsgroupThreads()) {
				 
				 String severity = newsgroupThreadData.getSeverity();
				 if (severities.containsKey(severity))
					 severities.put(severity, severities.get(severity) + 1);
				 else
					 severities.put(severity, + 1);
			 
				 ThreadStatistics threadData = null;
				 Iterable<ThreadStatistics> threadDataIt = threadsRequestReplies.getThreads().
						 						find(ThreadStatistics.NEWSGROUPNAME.eq(newsgroupThreadData.getNewsgroupName()),//uses ossmeterID
						 							 ThreadStatistics.THREADID.eq(newsgroupThreadData.getThreadId()));
				 for (ThreadStatistics ts: threadDataIt) threadData = ts;
		 
				 float averageSentiment = threadData.getAverageSentiment();
				 addOrIncreaseFloat(sentimentAverage, severity, averageSentiment);
				 
				 int startSentiment = transformSentimentToInteger(threadData.getStartSentiment());
				 addOrIncrease(sentimentAtBeginning, severity, startSentiment);
				 
				 int endSentiment = transformSentimentToInteger(threadData.getEndSentiment());
				 addOrIncrease(sentimentAtEnd, severity, endSentiment);

			 }
			 
			 for (String severity: severities.keySet()) {
				 
				 SeverityLevel severityLevel = new SeverityLevel();
				 severityLevel.setSeverityLevel(severity);

				 int numberOfSeverityThreads = severities.get(severity);
				 severityLevel.setNumberOfThreads(numberOfSeverityThreads);
				 
				 float averageSentiment = sentimentAverage.get(severity) / numberOfSeverityThreads;
				 severityLevel.setAverageSentiment(averageSentiment);
				 
				 float sentimentAtThreadBeggining = ((float) sentimentAtBeginning.get(severity)) / numberOfSeverityThreads;
				 severityLevel.setSentimentAtThreadBeggining(sentimentAtThreadBeggining);
				 
				 float sentimentAtThreadEnd = ((float) sentimentAtEnd.get(severity)) / numberOfSeverityThreads;
				 severityLevel.setSentimentAtThreadEnd(sentimentAtThreadEnd);
				 
				 metric.getSeverityLevels().add(severityLevel);
			 }

		}
		return metric;
	
	}
	
	private int transformSentimentToInteger(String sentimentString) {
		 if (sentimentString.equals("__label__negative"))
			 return -1;
		 else if (sentimentString.equals("__label__positive"))
			 return 1;
		 else
			 return 0;
	}
	
	private void addOrIncrease(Map<String, Integer> map, String item, int increment) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + increment);
		else
			map.put(item, + increment);
	}
	
	private void addOrIncreaseFloat(Map<String, Float> map, String item, float increment) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + increment);
		else
			map.put(item, + increment);
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityClassificationTransMetricProvider.class.getCanonicalName(),
							 SentimentTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.newsgroups.severitysentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Average sentiment in threads per severity level per day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average sentiment, the sentiment at the beginning of threads "
				+ "and the sentiment at the end of threads; for each severity level in newsgroup threads "
				+ "submitted every day. Sentiment can be -1 (negative sentiment), 0 (neutral sentiment) "
				+ "or +1 (positive sentiment). Note: there are 7 severity  levels (blocker, critical, major, "
				+ "minor, enhancement, normal, trivial).";
	}
}
