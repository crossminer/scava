/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.severitysentiment;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.BugsSeveritySentimentHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.severitysentiment.model.SeverityLevel;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SeveritySentimentHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.bugs.severitysentiment";

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
		BugsSeveritySentimentHistoricMetric metric = new BugsSeveritySentimentHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			BugsBugMetadataTransMetric bugMetadata = 
					 ((BugMetadataTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
			 
			 Map<String, Integer> sentimentAtBeginning = new HashMap<String, Integer>(),
					 			  sentimentAtEnd = new HashMap<String, Integer>();
			 Map<String, Float> sentimentAverage = new HashMap<String, Float>();
		 			  
			 Map<String, Integer> severities = new HashMap<String, Integer>();
			 
			 for (BugTrackerBugsData bugTrackerBugsData: severityClassifier.getBugTrackerBugs()) {
				 
				 String trackerId = bugTrackerBugsData.getBugTrackerId();
				 
				 String severity = bugTrackerBugsData.getSeverity();
				 if (severities.containsKey(severity))
					 severities.put(severity, severities.get(severity) + 1);
				 else
					 severities.put(severity, 1);
			 
				 BugData bugData = null;
				 Iterable<BugData> bugDataIt = bugMetadata.getBugData().find(BugData.BUGTRACKERID.eq(trackerId),
						 													 BugData.BUGID.eq(bugTrackerBugsData.getBugId()));
				 for (BugData bd: bugDataIt) bugData = bd;

				 float averageSentiment = bugData.getAverageSentiment();
//				 Map<String, Float> sentAverage = retrieveOrAddFloat(sentimentAverage, trackerId);
				 addOrIncreaseFloat(sentimentAverage, severity, averageSentiment);
				 
				 int startSentiment = transformSentimentToInteger(bugData.getStartSentiment());
//				 Map<String, Integer> sentBeginning = retrieveOrAdd(sentimentAtBeginning, trackerId);
				 addOrIncrease(sentimentAtBeginning, severity, startSentiment);
				 
				 int endSentiment = transformSentimentToInteger(bugData.getEndSentiment());
//				 Map<String, Integer> sentEnd = retrieveOrAdd(sentimentAtEnd, trackerId);
				 addOrIncrease(sentimentAtEnd, severity, endSentiment);

			 }
			 
			 for (String severity: severities.keySet()) {
				 int numberOfSeverityBugs = severities.get(severity);
				 SeverityLevel severityLevel = new SeverityLevel();
				 severityLevel.setSeverityLevel(severity);
				 severityLevel.setNumberOfBugs(numberOfSeverityBugs);
				 float averageSentiment = sentimentAverage.get(severity) / numberOfSeverityBugs;
				 severityLevel.setAverageSentiment(averageSentiment);
				 float sentimentAtThreadBeggining = 
						 ((float) sentimentAtBeginning.get(severity)) / numberOfSeverityBugs;
				 severityLevel.setSentimentAtThreadBeggining(sentimentAtThreadBeggining);
				 float sentimentAtThreadEnd = 
						 ((float) sentimentAtEnd.get(severity)) / numberOfSeverityBugs;
				 severityLevel.setSentimentAtThreadEnd(sentimentAtThreadEnd);
				 metric.getSeverityLevels().add(severityLevel);
			 }
			 
//			 for (String bugTrackerId: severitiesPerTracker.keySet()) {
//				 Map<String, Integer> severityMap = severitiesPerTracker.get(bugTrackerId);
//			 }
			 
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

//	private Map<String, Integer> retrieveOrAdd(
//			 Map<String, Map<String, Integer>> map, String trackerId) {
//		Map<String, Integer> component;
//		if (map.containsKey(trackerId))
//			component = map.get(trackerId);
//		else {
//			component = new HashMap<String, Integer>();
//			map.put(trackerId, component);
//		}
//		return component;
//	}
	
//	private Map<String, Float> retrieveOrAddFloat(
//			 Map<String, Map<String, Float>> map, String trackerId) {
//		Map<String, Float> component;
//		if (map.containsKey(trackerId))
//			component = map.get(trackerId);
//		else {
//			component = new HashMap<String, Float>();
//			map.put(trackerId, component);
//		}
//		return component;
//	}
	
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
							 BugMetadataTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugseveritysentiment";
	}

	@Override
	public String getFriendlyName() {
		return "Sentiment Per Bug Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average sentiment, the sentiment at " +
			   "the beginning of threads and the sentiment at the end of threads " +
			   "per severity level, in bugs submitted every day.";
	}
}
