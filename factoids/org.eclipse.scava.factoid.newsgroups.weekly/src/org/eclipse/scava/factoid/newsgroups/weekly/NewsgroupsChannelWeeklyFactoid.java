/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.weekly;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.newsgroups.dailyrequestsreplies.DailyRequestsRepliesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.dailyrequestsreplies.model.DayArticles;
import org.eclipse.scava.metricprovider.trans.newsgroups.dailyrequestsreplies.model.NewsgroupsDailyRequestsRepliesTransMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

public class NewsgroupsChannelWeeklyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelWeekly";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Weekly";
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
		return Arrays.asList(DailyRequestsRepliesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		NewsgroupsDailyRequestsRepliesTransMetric dailyRequestsRepliesTransMetric = 
				((DailyRequestsRepliesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		float uniformPercentageOfComments = ( (float) 100 ) / 7;
		float maxPercentageOfArticles = 0,
			  minPercentageOfArticles = 101;
		String maxPercentageDay = "",
			   minPercentageDay = "";
		
		for (DayArticles dayArticles: dailyRequestsRepliesTransMetric.getDayArticles()) {
			if ( dayArticles.getPercentageOfArticles() > maxPercentageOfArticles ) {
				maxPercentageOfArticles = dayArticles.getPercentageOfArticles();
				maxPercentageDay = dayArticles.getName();
			}
			if ( dayArticles.getPercentageOfArticles() < minPercentageOfArticles ) {
				minPercentageOfArticles = dayArticles.getPercentageOfArticles(); 
				minPercentageDay = dayArticles.getName();
			}
		}
		
		if ( maxPercentageOfArticles < 2 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( maxPercentageOfArticles < 3 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.THREE);
		} else if ( maxPercentageOfArticles < 4 * uniformPercentageOfComments ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}

		StringBuffer stringBuffer = new StringBuffer();
		DecimalFormat decimalFormat = new DecimalFormat("#.##");

		stringBuffer.append("The busiest day of the week is ");
		stringBuffer.append(maxPercentageDay);
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(maxPercentageOfArticles));
		stringBuffer.append("% of articles), while the least busy day is ");
		stringBuffer.append(minPercentageDay);
		stringBuffer.append(" (");
		stringBuffer.append(decimalFormat.format(minPercentageOfArticles));
		stringBuffer.append("%) (");
		boolean first = true;
		for (DayArticles dayArticles: dailyRequestsRepliesTransMetric.getDayArticles()) {
			if ( (!dayArticles.getName().equals(maxPercentageDay)) &&
				 (!dayArticles.getName().equals(minPercentageDay)) ) {
				if (!first)
					stringBuffer.append(", ");
				stringBuffer.append(dayArticles.getName());
				stringBuffer.append(": ");
				stringBuffer.append(decimalFormat.format(dayArticles.getPercentageOfArticles()));
				stringBuffer.append("%");
				first = false;
			}
		}
		stringBuffer.append(").\n");

		factoid.setFactoid(stringBuffer.toString());
	
	}

}
