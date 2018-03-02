/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.newbugs;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.newbugs.model.BugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.newbugs.model.BugsNewBugsTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class NewBugsTransMetricProvider implements ITransientMetricProvider<BugsNewBugsTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return NewBugsTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsNewBugsTransMetric adapt(DB db) {
		return new BugsNewBugsTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsNewBugsTransMetric db) {
		BugTrackingSystemProjectDelta systemDelta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta delta: systemDelta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = delta.getBugTrackingSystem();
			String bugTrackerId = bugTracker.getOSSMeterId();
			
			//BugTrackerData data = db.getBugTrackerData().findOneByBugTrackerId(bugTrackerId);
			Iterator<BugTrackerData> it = db.getBugTrackerData().findByBugTrackerId(bugTrackerId).iterator();
			BugTrackerData data = null;
			if (it.hasNext()) {
			    data = it.next();
			}			    
			    
			if ( data == null ) {
			    data = new BugTrackerData();
			    data.setBugTrackerId(bugTrackerId);
			    db.getBugTrackerData().add(data);
			}
			int numberOfBugs = delta.getNewBugs().size();
			data.setNumberOfBugs(numberOfBugs);
			int cumulativeNumberOfBugs = data.getCumulativeNumberOfBugs();
			data.setCumulativeNumberOfBugs(cumulativeNumberOfBugs + numberOfBugs);
			if (delta.getNewBugs().size()>0)
				System.err.println("\t\t!!BugsNewBugsTransMetric stored: " + delta.getNewBugs().size() + " bugs!!");

			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "newbugs";
	}

	@Override
	public String getFriendlyName() {
		return "Number of bugs";
	}

	@Override
	public String getSummaryInformation() {
		return "The number of bugs over time. Lorum ipsum.";
	}

  

}
