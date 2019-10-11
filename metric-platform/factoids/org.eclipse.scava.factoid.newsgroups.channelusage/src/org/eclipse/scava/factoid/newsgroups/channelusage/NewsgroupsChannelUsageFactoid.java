/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.channelusage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.newsgroups.articles.ArticlesHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.articles.model.DailyNewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.articles.model.NewsgroupsArticlesHistoricMetric;
import org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.NewThreadsHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.NewsgroupsNewThreadsHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Date;
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

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelUsageFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "factoid.newsgroups.channelusage";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Usage";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "This plugin generates the factoid regarding usage data for newsgroups. "
				+ "For example, the total number of new articles or threads per year"; 
		// This method will NOT be removed in a later version.
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
		return Arrays.asList(ArticlesHistoricMetricProvider.IDENTIFIER,
							 NewThreadsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		ArticlesHistoricMetricProvider articlesProvider = null;
		NewThreadsHistoricMetricProvider threadsProvider = null;

		for (IMetricProvider m : this.uses) {
			if (m instanceof ArticlesHistoricMetricProvider) {
				articlesProvider = (ArticlesHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof NewThreadsHistoricMetricProvider) {
				threadsProvider = (NewThreadsHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = delta.getDate();
		Date start = (delta.getDate()).addDays(-365);
//		Date start=null, end=null;
//		try {
//			start = new Date("20040801");
//			end = new Date("20050801");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> articlesList = articlesProvider.getHistoricalMeasurements(context, project, start, end),
					threadsList = threadsProvider.getHistoricalMeasurements(context, project, start, end);
		
//		System.err.println("---USAGE===RETRIEVED PONGOLIST FOR " + articlesList.size() + " DAYS===---");

		Map<String, Integer> newsgroupArticles = new HashMap<String, Integer>();
		int numberOfArticles = parseArticlesPongos(articlesList, newsgroupArticles);

		Map<String, Integer> newsgroupThreads = new HashMap<String, Integer>();
		int numberOfThreads = parseThreadsPongos(threadsList, newsgroupThreads);

		long workingDaysInAYear= Math.round((articlesList.size()*250.0)/365.0); //Proportion of working days
		
		if ( (numberOfArticles > workingDaysInAYear) || (numberOfThreads > workingDaysInAYear) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfArticles > workingDaysInAYear) || (2 * numberOfThreads > workingDaysInAYear) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfArticles > workingDaysInAYear) || (4 * numberOfThreads > workingDaysInAYear) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}
		

		StringBuffer stringBuffer = new StringBuffer();
		
		stringBuffer.append("Over the last year of analysis, ");
		stringBuffer.append(numberOfArticles);
		stringBuffer.append(" new articles and ");
		stringBuffer.append(numberOfThreads);
		stringBuffer.append(" new threads have been posted in total.\n");

		end = delta.getDate();
		start = (delta.getDate()).addDays(-30);
		articlesList = articlesProvider.getHistoricalMeasurements(context, project, start, end);
		threadsList = threadsProvider.getHistoricalMeasurements(context, project, start, end);
		
		newsgroupArticles = new HashMap<String, Integer>();
		numberOfArticles = parseArticlesPongos(articlesList, newsgroupArticles);

		newsgroupThreads = new HashMap<String, Integer>();
		numberOfThreads = parseThreadsPongos(threadsList, newsgroupThreads);
		
		stringBuffer.append("Over the last month of analysis, ");
		stringBuffer.append(numberOfArticles);
		stringBuffer.append(" new bugs and ");
		stringBuffer.append(numberOfThreads);
		stringBuffer.append(" new threads have been posted.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

	private int parseArticlesPongos(List<Pongo> articlesList, Map<String, Integer> newsgroupArticles) {
		int sumOfArticles = 0;
		for (Pongo pongo: articlesList) {
			NewsgroupsArticlesHistoricMetric articlesPongo = (NewsgroupsArticlesHistoricMetric) pongo;
			for (DailyNewsgroupData newsgroup: articlesPongo.getNewsgroups()) {
				int numberOfArticles = newsgroup.getNumberOfArticles(); 
				sumOfArticles += numberOfArticles; 
				if (newsgroupArticles.containsKey(newsgroup.getNewsgroupName()))
					newsgroupArticles.put(newsgroup.getNewsgroupName(), 
										  newsgroupArticles.get(newsgroup.getNewsgroupName()) + 
										  numberOfArticles);
				else
					newsgroupArticles.put(newsgroup.getNewsgroupName(), numberOfArticles);
			}
		}
		return sumOfArticles;
	}
		
	private int parseThreadsPongos(List<Pongo> threadsList, Map<String, Integer> newsgroupThreads) {
		int sumOfThreads = 0;
		for (Pongo pongo: threadsList) {
			NewsgroupsNewThreadsHistoricMetric threadsPongo = (NewsgroupsNewThreadsHistoricMetric) pongo;
			for ( org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData 
					newsgroup: threadsPongo.getNewsgroups()) {
				int numberOfThreads = newsgroup.getNumberOfNewThreads();
				sumOfThreads += numberOfThreads;
				if (newsgroupThreads.containsKey(newsgroup.getNewsgroupName()))
					newsgroupThreads.put(newsgroup.getNewsgroupName(), 
										 newsgroupThreads.get(newsgroup.getNewsgroupName()) + 
										 numberOfThreads);
				else
					newsgroupThreads.put(newsgroup.getNewsgroupName(), numberOfThreads);
			}
		}
		return sumOfThreads;
	}
	
}
