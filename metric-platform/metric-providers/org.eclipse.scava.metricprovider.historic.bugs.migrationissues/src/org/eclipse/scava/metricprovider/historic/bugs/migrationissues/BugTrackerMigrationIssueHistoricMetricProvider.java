/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.migrationissues;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.BugTrackerMigrationIssueHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.migrationissues.model.DailyBugTrackerMigrationData;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.BugTrackerMigrationIssueTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssue;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissues.model.BugTrackerMigrationIssueTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugTrackerMigrationIssueHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = BugTrackerMigrationIssueHistoricMetricProvider.class.getCanonicalName();

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
			System.err.println("Metric: " + IDENTIFIER + " failed to retrieve " + 
					"the transient metric it needs!");
			System.exit(-1);

		}
			BugTrackerMigrationIssueTransMetric migration = ((BugTrackerMigrationIssueTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			
			HashSet<String> bugIdSet = new HashSet<String>();
			for (BugTrackerMigrationIssue migrationIssue: migration.getBugTrackerMigrationIssues())
				bugIdSet.add(migrationIssue.getBugId());
			
			BugTrackerMigrationIssueHistoricMetric dailyMigration = new BugTrackerMigrationIssueHistoricMetric();
			
			Map<String, Integer> sumOfIssues = new HashMap<String, Integer>();
			
			for (BugTrackerMigrationIssue trackerMigrationIssue: migration.getBugTrackerMigrationIssues()) {
				int sum = 1;
				if (sumOfIssues.containsKey(trackerMigrationIssue.getBugTrackerId()))
					sum += sumOfIssues.get(trackerMigrationIssue.getBugTrackerId());
				sumOfIssues.put(trackerMigrationIssue.getBugTrackerId(), sum);
			}
			int totalIssues = (int) migration.getBugTrackerMigrationIssues().size();
			
			for (String bugTrackerId: sumOfIssues.keySet()) {
				DailyBugTrackerMigrationData migrationData = new DailyBugTrackerMigrationData();
				migrationData.setBugTrackerId(bugTrackerId);
				migrationData.setNumberOfIssues(sumOfIssues.get(bugTrackerId));
				dailyMigration.getDailyBugTrackerMigrationData().add(migrationData);
			}
			dailyMigration.setNumberOfIssues(totalIssues);
			int numberOfIssues = dailyMigration.getNumberOfIssues();
			int cumulativeNumberOfIssues = dailyMigration.getCumulativeNumberOfIssues();
			dailyMigration.setCumulativeNumberOfIssues(cumulativeNumberOfIssues + numberOfIssues);

			return dailyMigration;
		}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugTrackerMigrationIssueTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.bugs.migrationissues";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection in Bug Trackers per day per bug tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues in Bug Tracking Systems per day for each bug tracker.";
	}
}
