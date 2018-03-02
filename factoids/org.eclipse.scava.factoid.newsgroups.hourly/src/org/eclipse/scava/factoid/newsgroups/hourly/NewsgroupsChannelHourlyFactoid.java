/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.hourly;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.HourlyRequestsRepliesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.HourArticles;
import org.eclipse.scava.metricprovider.trans.newsgroups.hourlyrequestsreplies.model.NewsgroupsHourlyRequestsRepliesTransMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

public class NewsgroupsChannelHourlyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelHourly";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Hourly";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
		}
		return false;
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

		NewsgroupsHourlyRequestsRepliesTransMetric hourlyRequestsRepliesTransMetric = 
				((HourlyRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float uniformPercentageOfComments = ( (float) 100 ) / 24;
		float maxPercentageOfArticles = 0,
			  minPercentageOfArticles = 100,
			  workingHoursSum = 0,
			  ourOfWorkingHoursSum = 0;
		
		for (HourArticles hourArticles: hourlyRequestsRepliesTransMetric.getHourArticles()) {
			if ( hourArticles.getPercentageOfArticles() > maxPercentageOfArticles )
				maxPercentageOfArticles = hourArticles.getPercentageOfArticles();
			if ( hourArticles.getPercentageOfArticles() < minPercentageOfArticles )
				minPercentageOfArticles = hourArticles.getPercentageOfArticles(); 
			if ( hourArticles.getHour().equals("08:00") || hourArticles.getHour().equals("09:00")
			  || hourArticles.getHour().equals("10:00") || hourArticles.getHour().equals("11:00")
			  || hourArticles.getHour().equals("12:00") || hourArticles.getHour().equals("13:00")
			  || hourArticles.getHour().equals("14:00") || hourArticles.getHour().equals("15:00")
			  || hourArticles.getHour().equals("16:00") )
				workingHoursSum += hourArticles.getPercentageOfArticles();
			else
				ourOfWorkingHoursSum += hourArticles.getPercentageOfArticles();
		}
		if ( maxPercentageOfArticles < 2 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( maxPercentageOfArticles < 4 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.THREE);
		} else if ( maxPercentageOfArticles < 6 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("The number of articles, requests or replies, ");
		if ( maxPercentageOfArticles - minPercentageOfArticles < uniformPercentageOfComments )
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
