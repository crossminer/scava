/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.newbugs;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.DailyBugData;
import org.eclipse.scava.metricprovider.trans.bugs.newbugs.NewBugsTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.newbugs.model.BugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.newbugs.model.BugsNewBugsTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class NewBugsHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.bugs.newbugs";

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
	public String getShortIdentifier() {
		return "bugs";
	}

	@Override
	public String getFriendlyName() {
		return "Daily Number Of New Bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of new bugs reported per day during the period of interest. " +
				"A small number of bug reports can indicate either a bug-free, robust project " +
				"or a project with a small/inactive user community.";
	}
	
	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		BugsNewBugsHistoricMetric dailyNonbb = new BugsNewBugsHistoricMetric();
		if (uses.size()==1) {
			BugsNewBugsTransMetric newBugsMetric = 
					((NewBugsTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			int numberOfNewBugs = 0,
				cumulativeNumberOfNewBugs = 0;
			for (BugTrackerData bugTracker: newBugsMetric.getBugTrackerData()) {
				int numberOfBugs = bugTracker.getNumberOfBugs(),
					cumulativeNumberOfBugs = bugTracker.getCumulativeNumberOfBugs();
				numberOfNewBugs += numberOfBugs;
				cumulativeNumberOfNewBugs += cumulativeNumberOfBugs;
				if ( (numberOfBugs>0) || (cumulativeNumberOfNewBugs>0) ) {
					DailyBugData bugData = new DailyBugData();
					bugData.setBugTrackerId(bugTracker.getBugTrackerId());
					bugData.setNumberOfBugs(numberOfBugs);
					bugData.setCumulativeNumberOfBugs(cumulativeNumberOfBugs);
					dailyNonbb.getBugs().add(bugData);
				}
			}
			dailyNonbb.setNumberOfBugs(numberOfNewBugs);
			dailyNonbb.setCumulativeNumberOfBugs(cumulativeNumberOfNewBugs);
			if (numberOfNewBugs > 0)
				System.err.println("\t\t!!BugsNewBugsHistoricMetric stored: " + numberOfNewBugs + " bugs!!");
		}
		return dailyNonbb;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NewBugsTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}
}
