/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.emotions;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.newsgroups.emotions.model.Emotion;
import org.eclipse.scava.metricprovider.historic.newsgroups.emotions.model.Newsgroups;
import org.eclipse.scava.metricprovider.historic.newsgroups.emotions.model.NewsgroupsEmotionsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.EmotionsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.EmotionDimension;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.emotions.model.NewsgroupsEmotionsTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class EmotionsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = EmotionsHistoricMetricProvider.class.getCanonicalName();

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
		NewsgroupsEmotionsHistoricMetric emotions = new NewsgroupsEmotionsHistoricMetric();
		if (uses.size()==1) {
			NewsgroupsEmotionsTransMetric usedEmotions = ((EmotionsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (NewsgroupData newsgroupData: usedEmotions.getNewsgroups()) {
				 Newsgroups newsgroups = new Newsgroups();
				 emotions.getNewsgroups().add(newsgroups);
				 newsgroups.setNewsgroupName(newsgroupData.getNewsgroupName());
				 newsgroups.setCumulativeNumberOfArticles(newsgroupData.getCumulativeNumberOfArticles());
				 newsgroups.setNumberOfArticles(newsgroupData.getNumberOfArticles());
			 }
			 
			 for (EmotionDimension emotionDimension: usedEmotions.getDimensions()) {
				 Emotion emotion = new Emotion();
				 emotions.getEmotions().add(emotion);
				 emotion.setNewsgroupName(emotionDimension.getNewsgroupName());
				 emotion.setEmotionLabel(emotionDimension.getEmotionLabel());
				 emotion.setCumulativeNumberOfArticles(emotionDimension.getCumulativeNumberOfArticles());
				 emotion.setCumulativePercentage(emotionDimension.getCumulativePercentage());
				 emotion.setNumberOfArticles(emotionDimension.getNumberOfArticles());
				 emotion.setPercentage(emotionDimension.getPercentage());
			 }
		}
		return emotions;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(EmotionsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugemotions";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bug Emotions Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of emotional dimensions in comments submitted every day.";
	}
}
