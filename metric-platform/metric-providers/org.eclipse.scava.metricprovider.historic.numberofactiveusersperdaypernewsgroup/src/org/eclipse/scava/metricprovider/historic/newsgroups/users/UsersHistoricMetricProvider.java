/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.users;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.newsgroups.users.model.DailyNewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.users.model.NewsgroupsUsersHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.activeusers.ActiveUsersTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.activeusers.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.activeusers.model.NewsgroupsActiveUsersTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class UsersHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = UsersHistoricMetricProvider.class.getCanonicalName();

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
			if (communicationChannel instanceof Mbox) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {
		NewsgroupsUsersHistoricMetric users = new NewsgroupsUsersHistoricMetric();
		if (uses.size()==1) {
			NewsgroupsActiveUsersTransMetric activeUsers = ((ActiveUsersTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			int numberOfUsers = 0,
				numberOfActiveUsers = 0,
				numberOfInactiveUsers = 0;
			for (NewsgroupData newsgroup: activeUsers.getNewsgroups()) {
				if ((newsgroup.getUsers() > 0) || (newsgroup.getActiveUsers() > 0) || (newsgroup.getInactiveUsers() > 0)) {
					DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
					dailyNewsgroupData.setNewsgroupName(newsgroup.getNewsgroupName());
					if (newsgroup.getUsers() > 0)
						numberOfUsers += newsgroup.getUsers();
					if (newsgroup.getActiveUsers() > 0)
						numberOfActiveUsers += newsgroup.getActiveUsers();
					if (newsgroup.getInactiveUsers() > 0)
						numberOfInactiveUsers += newsgroup.getInactiveUsers();
					dailyNewsgroupData.setNumberOfUsers(newsgroup.getUsers());
					dailyNewsgroupData.setNumberOfActiveUsers(newsgroup.getActiveUsers());
					dailyNewsgroupData.setNumberOfInactiveUsers(newsgroup.getInactiveUsers());
					users.getNewsgroups().add(dailyNewsgroupData);
				}
			}
			if (numberOfUsers > 0)
			{
				users.setNumberOfUsers(numberOfUsers);
				users.setNumberOfActiveUsers(numberOfActiveUsers);
				users.setNumberOfInactiveUsers(numberOfInactiveUsers);
			}
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
		return "historic.newsgroups.users";
	}

	@Override
	public String getFriendlyName() {
		return "Number of users, active and inactive per day per newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of users, including active and inactive users " +
				"per day for each newsgroup separately.";
	}

}
