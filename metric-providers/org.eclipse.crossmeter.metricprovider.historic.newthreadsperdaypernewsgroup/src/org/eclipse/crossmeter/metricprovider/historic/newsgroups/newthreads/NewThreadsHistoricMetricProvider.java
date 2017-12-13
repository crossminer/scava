/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.newthreads;

import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData;
import org.eclipse.crossmeter.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model.NewsgroupData;
import org.eclipse.crossmeter.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.crossmeter.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class NewThreadsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.newsgroups.newthreads";

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

	@Override
	public Pongo measure(Project project) {

		if (uses.size()!=1) {
			System.err.println("Metric: newthreadsperdaypernewsgroup failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		NewsgroupsNewThreadsHistoricMetric dailyNewThreads = new NewsgroupsNewThreadsHistoricMetric();
		NewsgroupsThreadsTransMetric usedThreads = ((ThreadsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		int numberOfNewThreads = 0,
			cumulativeNumberOfNewThreads = 0;
		for (NewsgroupData newsgroups: usedThreads.getNewsgroups()) {
			int newThreads = newsgroups.getThreads() - newsgroups.getPreviousThreads(),
				cumulativenewThreads = newsgroups.getThreads();
			dailyNewThreads.getNewsgroups().add(
					prepareNewsgroupData(newsgroups.getNewsgroupName(), newThreads, cumulativenewThreads));
			numberOfNewThreads += newThreads;
			cumulativeNumberOfNewThreads += cumulativenewThreads;
		}
		dailyNewThreads.setNumberOfNewThreads(numberOfNewThreads);
		dailyNewThreads.setCumulativeNumberOfNewThreads(cumulativeNumberOfNewThreads);
		return dailyNewThreads;
	}
			
	private DailyNewsgroupData prepareNewsgroupData(String newsgroupName, int newThreads, int cumulativeNewThreads) {
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setNewsgroupName(newsgroupName);
		dailyNewsgroupData.setNumberOfNewThreads(newThreads);
		dailyNewsgroupData.setCumulativeNumberOfNewThreads(cumulativeNewThreads);
		return dailyNewsgroupData;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ThreadsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newthreadspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of New Threads Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of new threads per day for each newsgroup separately.";
	}
}
