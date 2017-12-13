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
package org.eclipse.crossmeter.metricprovider.historic.bugs.newusers;

import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.bugs.newusers.model.BugsNewUsersHistoricMetric;
import org.eclipse.crossmeter.metricprovider.historic.bugs.newusers.model.DailyBugTrackerData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.ActiveUsersTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugsActiveUsersTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class NewUsersHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.bugs.newusers";

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
		BugsNewUsersHistoricMetric dailyNewUsers = new BugsNewUsersHistoricMetric();
		if (uses.size()==1) {
			int newUsersSum = 0,
				cumulativeNewUsersSum = 0;
			BugsActiveUsersTransMetric activeUsers = ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			for (BugData newsgroup: activeUsers.getBugs()) {
				int newUsers = newsgroup.getUsers() - newsgroup.getPreviousUsers(),
					cumulativeNewUsers = newsgroup.getUsers();
				newUsersSum += newUsers;
				cumulativeNewUsersSum += cumulativeNewUsers;
				if ( (newUsers > 0) || (cumulativeNewUsers > 0) ) {
					DailyBugTrackerData dailyBugTrackerData = new DailyBugTrackerData();
					dailyBugTrackerData.setBugTrackerId(newsgroup.getBugTrackerId());
					dailyBugTrackerData.setNumberOfNewUsers(newUsers);
					dailyBugTrackerData.setCumulativeNumberOfNewUsers(cumulativeNewUsers);
					dailyNewUsers.getBugTrackers().add(dailyBugTrackerData);
				}
			}
			dailyNewUsers.setNumberOfNewUsers(newUsersSum);
			dailyNewUsers.setCumulativeNumberOfNewUsers(cumulativeNewUsersSum);
		}
		return dailyNewUsers;
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
		return "newuserspernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of New Users Per Day Per Newsgroup Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of new users " +
				"per day for each newsgroup separately.";
	}

}
