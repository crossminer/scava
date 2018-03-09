/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.bugs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.bugs.bugs.model.BugsBugsHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.bugs.model.DailyBugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.ActiveUsersTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugsActiveUsersTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.eclipse.scava.metricprovider.historic.bugs.bugs";

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

		if (uses.size()!=2) {
			System.err.println("Metric: " + IDENTIFIER + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		BugsBugMetadataTransMetric usedBugMetadata = 
				((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		BugsActiveUsersTransMetric usedUsers = 
				((ActiveUsersTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
//		System.err.println("usedBugMetadata.getBugData(): " + usedBugMetadata.getBugData().size());

		HashSet<String> bugIdSet = new HashSet<String>();
		for (BugData bugData: usedBugMetadata.getBugData())
			bugIdSet.add(bugData.getBugId());
		
		BugsBugsHistoricMetric dailyBugs = new BugsBugsHistoricMetric();

		Map<String, Integer> sumOfBugs = new HashMap<String, Integer>();
		
		for (BugData bugTrackerData: usedBugMetadata.getBugData()) {
			int sum = 1;
			if (sumOfBugs.containsKey(bugTrackerData.getBugTrackerId()))
				sum += sumOfBugs.get(bugTrackerData.getBugTrackerId());
			sumOfBugs.put(bugTrackerData.getBugTrackerId(), sum);
		}
		int totalBugs = (int) usedBugMetadata.getBugData().size();
				
		for (String bugTrackerId: sumOfBugs.keySet()) {
			DailyBugTrackerData bugTrackerData = new DailyBugTrackerData();
			bugTrackerData.setBugTrackerId(bugTrackerId);
			bugTrackerData.setNumberOfBugs(sumOfBugs.get(bugTrackerId));
			dailyBugs.getBugTrackers().add(bugTrackerData);
		}

		int numberOfComments = 0,
			numberOfRequests = 0,
			numberOfReplies = 0;
		
		for (User user: usedUsers.getUsers()) {
			numberOfComments += user.getComments();
			numberOfReplies += user.getReplies();
			numberOfRequests += user.getRequests();
		}
		
		dailyBugs.setNumberOfBugs(totalBugs);
		
		float avgArticles = 0,
			  avgReplies = 0,
			  avgRequests = 0;
		
		if (bugIdSet.size()>0) {
			avgArticles = ((float) numberOfComments) / bugIdSet.size();
			avgReplies = ((float) numberOfReplies) / bugIdSet.size();
			avgRequests = ((float) numberOfRequests) / bugIdSet.size();
		}
		
		dailyBugs.setAverageCommentsPerBug(avgArticles);
		dailyBugs.setAverageRepliesPerBug(avgReplies);
		dailyBugs.setAverageRequestsPerBug(avgRequests);
		
		avgArticles = 0;
		avgReplies = 0;
		avgRequests = 0;
			
		if (usedUsers.getUsers().size()>0) {
			avgArticles = ((float) numberOfComments) / usedUsers.getUsers().size();
			avgReplies = ((float) numberOfReplies) / usedUsers.getUsers().size();
			avgRequests = ((float) numberOfRequests) / usedUsers.getUsers().size();
		}

		dailyBugs.setAverageCommentsPerUser(avgArticles);
		dailyBugs.setAverageRepliesPerUser(avgReplies);
		dailyBugs.setAverageRequestsPerUser(avgRequests);

		return dailyBugs;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugMetadataTransMetricProvider.class.getCanonicalName(),
							 ActiveUsersTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugsperbugtracker";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bugs Per Day Per Bug Tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of bugs per day for each bug tracker separately.";
	}
}
