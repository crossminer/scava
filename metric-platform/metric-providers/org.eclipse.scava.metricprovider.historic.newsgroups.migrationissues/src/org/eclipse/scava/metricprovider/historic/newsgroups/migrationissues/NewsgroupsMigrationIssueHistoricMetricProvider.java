/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.DailyNewsgroupsMigrationData;
import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissues.model.NewsgroupsMigrationIssueHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.NewsgroupsMigrationIssueTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssue;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissues.model.NewsgroupsMigrationIssueTransMetric;
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

public class NewsgroupsMigrationIssueHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = NewsgroupsMigrationIssueHistoricMetricProvider.class.getCanonicalName();

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

		NewsgroupsMigrationIssueTransMetric migration = ((NewsgroupsMigrationIssueTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		HashSet<Integer> threadIdSet = new HashSet<Integer>();
		for (NewsgroupsMigrationIssue migrationIssue: migration.getNewsgroupsMigrationIssues())
			threadIdSet.add(migrationIssue.getThreadId());
		
		NewsgroupsMigrationIssueHistoricMetric historicMigration = new NewsgroupsMigrationIssueHistoricMetric();
		
		Map<String, Integer> sumOfIssues = new HashMap<String, Integer>();
		Map<String, List<Integer>> allThreadsIds = new HashMap<String, List<Integer>>();
		
		for (NewsgroupsMigrationIssue newsgroupMigrationIssue: migration.getNewsgroupsMigrationIssues()) {
			int sum = 1;
			List<Integer> threadsIds;
			if (sumOfIssues.containsKey(newsgroupMigrationIssue.getNewsgroupName()))
			{
				sum += sumOfIssues.get(newsgroupMigrationIssue.getNewsgroupName());
				threadsIds=allThreadsIds.get(newsgroupMigrationIssue.getNewsgroupName());
			}
			else
				threadsIds=new ArrayList<Integer>(1);
			sumOfIssues.put(newsgroupMigrationIssue.getNewsgroupName(), sum);
			threadsIds.add(newsgroupMigrationIssue.getThreadId());
			allThreadsIds.put(newsgroupMigrationIssue.getNewsgroupName(), threadsIds);
		}
		
		for (String newsgroupName: sumOfIssues.keySet()) {
			DailyNewsgroupsMigrationData dailyData = new DailyNewsgroupsMigrationData();
			dailyData.setNewsgroupName(newsgroupName);
			dailyData.setNumberOfIssues(sumOfIssues.get(newsgroupName));
			dailyData.getThreadsId().addAll(allThreadsIds.get(newsgroupName));
			historicMigration.getDailyNewsgroupsMigrationData().add(dailyData);
		}
		return historicMigration;
	}

			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NewsgroupsMigrationIssueTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.newsgroups.migrationissues";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection in Newsgroups per day per bug tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Newsgroups per day for each bug tracker.";
	}
}
