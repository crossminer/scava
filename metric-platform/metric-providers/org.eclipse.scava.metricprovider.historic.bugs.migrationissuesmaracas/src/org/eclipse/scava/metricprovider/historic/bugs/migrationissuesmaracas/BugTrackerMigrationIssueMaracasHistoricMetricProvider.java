/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracasHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.BugTrackerMigrationMaracasData;
import org.eclipse.scava.metricprovider.historic.bugs.migrationissuesmaracas.model.DailyBugTrackerMigrationMaracasData;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.BugTrackerMigrationIssueMaracasTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracas;
import org.eclipse.scava.metricprovider.trans.bugs.migrationissuesmaracas.model.BugTrackerMigrationIssueMaracasTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugTrackerMigrationIssueMaracasHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = BugTrackerMigrationIssueMaracasHistoricMetricProvider.class.getCanonicalName();

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

			BugTrackerMigrationIssueMaracasTransMetric migration = ((BugTrackerMigrationIssueMaracasTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			
			BugTrackerMigrationIssueMaracasHistoricMetric historicMigration = new BugTrackerMigrationIssueMaracasHistoricMetric();
			
			Map<String, DataStructure> processedData = new HashMap<String, DataStructure>();
			
			DataStructure data;
			DailyBugTrackerMigrationMaracasData dailyMigration;
			BugTrackerMigrationMaracasData migrationData;
			
			for (BugTrackerMigrationIssueMaracas trackerMigrationIssue: migration.getBugTrackerMigrationIssuesMaracas())
			{	
				if(processedData.containsKey(trackerMigrationIssue.getBugTrackerId()))
					data = processedData.get(trackerMigrationIssue.getBugTrackerId());
				else
					data = new DataStructure();
				
				data.increaseSumOfIssues();
				List<String> changes = trackerMigrationIssue.getChanges();
				List<Double> percentages = trackerMigrationIssue.getMatchingPercentage();
				for(int i=0; i<changes.size(); i++)
				{
					data.addChange(trackerMigrationIssue.getBugId(), changes.get(i), percentages.get(i));
				}
				processedData.put(trackerMigrationIssue.getBugTrackerId(), data);
			}
			
			for (String bugTrackerId: processedData.keySet()) {
				data= processedData.get(bugTrackerId);
				dailyMigration = new DailyBugTrackerMigrationMaracasData();
				dailyMigration.setBugTrackerId(bugTrackerId);
				dailyMigration.setNumberOfIssues(data.getSumOfIssues());
				
				
				
				for(String bugId : data.getBugsId())
				{
					dailyMigration.getBugsId().add(bugId);
					
					migrationData = new BugTrackerMigrationMaracasData();
					migrationData.setBugTrackerId(bugTrackerId);
					migrationData.setBugId(bugId);
					migrationData.getChangesAndMatchingPercentage().addAll(data.getChangesPerBugId(bugId));
					historicMigration.getBugTrackerMigrationMaracasData().add(migrationData);
				}		
				historicMigration.getDailyBugTrackerMigrationMaracasData().add(dailyMigration);
				
				
				
			}

			return historicMigration;
		}
	
	private class DataStructure {
		int sumOfIssues;
		Map<String, List<String>> changesMapping;
		
		public DataStructure() {
			sumOfIssues=0;
			changesMapping= new HashMap<String, List<String>>();
		}
		
		public void increaseSumOfIssues() {
			sumOfIssues++;
		}
		
		public void addChange(String bugId, String change, Double percentage) {
			List<String> changes;
			if(changesMapping.containsKey(bugId))
				changes=changesMapping.get(bugId);
			else
				changes=new ArrayList<String>(1);
			changes.add(change+"\t"+percentage);
			changesMapping.put(bugId, changes);
		}
		
		public Set<String> getBugsId() {
			return changesMapping.keySet();
		}
		
		public int getSumOfIssues() {
			return sumOfIssues;
		}
		
		public List<String> getChangesPerBugId(String bugId)
		{
			return changesMapping.get(bugId);
		}
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugTrackerMigrationIssueMaracasTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.bugs.migrationissuesmaracas";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection along with Maracas in Bug Trackers per day per bug tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues along with Maracas in Bug Tracking Systems per day for each bug tracker.";
	}
}
