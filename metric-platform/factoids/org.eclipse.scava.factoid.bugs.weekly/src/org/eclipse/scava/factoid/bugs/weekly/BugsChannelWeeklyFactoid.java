/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.bugs.weekly;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.DailyRequestsRepliesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.model.BugsDailyRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.model.DayComments;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

public class BugsChannelWeeklyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "factoid.bugs.weekly";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Tracker Weekly";
	}

	@Override
	public String getSummaryInformation() {
		return "This plugin generates the factoid regarding weekly user engagements for bug trackers. "
				+ "For example, the average number of bug comments per week. This can be used to present "
				+ "the most and least busy week in terms of engagement for a particular project."; 
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(DailyRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
		factoid.setName(getFriendlyName());

		BugsDailyRequestsRepliesTransMetric dailyRequestsRepliesTransMetric = 
				((DailyRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float uniformPercentageOfComments = ( (float) 100 ) / 7;
		float maxPercentageOfComments = 0,
			  minPercentageOfComments = 101;
		List<String> maxPercentageDay = new ArrayList<String>(1);
		List<String> minPercentageDay = new ArrayList<String>(1);
		
		for (DayComments dayComments: dailyRequestsRepliesTransMetric.getDayComments())
		{
			if ( dayComments.getPercentageOfComments() >= maxPercentageOfComments ) {
				if ( dayComments.getPercentageOfComments() > maxPercentageOfComments )
				{
					maxPercentageOfComments = dayComments.getPercentageOfComments();
					maxPercentageDay.clear();
				}
				maxPercentageDay.add(dayComments.getName());
			}
			if(dayComments.getPercentageOfComments() <= minPercentageOfComments)
			{
				if (dayComments.getPercentageOfComments() < minPercentageOfComments)
				{
					minPercentageOfComments = dayComments.getPercentageOfComments();
					minPercentageDay.clear();
				}
				minPercentageDay.add(dayComments.getName());
			}
		}
		
		if ( maxPercentageOfComments < 2 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( maxPercentageOfComments < 3 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.THREE);
		} else if ( maxPercentageOfComments < 4 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		if(maxPercentageOfComments==(float) 0.0)
		{
			stringBuffer.append("None of the days is busy");
			stringBuffer.append(" (");
			stringBuffer.append(decimalFormat.format(maxPercentageOfComments));
			stringBuffer.append("% of reports and comments every day).\n");
		}
		else
		{
			if(maxPercentageDay.size()==1)
			{
				stringBuffer.append("The busiest day of the week is ");
				stringBuffer.append(maxPercentageDay.get(0));
			}
			else
			{
				stringBuffer.append("The busiest days of the week are ");
				readLists(stringBuffer, maxPercentageDay);
			}
			stringBuffer.append(" (");
			stringBuffer.append(decimalFormat.format(maxPercentageOfComments));
			stringBuffer.append("% of reports and comments)");
			if(minPercentageDay.size()==1)
			{
				stringBuffer.append(", while the least busy day is ");
				stringBuffer.append(minPercentageDay.get(0));
			}
			else
			{
				stringBuffer.append(", while the least busy days are ");
				readLists(stringBuffer, minPercentageDay);
			}
			stringBuffer.append(" (");
			stringBuffer.append(decimalFormat.format(minPercentageOfComments));
			stringBuffer.append("%) Specifially, each day of the week has the the following percentages. ");
			boolean first = true;
			for (DayComments dayComments: dailyRequestsRepliesTransMetric.getDayComments()) {
					if (!first)
						stringBuffer.append(", ");
					stringBuffer.append(dayComments.getName());
					stringBuffer.append(": ");
					stringBuffer.append(decimalFormat.format(dayComments.getPercentageOfComments()));
					stringBuffer.append("%");
					first = false;
			}
			stringBuffer.append(".\n");
		}
		factoid.setFactoid(stringBuffer.toString());

	}
	
	private void readLists(StringBuffer stringBuffer, List<String> list)
	{
		boolean first = true;
		
		for(String element : list)
		{
			if (!first)
				stringBuffer.append(", ");
			stringBuffer.append(element);
			first=false;
		}
	}

}
