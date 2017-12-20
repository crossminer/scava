/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.crossmeter.metricprovider.historic.bugs.severitybugstatus;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.crossmeter.metricprovider.historic.bugs.severitybugstatus.model.BugsSeverityBugStatusHistoricMetric;
import org.eclipse.crossmeter.metricprovider.historic.bugs.severitybugstatus.model.SeverityLevel;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.BugTrackerBugsData;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityBugStatusHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.bugs.severitybugstatus";

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
		BugsSeverityBugStatusHistoricMetric metric = new BugsSeverityBugStatusHistoricMetric();
		
		if (uses.size()==2) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			BugsBugMetadataTransMetric bugMetadata = 
					 ((BugMetadataTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
			 
			Map<String, Integer> severities = new HashMap<String, Integer>(),
								 resolvedClosedBugs = new HashMap<String, Integer>(),
								 wontFixBugs = new HashMap<String, Integer>(),
								 worksForMeBugs = new HashMap<String, Integer>(),
								 nonResolvedClosedBugs = new HashMap<String, Integer>(),
								 invalidBugs = new HashMap<String, Integer>(),
								 fixedBugs = new HashMap<String, Integer>(),
								 duplicateBugs = new HashMap<String, Integer>();

			 for (BugTrackerBugsData bugTrackerBugsData: severityClassifier.getBugTrackerBugs()) {
				 
				 String trackerId = bugTrackerBugsData.getBugTrackerId();
				 
				 String severity = bugTrackerBugsData.getSeverity();
				 addOrIncrease(severities, severity);
			 
				 BugData bugData = null;
				 Iterable<BugData> bugDataIt = bugMetadata.getBugData().find(BugData.BUGTRACKERID.eq(trackerId),
						 													 BugData.BUGID.eq(bugTrackerBugsData.getBugId()));
				 for (BugData bd: bugDataIt) bugData = bd;

				 if (bugData.getStatus().toLowerCase().equals("resolved")||
						 (bugData.getStatus().toLowerCase().equals("closed")))
					 addOrIncrease(resolvedClosedBugs, severity);

				 if (bugData.getResolution().toLowerCase().equals("wontfix")
						 ||(bugData.getResolution().toLowerCase().equals("cantfix")))
					 addOrIncrease(wontFixBugs, severity);

				 if (bugData.getResolution().toLowerCase().equals("worksforme"))
					 addOrIncrease(worksForMeBugs, severity);

				 if (!bugData.getStatus().toLowerCase().equals("resolved")
						 &&(!bugData.getStatus().toLowerCase().equals("closed")))
					 addOrIncrease(nonResolvedClosedBugs, severity);

				 if (bugData.getResolution().toLowerCase().equals("invalid")
						 ||(bugData.getResolution().toLowerCase().equals("notabug")))

					 addOrIncrease(invalidBugs, severity);

				 if ((bugData.getResolution().toLowerCase().equals("fixed"))
						 ||(bugData.getResolution().toLowerCase().equals("upstream"))
						 ||(bugData.getResolution().toLowerCase().equals("currentrelease"))
						 ||(bugData.getResolution().toLowerCase().equals("nextrelease"))
						 ||(bugData.getResolution().toLowerCase().equals("rawhide")))
					 addOrIncrease(fixedBugs, severity);

				 if (bugData.getResolution().toLowerCase().equals("duplicate"))

					 addOrIncrease(duplicateBugs, severity);

			 }
			 
			 for (String severity: severities.keySet()) {
				 int numberOfSeverityBugs = severities.get(severity);
				 SeverityLevel severityLevel = new SeverityLevel();
				 severityLevel.setSeverityLevel(severity);
				 severityLevel.setNumberOfBugs(numberOfSeverityBugs);
				 
				 int numberOfResolvedClosedBugs = getValue(resolvedClosedBugs, severity);
				 severityLevel.setNumberOfResolvedClosedBugs(numberOfResolvedClosedBugs);

				 int numberOfWontFixBugs = getValue(wontFixBugs, severity);
				 severityLevel.setNumberOfWontFixBugs(numberOfWontFixBugs);

				 int numberOfWorksForMeBugs = getValue(worksForMeBugs, severity);
				 if (numberOfWorksForMeBugs > 0)
					 severityLevel.setNumberOfWorksForMeBugs(numberOfWorksForMeBugs);
				 
				 int numberOfNonResolvedClosedBugs = getValue(nonResolvedClosedBugs, severity);
				 severityLevel.setNumberOfNonResolvedClosedBugs(numberOfNonResolvedClosedBugs);
				 
				 int numberOfInvalidBugs = getValue(invalidBugs, severity);
				 severityLevel.setNumberOfInvalidBugs(numberOfInvalidBugs);
				 
				 int numberOfFixedBugs = getValue(fixedBugs, severity);
				 severityLevel.setNumberOfFixedBugs(numberOfFixedBugs);
				 
				 int numberOfDuplicateBugs = getValue(duplicateBugs, severity);
				 severityLevel.setNumberOfDuplicateBugs(numberOfDuplicateBugs);
				 
				 if (numberOfSeverityBugs > 0) {
					 severityLevel.setPercentageOfResolvedClosedBugs( ((float) numberOfResolvedClosedBugs) / numberOfSeverityBugs);
					 severityLevel.setPercentageOfWontFixBugs( ((float) numberOfWontFixBugs) / numberOfSeverityBugs);
					 severityLevel.setPercentageOfWorksForMeBugs( ((float) numberOfWorksForMeBugs) / numberOfSeverityBugs);
					 severityLevel.setPercentageOfNonResolvedClosedBugs( ((float) numberOfNonResolvedClosedBugs) / numberOfSeverityBugs);
					 severityLevel.setPercentageOfInvalidBugs( ((float) numberOfInvalidBugs) / numberOfSeverityBugs);
					 severityLevel.setPercentageOfFixedBugs( ((float) numberOfFixedBugs) / numberOfSeverityBugs);
					 severityLevel.setPercentageOfDuplicateBugs( ((float) numberOfDuplicateBugs) / numberOfSeverityBugs);
				 } else {
					 severityLevel.setPercentageOfResolvedClosedBugs(0);
					 severityLevel.setPercentageOfWontFixBugs(0);
					 severityLevel.setPercentageOfWorksForMeBugs(0);
					 severityLevel.setPercentageOfNonResolvedClosedBugs(0);
					 severityLevel.setPercentageOfInvalidBugs(0);
					 severityLevel.setPercentageOfFixedBugs(0);
					 severityLevel.setPercentageOfDuplicateBugs(0);
				 }
				 
				 metric.getSeverityLevels().add(severityLevel);
			 }
			 
		}
		return metric;
	
	}
			
	private void addOrIncrease(Map<String, Integer> map, String item) {
		if (map.containsKey(item))
			map.put(item, map.get(item) + 1);
		else
			map.put(item, + 1);
	}
	
	private int getValue(Map<String, Integer> map, String component) {
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
