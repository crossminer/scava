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
package org.eclipse.crossmeter.metricprovider.historic.bugs.status;

import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class StatusHistoricMetricProvider extends AbstractHistoricalMetricProvider{

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.bugs.status";

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
		return "bugstatus";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Bugs Per Status";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of bugs that correspond to a status value.";
	}
}
