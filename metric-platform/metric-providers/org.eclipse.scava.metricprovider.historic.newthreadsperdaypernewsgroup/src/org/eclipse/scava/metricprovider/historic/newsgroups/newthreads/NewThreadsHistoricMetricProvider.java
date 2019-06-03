/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.newthreads;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.ThreadsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.threads.model.NewsgroupsThreadsTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class NewThreadsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = NewThreadsHistoricMetricProvider.class.getCanonicalName();

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
			
	private DailyNewsgroupData prepareNewsgroupData(String ossmeterId, int newThreads, int cumulativeNewThreads) {
		DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
		dailyNewsgroupData.setNewsgroupName(ossmeterId);
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
		return "historic.newsgroups.newthreads";
	}

	@Override
	public String getFriendlyName() {
		return "Number of new threads per day per newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of new threads submitted by the community (users) "
				+ "per day for each newsgroup separately.";
	}
}
