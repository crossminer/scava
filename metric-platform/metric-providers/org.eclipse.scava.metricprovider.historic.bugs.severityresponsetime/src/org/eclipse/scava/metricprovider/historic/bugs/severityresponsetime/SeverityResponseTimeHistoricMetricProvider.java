/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.model.BugsSeverityResponseTimeHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.severityresponsetime.model.SeverityLevel;
import org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.BugsRequestsRepliesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model.BugStatistics;
import org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model.BugsRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityResponseTimeHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = SeverityResponseTimeHistoricMetricProvider.class.getCanonicalName();

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
		BugsSeverityResponseTimeHistoricMetric metric = new BugsSeverityResponseTimeHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			BugsRequestsRepliesTransMetric bugsRequestsReplies = 
					((BugsRequestsRepliesTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));

			Map<String, Integer> severities = new HashMap<String, Integer>();
			Map<String, Long> durations = new HashMap<String, Long>();
			 
			 for (BugTrackerBugsData bugTrackerBugsData: severityClassifier.getBugTrackerBugs()) {
				 
				 String trackerId = bugTrackerBugsData.getBugTrackerId();
				 
				 String severity = bugTrackerBugsData.getSeverity();
				 addOrIncrease(severities, severity);
			 
				 BugStatistics bugStatistics = null;
				 Iterable<BugStatistics> bugStatisticsIt = bugsRequestsReplies.getBugs().
						 							 	   find(BugStatistics.BUGTRACKERID.eq(trackerId),
						 							 			BugStatistics.BUGID.eq(bugTrackerBugsData.getBugId()));
				 for (BugStatistics bd: bugStatisticsIt) bugStatistics = bd;

				 if ((bugStatistics!=null) && bugStatistics.getAnswered())
					 addOrIncrease(durations, severity, bugStatistics.getResponseDurationSec());

			 }
			 
			 for (String severity: severities.keySet()) {
				 int numberOfSeverityBugs = severities.get(severity);
				 SeverityLevel severityLevel = new SeverityLevel();
				 long duration = getValueLong(durations, severity);
				 if (duration > 0)
				 {
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfBugs(numberOfSeverityBugs);
					 
					 
					 long avgResponseTime = computeAverageDuration(duration, numberOfSeverityBugs);
					 severityLevel.setAvgResponseTime(avgResponseTime);
					 severityLevel.setAvgResponseTimeFormatted(format(avgResponseTime));
				 }
				 metric.getSeverityLevels().add(severityLevel);
			 }
			 
		}
		return metric;
	
	}
	
	private static final long SECONDS_DAY = 24 * 60 * 60;

	private long computeAverageDuration(long sumOfDurations, int threads) {
		if (threads>0)
			return sumOfDurations/threads;
		return 0;
	}

	private String format(long avgDuration) {
		String formatted = null;
		if (avgDuration>0) {
			int days = (int) (avgDuration / SECONDS_DAY);
			long lessThanDay = (avgDuration % SECONDS_DAY);
			formatted = days + ":" + 
					DurationFormatUtils.formatDuration(lessThanDay*1000, "HH:mm:ss:SS");
		} else {
			formatted = 0 + ":" + 
					DurationFormatUtils.formatDuration(0, "HH:mm:ss:SS");
		}
		return formatted;
	}
			
	private void addOrIncrease(Map<String, Integer> map, String item) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + 1);
		else
			map.put(item, + 1);
	}
	
	private void addOrIncrease(Map<String, Long> map, String item, long increment) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + increment);
		else
			map.put(item, increment);
	}

	private long getValueLong(Map<String, Long> map, String component) {
		if (!map.containsKey(component))
			return 0;
		return map.get(component);
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityClassificationTransMetricProvider.class.getCanonicalName(),
							 BugsRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.bugs.severityresponsetime";
	}

	@Override
	public String getFriendlyName() {
		return "Average response time to bugs per severity level per day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average time in which the community (users) responds to open "
				+ "bugs per severity level per day for each bug tracker. Format: dd:HH:mm:ss:SS, "
				+ "where dd=days, HH:hours, mm=minutes, ss:seconds, SS=milliseconds. Note: there are 8 "
				+ "severity  levels (blocker, critical, major, minor, enhancement, normal, trivial, unknown). "
				+ "A bug severity is considered `unknown` if there is not enough information for the classifier "
				+ "to make a decision. For example, an unanswered bug with no user comment to analyse.";
	}
}
