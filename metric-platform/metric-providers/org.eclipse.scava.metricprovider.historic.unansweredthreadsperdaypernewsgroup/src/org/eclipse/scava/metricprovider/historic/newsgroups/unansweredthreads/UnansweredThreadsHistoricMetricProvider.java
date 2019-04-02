/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model.DailyNewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.unansweredthreads.model.NewsgroupsUnansweredThreadsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.ThreadsRequestsRepliesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.NewsgroupsThreadsRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threadsrequestsreplies.model.ThreadStatistics;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class UnansweredThreadsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = UnansweredThreadsHistoricMetricProvider.class.getCanonicalName();

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
		}
		return false;
	}

//	private String time(long timeInMS) {
//		return DurationFormatUtils.formatDuration(timeInMS, "HH:mm:ss,SSS");
//	}
	
	@Override
	public Pongo measure(Project project) {
//		final long startTime = System.currentTimeMillis();

		if (uses.size()!=1) {
			System.err.println("Metric: unansweredthreadsperdaypernewsgroup failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		NewsgroupsThreadsRequestsRepliesTransMetric usedThreads = 
				((ThreadsRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		Map<String, Integer> newsgroupsUnansweredThreads = new HashMap<String, Integer>();
		for (ThreadStatistics thread: usedThreads.getThreads()) {
			if ((!thread.getAnswered())&&(thread.getFirstRequest())) {
				if (newsgroupsUnansweredThreads.containsKey(thread.getNewsgroupName()))
					newsgroupsUnansweredThreads.put(thread.getNewsgroupName(), 
									newsgroupsUnansweredThreads.get(thread.getNewsgroupName()) + 1);
				else
					newsgroupsUnansweredThreads.put(thread.getNewsgroupName(), 1);
			}
		}
		
		NewsgroupsUnansweredThreadsHistoricMetric dailyUnansweredThreads = 
									new NewsgroupsUnansweredThreadsHistoricMetric();

		int unansweredThreadsSum = 0;
		for (String newsgroupName: newsgroupsUnansweredThreads.keySet()) {
			DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
			dailyUnansweredThreads.getNewsgroups().add(dailyNewsgroupData);
			dailyNewsgroupData.setNewsgroupName(newsgroupName);
			int unansweredThreads = newsgroupsUnansweredThreads.get(newsgroupName);
			dailyNewsgroupData.setNumberOfUnansweredThreads(unansweredThreads);
			unansweredThreadsSum += unansweredThreads;
		}
		dailyUnansweredThreads.setNumberOfUnansweredThreads(unansweredThreadsSum);
		
//		System.err.println(time(System.currentTimeMillis() - startTime) + "\tunanswered_new");
		return dailyUnansweredThreads;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "unansweredthreadspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Unanswered Threads Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of unanswered threads per day for each newsgroup separately.";
	}
}
