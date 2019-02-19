/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
//Adrián was here
package org.eclipse.scava.metricprovider.historic.forums.emotions;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.forums.emotions.model.Dimension;
import org.eclipse.scava.metricprovider.historic.forums.emotions.model.ForumEmotionData;
import org.eclipse.scava.metricprovider.historic.forums.emotions.model.ForumsEmotionsHistoricMetric;
import org.eclipse.scava.metricprovider.trans.forums.emotions.EmotionsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.forums.emotions.model.EmotionDimension;
import org.eclipse.scava.metricprovider.trans.forums.emotions.model.ForumEmotionsData;
import org.eclipse.scava.metricprovider.trans.forums.emotions.model.ForumsEmotionsTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import com.googlecode.pongo.runtime.Pongo;

public class EmotionsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.forums.emotions";

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
			if (communicationChannel instanceof EclipseForum) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		ForumsEmotionsHistoricMetric emotions = new ForumsEmotionsHistoricMetric();
		if (uses.size()==1)
		{
			ForumsEmotionsTransMetric usedEmotions = ((EmotionsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (ForumEmotionsData forumDataInEmotions: usedEmotions.getForums())
			 {
				 ForumEmotionData forums = new ForumEmotionData();
				 emotions.getForumEmotionData().add(forums);
				 forums.setForumId(forumDataInEmotions.getForumId());
				 forums.setCumulativeNumberOfPosts(forumDataInEmotions.getCumulativeNumberOfPosts());
				 forums.setNumberOfPosts(forumDataInEmotions.getNumberOfPosts());
			 }
			 
			 for (EmotionDimension emotionDimension: usedEmotions.getDimensions()) {
				 Dimension emotion = new Dimension();
				 emotions.getDimension().add(emotion);
				 emotion.setForumId(emotionDimension.getForumId());
				 emotion.setEmotionLabel(emotionDimension.getEmotionLabel());
				 emotion.setCumulativeNumberOfPosts(emotionDimension.getCumulativeNumberOfPosts());
				 emotion.setCumulativePercentage(emotionDimension.getCumulativePercentage());
				 emotion.setNumberOfPosts(emotionDimension.getNumberOfPosts());
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
		return "forumsemotions";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Forums Emotions Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of emotional dimensions in posts submitted every day.";
	}
}
