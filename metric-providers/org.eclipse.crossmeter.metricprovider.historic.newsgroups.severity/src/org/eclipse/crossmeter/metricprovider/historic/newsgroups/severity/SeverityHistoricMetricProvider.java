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
package org.eclipse.crossmeter.metricprovider.historic.newsgroups.severity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.crossmeter.metricprovider.historic.newsgroups.severity.model.NewsgroupData;
import org.eclipse.crossmeter.metricprovider.historic.newsgroups.severity.model.NewsgroupsSeveritiesHistoricMetric;
import org.eclipse.crossmeter.metricprovider.historic.newsgroups.severity.model.SeverityLevel;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.NewsgroupThreadData;
import org.eclipse.crossmeter.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.CommunicationChannel;
import org.eclipse.crossmeter.repository.model.Project;
import org.eclipse.crossmeter.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.crossmeter.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class SeverityHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.newsgroups.severity";

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
		NewsgroupsSeveritiesHistoricMetric metric = new NewsgroupsSeveritiesHistoricMetric();
		
		if (uses.size()==1) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			 Map<String, Integer> threadsPerNewsgroup = new HashMap<String, Integer>();
			 Map<String, Map<String, Integer>> severitiesPerNewsgroup = new HashMap<String, Map<String, Integer>>();
			 
			 for (NewsgroupThreadData newsgroupThreadData: severityClassifier.getNewsgroupThreads()) {
				 
				 String newsgroupName = newsgroupThreadData.getNewsgroupName();
				 
				 if (threadsPerNewsgroup.containsKey(newsgroupName))
					 threadsPerNewsgroup.put(newsgroupName, threadsPerNewsgroup.get(newsgroupName) + 1);
				 else 
					 threadsPerNewsgroup.put(newsgroupName, 1);
				 
				 String severity = newsgroupThreadData.getSeverity();
				  Map<String, Integer> severityMap;
				 if (severitiesPerNewsgroup.containsKey(newsgroupName))
					 severityMap = severitiesPerNewsgroup.get(newsgroupName);
				 else {
					 severityMap = new HashMap<String, Integer>();
					 severitiesPerNewsgroup.put(newsgroupName, severityMap);
				 }
				 
				 if (severityMap.containsKey(severity))
					 severityMap.put(severity, severityMap.get(severity) + 1);
				 else
					 severityMap.put(severity, + 1);
			 
			 }
			 
			 for (String newsgroupName: threadsPerNewsgroup.keySet()) {
				 NewsgroupData newsgroupData = new NewsgroupData();
				 int numberOfThreads = threadsPerNewsgroup.get(newsgroupName);
				 newsgroupData.setNewsgroupName(newsgroupName);
				 newsgroupData.setNumberOfThreads(numberOfThreads);
				 metric.getNewsgroupData().add(newsgroupData);
			 
				 Map<String, Integer> severityMap = severitiesPerNewsgroup.get(newsgroupName);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityThreads = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setNewsgroupName(newsgroupName);
					 severityLevel.setSeverityLevel(severity);
					 severityLevel.setNumberOfThreads(numberOfSeverityThreads);
					 if ( numberOfThreads > 0 )
						 severityLevel.setPercentage(( (float) 100 * numberOfSeverityThreads ) / numberOfThreads);
					 else
						 severityLevel.setPercentage( (float) 0 );
					 metric.getSeverityLevels().add(severityLevel);
				 }
			 
			 }
			 
		}
		return metric;
		
		

	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(SeverityClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "newsgroupseveritylevels";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Newsgroup Thread Severity Levels Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of newsgroup severity levels in threads submitted every day.";
	}
}
