/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.bugs.hourly;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.HourlyRequestsRepliesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.BugsHourlyRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

public class BugsChannelHourlyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelHourly";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Tracker Hourly";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(HourlyRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		BugsHourlyRequestsRepliesTransMetric hourlyRequestsRepliesTransMetric = 
				((HourlyRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float uniformPercentageOfComments = ( (float) 100 ) / 24;
		float maxPercentageOfComments = 0,
			  minPercentageOfComments = 0,
			  workingHoursSum = 0,
			  ourOfWorkingHoursSum = 0;
		
		for (HourComments hourComments: hourlyRequestsRepliesTransMetric.getHourComments()) {
			if ( hourComments.getPercentageOfComments() > maxPercentageOfComments )
				maxPercentageOfComments = hourComments.getPercentageOfComments();
			if ( hourComments.getPercentageOfComments() < minPercentageOfComments )
				minPercentageOfComments = hourComments.getPercentageOfComments(); 
			if ( hourComments.getHour().equals("08:00") || hourComments.getHour().equals("09:00")
			  || hourComments.getHour().equals("10:00") || hourComments.getHour().equals("11:00")
			  || hourComments.getHour().equals("12:00") || hourComments.getHour().equals("13:00")
			  || hourComments.getHour().equals("14:00") || hourComments.getHour().equals("15:00")
			  || hourComments.getHour().equals("16:00") )
				workingHoursSum += hourComments.getPercentageOfComments();
			else
				ourOfWorkingHoursSum += hourComments.getPercentageOfComments();
		}
		
		if ( maxPercentageOfComments < 2 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( maxPercentageOfComments < 4 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.THREE);
		} else if ( maxPercentageOfComments < 6 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The number of comments, requests or replies, ");
		if ( maxPercentageOfComments - minPercentageOfComments < uniformPercentageOfComments )
			stringBuffer.append("does not depend");
		else
			stringBuffer.append("largely depends");
		stringBuffer.append(" on the hour of the day.\nThere is");
		if ( Math.abs( ( workingHoursSum / 9 ) - ( ourOfWorkingHoursSum / 15 ) ) < uniformPercentageOfComments ) 
			stringBuffer.append(" no ");
		else
			stringBuffer.append(" ");
		stringBuffer.append("significant difference between the number of comments" +
							" within as opposed to out of working hours.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

}
