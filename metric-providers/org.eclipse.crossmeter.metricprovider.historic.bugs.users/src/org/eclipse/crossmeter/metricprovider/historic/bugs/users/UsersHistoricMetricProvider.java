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
package org.eclipse.crossmeter.metricprovider.historic.bugs.users;

import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.bugs.users.model.BugsUsersHistoricMetric;
import org.eclipse.crossmeter.metricprovider.historic.bugs.users.model.DailyBugTrackingData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.ActiveUsersTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.activeusers.model.BugsActiveUsersTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class UsersHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = 
			"org.eclipse.crossmeter.metricprovider.historic.bugs.users";

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
		BugsUsersHistoricMetric users = new BugsUsersHistoricMetric();
		if (uses.size()==1) {
			BugsActiveUsersTransMetric activeUsers = ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			int numberOfUsers = 0,
				numberOfActiveUsers = 0,
				numberOfInactiveUsers = 0;
			for (BugData bug: activeUsers.getBugs()) {
				if ((bug.getUsers() > 0) || (bug.getActiveUsers() > 0) || (bug.getInactiveUsers() > 0)) {
					DailyBugTrackingData dailyNewsgroupData = new DailyBugTrackingData();
					dailyNewsgroupData.setBugTrackerId(bug.getBugTrackerId());
					if (bug.getUsers() > 0) {
						dailyNewsgroupData.setNumberOfUsers(bug.getUsers());
						numberOfUsers += bug.getUsers();
					}
					if (bug.getActiveUsers() > 0) {
						dailyNewsgroupData.setNumberOfActiveUsers(bug.getActiveUsers());
						numberOfActiveUsers += bug.getActiveUsers();
					}
					if (bug.getInactiveUsers() > 0) {
						dailyNewsgroupData.setNumberOfInactiveUsers(bug.getInactiveUsers());
						numberOfInactiveUsers += bug.getInactiveUsers();
					}
					users.getBugTrackers().add(dailyNewsgroupData);
				}
			}
			if (numberOfUsers > 0)
				users.setNumberOfUsers(numberOfUsers);
			if (numberOfActiveUsers > 0)
				users.setNumberOfActiveUsers(numberOfActiveUsers);
			if (numberOfInactiveUsers > 0)
				users.setNumberOfInactiveUsers(numberOfInactiveUsers);
		}
		return users;
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
		return "activeinactiveusersperbugtracker";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Active and Inactive Users Per Day Per Bug Tracker Provider";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of active and inactive users " +
				"per day for each bug tracker separately.";
	}

}
