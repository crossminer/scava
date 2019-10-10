/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.DailyNewsgroupMigrationMaracasData;
import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracasHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationMaracasData;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.NewsgroupsMigrationIssueMaracasTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupMigrationIssueMaracas;
import org.eclipse.scava.metricprovider.trans.newsgroups.migrationissuesmaracas.model.NewsgroupsMigrationIssueMaracasTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupMigrationIssueMaracasHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = NewsgroupMigrationIssueMaracasHistoricMetricProvider.class.getCanonicalName();

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

			NewsgroupsMigrationIssueMaracasTransMetric migration = ((NewsgroupsMigrationIssueMaracasTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			
			NewsgroupMigrationIssueMaracasHistoricMetric historicMigration = new NewsgroupMigrationIssueMaracasHistoricMetric();
			
			Map<String, DataStructure> processedData = new HashMap<String, DataStructure>();
			
			DataStructure data;
			DailyNewsgroupMigrationMaracasData dailyMigration;
			NewsgroupMigrationMaracasData migrationData;
			
			for (NewsgroupMigrationIssueMaracas trackerMigrationIssue: migration.getNewsgroupsMigrationIssuesMaracas())
			{	
				if(processedData.containsKey(trackerMigrationIssue.getNewsgroupName()))
					data = processedData.get(trackerMigrationIssue.getNewsgroupName());
				else
					data = new DataStructure();
				
				data.increaseSumOfIssues();
				List<String> changes = trackerMigrationIssue.getChanges();
				List<Double> percentages = trackerMigrationIssue.getMatchingPercentage();
				for(int i=0; i<changes.size(); i++)
				{
					data.addChange(trackerMigrationIssue.getThreadId(), changes.get(i), percentages.get(i));
				}
				processedData.put(trackerMigrationIssue.getNewsgroupName(), data);
			}
			
			for (String newsgroupName: processedData.keySet()) {
				data= processedData.get(newsgroupName);
				dailyMigration = new DailyNewsgroupMigrationMaracasData();
				dailyMigration.setNewsgroupName(newsgroupName);
				dailyMigration.setNumberOfIssues(data.getSumOfIssues());
				
				
				
				for(String threadId : data.getThreadsId())
				{
					dailyMigration.getThreadsId().add(threadId);
					
					migrationData = new NewsgroupMigrationMaracasData();
					migrationData.setNewsgroupName(newsgroupName);
					migrationData.setThreadId(threadId);
					migrationData.getChangesAndMatchingPercentage().addAll(data.getChangesPerBugId(threadId));
					historicMigration.getNewsgroupMigrationMaracasData().add(migrationData);
				}		
				historicMigration.getDailyNewsgroupMigrationMaracasData().add(dailyMigration);
				
				
				
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
		
		public void addChange(String threadId, String change, Double percentage) {
			List<String> changes;
			if(changesMapping.containsKey(threadId))
				changes=changesMapping.get(threadId);
			else
				changes=new ArrayList<String>(1);
			changes.add(change+"\t"+percentage);
			changesMapping.put(threadId, changes);
		}
		
		public Set<String> getThreadsId() {
			return changesMapping.keySet();
		}
		
		public int getSumOfIssues() {
			return sumOfIssues;
		}
		
		public List<String> getChangesPerBugId(String threadId)
		{
			return changesMapping.get(threadId);
		}
	}
	
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NewsgroupsMigrationIssueMaracasTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.newsgroups.migrationissuesmaracas";
	}

	@Override
	public String getFriendlyName() {
		return "Migration Issues Detection along with Maracas in articles per day per newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric detects migration issues along with Maracas in newsgroups per day for each newsgroup.";
	}
}
