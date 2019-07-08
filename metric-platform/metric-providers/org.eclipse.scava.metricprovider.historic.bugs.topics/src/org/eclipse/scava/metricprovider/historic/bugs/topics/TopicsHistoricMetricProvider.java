/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.topics;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugTopic;
import org.eclipse.scava.metricprovider.historic.bugs.topics.model.BugsTopicsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.topics.TopicsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.topics.model.BugTrackerTopic;
import org.eclipse.scava.metricprovider.trans.topics.model.TopicsTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class TopicsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = TopicsHistoricMetricProvider.class.getCanonicalName();

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsTopicsHistoricMetric topics = new BugsTopicsHistoricMetric();
		if (uses.size()==1) {
			TopicsTransMetric usedTopics = ((TopicsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (BugTrackerTopic bugTrackerTopic: usedTopics.getBugTrackerTopics()) {
				 BugTopic topic = new BugTopic();
				 topics.getBugTopics().add(topic);
				 topic.setBugTrackerId(bugTrackerTopic.getBugTrackerId());
				 topic.getLabels().addAll(bugTrackerTopic.getLabels());
				 topic.setNumberOfDocuments(bugTrackerTopic.getNumberOfDocuments());
				 topic.getCommentsId().addAll(bugTrackerTopic.getCommentsId());
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
		return "historic.bugs.topics";
	}

	@Override
	public String getFriendlyName() {
		return "Labels of topics clusters in bug comments per bug tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the labels of topic clusters extracted from bug comments submitted by the community (users), per bug tracker." ;
	}
}
