/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.severity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.newsgroups.severity.model.NewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.severity.model.NewsgroupsSeveritiesHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.severity.model.SeverityLevel;
import org.eclipse.scava.metricprovider.trans.severityclassification.SeverityClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.NewsgroupThreadData;
import org.eclipse.scava.metricprovider.trans.severityclassification.model.SeverityClassificationTransMetric;
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

public class SeverityHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = SeverityHistoricMetricProvider.class.getCanonicalName();

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
		NewsgroupsSeveritiesHistoricMetric metric = new NewsgroupsSeveritiesHistoricMetric();
		
		if (uses.size()==1) {

			SeverityClassificationTransMetric severityClassifier = 
					 ((SeverityClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 
			 Map<String, Integer> threadsPerNewsgroup = new HashMap<String, Integer>();
			 Map<String, Map<String, Integer>> severitiesPerNewsgroup = new HashMap<String, Map<String, Integer>>();
			 
			 for (NewsgroupThreadData newsgroupThreadData: severityClassifier.getNewsgroupThreads()) {
				 
				 String ossmeterID = newsgroupThreadData.getNewsgroupName();
				 
				 if (threadsPerNewsgroup.containsKey(ossmeterID))
					 threadsPerNewsgroup.put(ossmeterID, threadsPerNewsgroup.get(ossmeterID) + 1);
				 else 
					 threadsPerNewsgroup.put(ossmeterID, 1);
				 
				 String severity = newsgroupThreadData.getSeverity();
				  Map<String, Integer> severityMap;
				 if (severitiesPerNewsgroup.containsKey(ossmeterID))
					 severityMap = severitiesPerNewsgroup.get(ossmeterID);
				 else {
					 severityMap = new HashMap<String, Integer>();
					 severitiesPerNewsgroup.put(ossmeterID, severityMap);
				 }
				 
				 if (severityMap.containsKey(severity))
					 severityMap.put(severity, severityMap.get(severity) + 1);
				 else
					 severityMap.put(severity, + 1);
			 
			 }
			 
			 for (String ossmeterID: threadsPerNewsgroup.keySet()) {
				 NewsgroupData newsgroupData = new NewsgroupData();
				 int numberOfThreads = threadsPerNewsgroup.get(ossmeterID);
				 newsgroupData.setNewsgroupName(ossmeterID);
				 newsgroupData.setNumberOfThreads(numberOfThreads);
				 metric.getNewsgroupData().add(newsgroupData);
			 
				 Map<String, Integer> severityMap = severitiesPerNewsgroup.get(ossmeterID);
				 
				 for (String severity: severityMap.keySet()) {
					 int numberOfSeverityThreads = severityMap.get(severity);
					 SeverityLevel severityLevel = new SeverityLevel();
					 severityLevel.setNewsgroupName(ossmeterID);
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
		return "historic.newsgroups.severity";
	}

	@Override
	public String getFriendlyName() {
		return "Number of each severity level in newsgroup threads per day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of each severity levels in threads submitted every day, "
				+ "per newsgroup. There are 7 severity  levels (blocker, critical, major, minor, enhancement,  normal, trivial).";
	}
}
