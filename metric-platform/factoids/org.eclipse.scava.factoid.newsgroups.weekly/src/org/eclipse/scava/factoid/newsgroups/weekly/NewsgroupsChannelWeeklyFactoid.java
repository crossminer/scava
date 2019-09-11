/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.weekly;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
import org.eclipse.scava.repository.model.cc.eclipseforums.EclipseForum;
import org.eclipse.scava.repository.model.cc.irc.Irc;
import org.eclipse.scava.repository.model.cc.mbox.Mbox;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.cc.sympa.SympaMailingList;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

public class NewsgroupsChannelWeeklyFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "factoid.newsgroups.weekly";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Weekly";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "This plugin generates the factoid regarding weekly user engagement for newsgroups. "
				+ "For example, the average number of comments per week. This can be used to "
				+ "present the most and least busy week in terms of engagement for a particular "
				+ "project"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
			if (communicationChannel instanceof EclipseForum) return true;
			if (communicationChannel instanceof SympaMailingList) return true;
			if (communicationChannel instanceof Irc) return true;
			if (communicationChannel instanceof Mbox) return true;
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
		List<String> maxPercentageDay = new ArrayList<String>(1);
		List<String> minPercentageDay = new ArrayList<String>(1);
		
		for (DayArticles dayArticles: dailyRequestsRepliesTransMetric.getDayArticles()) {
			if ( dayArticles.getPercentageOfArticles() >= maxPercentageOfArticles )
			{
				if ( dayArticles.getPercentageOfArticles() >= maxPercentageOfArticles )
				{
					maxPercentageOfArticles = dayArticles.getPercentageOfArticles();
					maxPercentageDay.clear();
				}
				maxPercentageDay.add(dayArticles.getName());
			}
			if ( dayArticles.getPercentageOfArticles() <= minPercentageOfArticles )
			{
				if ( dayArticles.getPercentageOfArticles() <= minPercentageOfArticles )
				{
					minPercentageOfArticles = dayArticles.getPercentageOfArticles();
					minPercentageDay.clear();
				}
				minPercentageDay.add(dayArticles.getName());
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

		if(maxPercentageOfArticles==(float) 0.0)
		{
			stringBuffer.append("None of the days is busy");
			stringBuffer.append(" (");
			stringBuffer.append(decimalFormat.format(maxPercentageOfArticles));
			stringBuffer.append("% of articles).\n");
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
			stringBuffer.append(decimalFormat.format(maxPercentageOfArticles));
			stringBuffer.append("% of articles)");
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
			stringBuffer.append(decimalFormat.format(minPercentageOfArticles));
			stringBuffer.append("%) Specifially, each day of the week has the the following percentages. ");
			boolean first = true;
			for (DayArticles dayArticles: dailyRequestsRepliesTransMetric.getDayArticles()) {
					if (!first)
						stringBuffer.append(", ");
					stringBuffer.append(dayArticles.getName());
					stringBuffer.append(": ");
					stringBuffer.append(decimalFormat.format(dayArticles.getPercentageOfArticles()));
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
