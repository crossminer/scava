/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.status;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class StatusHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = StatusHistoricMetricProvider.class.getCanonicalName();

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
		BugsStatusHistoricMetric bugStatus = new BugsStatusHistoricMetric();
		if (uses.size()==1) {
			 BugsBugMetadataTransMetric usedBhm = ((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			 int numberOfResolvedClosedBugs = 0,
				 numberOfWontFixBugs = 0,
				 numberOfWorksForMeBugs = 0,
				 numberOfNonResolvedClosedBugs = 0,
				 numberOfInvalidBugs = 0,
				 numberOfFixedBugs = 0,
				 numberOfDuplicateBugs = 0;
			 for (BugData bugData: usedBhm.getBugData()) {
				 if (bugData.getStatus().toLowerCase().equals("resolved")||
						 (bugData.getStatus().toLowerCase().equals("closed")))
					 numberOfResolvedClosedBugs++;
				 if (bugData.getResolution().toLowerCase().equals("wontfix")
						 ||(bugData.getResolution().toLowerCase().equals("cantfix")))
						 	numberOfWontFixBugs++;
				 if (bugData.getResolution().toLowerCase().equals("worksforme"))
					 numberOfWorksForMeBugs++;
				 if (!bugData.getStatus().toLowerCase().equals("resolved")
						 &&(!bugData.getStatus().toLowerCase().equals("closed")))
					 numberOfNonResolvedClosedBugs++;
				 if (bugData.getResolution().toLowerCase().equals("invalid")
						 ||(bugData.getResolution().toLowerCase().equals("notabug")))
					 numberOfInvalidBugs++;
				 if ((bugData.getResolution().toLowerCase().equals("fixed"))
						 ||(bugData.getResolution().toLowerCase().equals("upstream"))
						 ||(bugData.getResolution().toLowerCase().equals("currentrelease"))
						 ||(bugData.getResolution().toLowerCase().equals("nextrelease"))
						 ||(bugData.getResolution().toLowerCase().equals("rawhide")))
						 	numberOfFixedBugs++;
				 if (bugData.getResolution().toLowerCase().equals("duplicate"))
					 numberOfDuplicateBugs++;
			 }
			 bugStatus.setNumberOfBugs(usedBhm.getBugData().size());
			 bugStatus.setNumberOfResolvedClosedBugs(numberOfResolvedClosedBugs);
			 bugStatus.setNumberOfWontFixBugs(numberOfWontFixBugs);
			 bugStatus.setNumberOfWorksForMeBugs(numberOfWorksForMeBugs);
			 bugStatus.setNumberOfNonResolvedClosedBugs(numberOfNonResolvedClosedBugs);
			 bugStatus.setNumberOfInvalidBugs(numberOfInvalidBugs);
			 bugStatus.setNumberOfFixedBugs(numberOfFixedBugs);
			 bugStatus.setNumberOfDuplicateBugs(numberOfDuplicateBugs);
		}
		return bugStatus;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(BugMetadataTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "historic.bugs.status";
	}

	@Override
	public String getFriendlyName() {
		return "Number of bugs per bug status per day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the total number of bugs that corresponds to each bug "
				+ "status, in bugs submitted every day, per bug tracker. There are 7 bug status "
				+ "(ResolvedClosed, WontFix, WorksForMe, NonResolvedClosed, Invalid, Fixed, Duplicate).";
	}
}
