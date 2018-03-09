/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.average;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.average.model.BugsRequestsRepliesHistoricMetric;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.ActiveUsersTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.BugsActiveUsersTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.activeusers.model.User;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class RequestsRepliesHistoricMetricProvider extends AbstractHistoricalMetricProvider{
	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.average";

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

		if (uses.size()!=1) {
			System.err.println("Metric: avgnumberofrequestsreplies failed to retrieve " + 
								"the two transient metrics it needs!");
			System.exit(-1);
		}

		 BugsActiveUsersTransMetric usedUsers = 
				 ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		int numberOfArticles = 0,
			numberOrRequests = 0,
			numberOrReplies = 0;
		for (User user: usedUsers.getUsers()) {
			numberOfArticles += user.getComments();
			numberOrReplies += user.getReplies();
			numberOrRequests += user.getRequests();
		}
		int days = 0;
		for (BugData bugTracker: usedUsers.getBugs()) {
			if (days < bugTracker.getDays())
				days = bugTracker.getDays();
		}
		
		float avgCommentsPerDay = 0,
			  avgRepliesPerDay = 0,
			  avgRequestsPerDay = 0;
		if (days>0) {
			avgCommentsPerDay = ((float) numberOfArticles) / days;
			avgRepliesPerDay = ((float) numberOrReplies) / days;
			avgRequestsPerDay = ((float) numberOrRequests) / days;
		}

		BugsRequestsRepliesHistoricMetric avgRRThread = new BugsRequestsRepliesHistoricMetric();
		avgRRThread.setAverageCommentsPerDay(avgCommentsPerDay);
		avgRRThread.setAverageRepliesPerDay(avgRepliesPerDay);
		avgRRThread.setAverageRequestsPerDay(avgRequestsPerDay);
		return avgRRThread;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ActiveUsersTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "bugsrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Average Number of Comments, Requests and Replies Per Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the average number of comments, " +
				"request and reply bug tracker comments per day.";
	}

}
