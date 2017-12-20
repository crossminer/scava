/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.topics;

import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.newsgroups.topics.model.NewsgroupTopicsHistoricMetric;
import org.eclipse.crossmeter.metricprovider.historic.newsgroups.topics.model.NewsgrpTopic;
import org.eclipse.crossmeter.metricprovider.trans.topics.TopicsTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.topics.model.NewsgroupTopic;
import org.eclipse.crossmeter.metricprovider.trans.topics.model.TopicsTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.crossmeter.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class TopicsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.newsgroups.topics";

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
		for (CommunicationChannel communicationchannel: project.getCommunicationChannels()) {
			if (communicationchannel instanceof NntpNewsGroup) return true;
			if (communicationchannel instanceof Discussion) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		NewsgroupTopicsHistoricMetric topics = new NewsgroupTopicsHistoricMetric();
		if (uses.size()==1) {
			TopicsTransMetric usedTopics = ((TopicsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (NewsgroupTopic newsgroupTopic: usedTopics.getNewsgroupTopics()) {
				 NewsgrpTopic topic = new NewsgrpTopic();
				 topics.getNewsgrpTopics().add(topic);
				 topic.setNewsgroupName(newsgroupTopic.getNewsgroupName());
				 topic.setLabel(newsgroupTopic.getLabel());
				 topic.setNumberOfDocuments(newsgroupTopic.getNumberOfDocuments());
			 }
		}
		return topics;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(TopicsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newsgrouptopics";
	}

	@Override
	public String getFriendlyName() {
		return "Labels Of Newsgroup Topics Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the labels of topics (thematci clusters) in articles submitted every day.";
	}
}
