/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.emotions;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.emotions.model.BugData;
import org.eclipse.scava.metricprovider.historic.bugs.emotions.model.BugsEmotionsHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.emotions.model.Dimension;
import org.eclipse.scava.metricprovider.trans.bugs.emotions.EmotionsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.emotions.model.BugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.emotions.model.BugsEmotionsTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.emotions.model.EmotionDimension;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsEmotionsHistoricMetric emotions = new BugsEmotionsHistoricMetric();
		if (uses.size()==1) {
			 BugsEmotionsTransMetric usedEmotions = ((EmotionsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 for (BugTrackerData bugTrackerData: usedEmotions.getBugTrackerData()) {
				 BugData bugData = new BugData();
				 emotions.getBugData().add(bugData);
				 bugData.setBugTrackerId(bugTrackerData.getBugTrackerId());
				 bugData.setCumulativeNumberOfComments(bugTrackerData.getCumulativeNumberOfComments());
				 bugData.setNumberOfComments(bugTrackerData.getNumberOfComments());
			 }
			 
			 for (EmotionDimension emotionDimension: usedEmotions.getDimensions()) {
				 Dimension dimension = new Dimension();
				 emotions.getDimensions().add(dimension);
				 dimension.setBugTrackerId(emotionDimension.getBugTrackerId());
				 dimension.setEmotionLabel(emotionDimension.getEmotionLabel());
				 dimension.setCumulativeNumberOfComments(emotionDimension.getCumulativeNumberOfComments());
				 dimension.setCumulativePercentage(emotionDimension.getCumulativePercentage());
				 dimension.setNumberOfComments(emotionDimension.getNumberOfComments());
				 dimension.setPercentage(emotionDimension.getPercentage());
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
		return "historic.bugs.emotions";
	}

	@Override
	public String getFriendlyName() {
		return "Number of emotions per day per bug tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the emotional dimensions present in bug comments submitted "
				+ "by the community (users) per day for each bug tracker.";
	}
}
