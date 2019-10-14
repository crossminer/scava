/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.bugs.status;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.bugs.status.StatusHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.status.model.BugsStatusHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsChannelStatusFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "factoid.bugs.status";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Tracker Status";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "This plugin generates the factoid regarding bug status for bug trackers. "
				+ "For example, the number of fixed bugs, duplicate bugs etc"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(StatusHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		StatusHistoricMetricProvider bugStatusProvider = null;

		for (IMetricProvider m : this.uses) {
			if (m instanceof StatusHistoricMetricProvider) {
				bugStatusProvider = (StatusHistoricMetricProvider) m;
				continue;
			}
		}

		List<Pongo> bugStatusList = bugStatusProvider.getHistoricalMeasurements(context, project, delta.getDate(), delta.getDate());
		
		StringBuffer stringBuffer = new StringBuffer();
		
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		
		long numberOfBugs = getNumberBugs(bugStatusList);

		int numberOfResolvedBugs = getNumberOfResolvedClosedBugs(bugStatusList),
			numberOfNonResolvedBugs = getNumberOfNonResolvedClosedBugs(bugStatusList),
			numberOfFixedBugs = getNumberOfFixedBugs(bugStatusList),
			numberOfWorksForMeBugs = getNumberOfWorksForMeBugs(bugStatusList),
			numberOfWontFixBugs = getNumberOfWontFixBugs(bugStatusList),
			numberOfInvalidBugs = getNumberOfInvalidBugs(bugStatusList),
			numberOfDuplicateBugs = getNumberOfDuplicateBugs(bugStatusList);

		float percentageOfResolvedBugs=0;
		float percentageOfNonResolvedBugs=0;
		float percentageOfFixedBugs=0;
		float percentageOfWorksForMeBugs=0;
		float percentageOfWontFixBugs=0;
		float percentageOfInvalidBugs=0;
		float percentageOfDuplicateBugs=0;
		
		if(numberOfBugs>0)
		{
			percentageOfResolvedBugs = ( (float) 100 * numberOfResolvedBugs ) / numberOfBugs;
			percentageOfNonResolvedBugs = ( (float) 100 * numberOfNonResolvedBugs ) / numberOfBugs;
			percentageOfFixedBugs = ( (float) 100 * numberOfFixedBugs ) / numberOfBugs;
			percentageOfWorksForMeBugs = ( (float) 100 * numberOfWorksForMeBugs ) / numberOfBugs;
			percentageOfWontFixBugs = ( (float) 100 * numberOfWontFixBugs ) / numberOfBugs;
			percentageOfInvalidBugs = ( (float) 100 * numberOfInvalidBugs ) / numberOfBugs;
			percentageOfDuplicateBugs = ( (float) 100 * numberOfDuplicateBugs ) / numberOfBugs;
		
		
			if (percentageOfResolvedBugs > 75 ) {
				stringBuffer.append("Nearly all");
				factoid.setStars(StarRating.FOUR);
			}
			else if (percentageOfResolvedBugs > 50 ) {
				stringBuffer.append("Most");
				factoid.setStars(StarRating.THREE);
			}
			else if (percentageOfResolvedBugs > 25 ) {
				stringBuffer.append("Few");
				factoid.setStars(StarRating.TWO);
			} else {
				stringBuffer.append("Very few");
				factoid.setStars(StarRating.ONE);
			}
			stringBuffer.append(" bugs are resolved.\n");
			
			stringBuffer.append("In a total of ");
			stringBuffer.append(numberOfBugs);
			stringBuffer.append(" bugs, ");
			stringBuffer.append(numberOfResolvedBugs);
			stringBuffer.append(" are resolved (");
			stringBuffer.append(decimalFormat.format(percentageOfResolvedBugs));
			stringBuffer.append(" %) and ");
			stringBuffer.append(numberOfNonResolvedBugs);
			stringBuffer.append(" are non-resolved (");
			stringBuffer.append(decimalFormat.format(percentageOfNonResolvedBugs));
			stringBuffer.append(" %).\n");
			
			stringBuffer.append(numberOfFixedBugs);
			stringBuffer.append(" bugs are closed as fixed (");
			stringBuffer.append(decimalFormat.format(percentageOfFixedBugs));
			stringBuffer.append(" %), ");
			stringBuffer.append(numberOfWorksForMeBugs);
			stringBuffer.append(" as non-reproducible (");
			stringBuffer.append(decimalFormat.format(percentageOfWorksForMeBugs));
			stringBuffer.append(" %), ");
			stringBuffer.append(numberOfWontFixBugs);
			stringBuffer.append(" as won't fix (");
			stringBuffer.append(decimalFormat.format(percentageOfWontFixBugs));
			stringBuffer.append(" %), ");
			stringBuffer.append(numberOfInvalidBugs);
			stringBuffer.append(" as invalid (");
			stringBuffer.append(decimalFormat.format(percentageOfInvalidBugs));
			stringBuffer.append(" %) and ");
			stringBuffer.append(numberOfDuplicateBugs);
			stringBuffer.append(" as duplicates of other bugs (");
			stringBuffer.append(decimalFormat.format(percentageOfDuplicateBugs));
			stringBuffer.append(" %).\n"); 

		}
		else
		{
			factoid.setStars(StarRating.FOUR);
			stringBuffer.append("Zero bugs were found.\n"); 
		}
		factoid.setFactoid(stringBuffer.toString());

	}

	private long getNumberBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfBugs();
		}
		return 0;
	}

	private int getNumberOfDuplicateBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfDuplicateBugs();
		}
		return 0;
	}

	private int getNumberOfFixedBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfFixedBugs();
		}
		return 0;
	}

	private int getNumberOfInvalidBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfInvalidBugs();
		}
		return 0;
	}

	private int getNumberOfNonResolvedClosedBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfNonResolvedClosedBugs();
		}
		return 0;
	}

	private int getNumberOfResolvedClosedBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfResolvedClosedBugs();
		}
		return 0;
	}

	private int getNumberOfWontFixBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfWontFixBugs();
		}
		return 0;
	}

	private int getNumberOfWorksForMeBugs(List<Pongo> bugStatusList) {
		if ( bugStatusList.size() > 0 ) {
			BugsStatusHistoricMetric bugStatusPongo = 
					(BugsStatusHistoricMetric) bugStatusList.get(0);
			return bugStatusPongo.getNumberOfWorksForMeBugs();
		}
		return 0;
	}

}
