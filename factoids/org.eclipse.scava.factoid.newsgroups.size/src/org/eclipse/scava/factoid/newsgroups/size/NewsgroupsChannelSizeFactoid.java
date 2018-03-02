/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.newsgroups.size;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;

import com.googlecode.pongo.runtime.Pongo;

public class NewsgroupsChannelSizeFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "NewsgroupChannelSize";
	}

	@Override
	public String getFriendlyName() {
		return "Newsgroup Channel Size";
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
		return Arrays.asList(ArticlesHistoricMetricProvider.IDENTIFIER,
							 NewThreadsHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.NEWSGROUPS);
		factoid.setName(getFriendlyName());

		ArticlesHistoricMetricProvider articlesProvider = new ArticlesHistoricMetricProvider();
		NewThreadsHistoricMetricProvider newThreadsProvider = new NewThreadsHistoricMetricProvider();

		for (IMetricProvider m : this.uses) {
			if (m instanceof ArticlesHistoricMetricProvider) {
				articlesProvider = (ArticlesHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof NewThreadsHistoricMetricProvider) {
				newThreadsProvider = (NewThreadsHistoricMetricProvider) m;
				continue;
			}
		}

		Date end = new Date();
		Date start = (new Date()).addDays(-30);
//		Date start=null, end=null;
//		try {
//			start = new Date("20040801");
//			end = new Date("20050801");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> articlesList = articlesProvider.getHistoricalMeasurements(context, project, start, end),
					newThreadsList = newThreadsProvider.getHistoricalMeasurements(context, project, start, end);
		
//		System.err.println("---SIZE===RETRIEVED PONGOLIST FOR " + articlesList.size() + " DAYS===---");
		
		Map<String, Integer> trackerArticles = new HashMap<String, Integer>();
		int numberOfArticles = getCumulativeNumberOfArticles(articlesList, trackerArticles);

		Map<String, Integer> trackerNewThreads = new HashMap<String, Integer>();
		int numberOfNewThreads = getCumulativeNumberOfThreads(newThreadsList, trackerNewThreads);

		int threshold = 1000;
		
		if ( (numberOfArticles > 10 * threshold) || (numberOfNewThreads > threshold) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfArticles > 10 * threshold) || (2 * numberOfNewThreads > threshold) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfArticles > 10 * threshold) || (4 * numberOfNewThreads > threshold) ) {
			factoid.setStars(StarRating.TWO);
		} else
			factoid.setStars(StarRating.ONE);
		
		StringBuffer stringBuffer = new StringBuffer();
		
		int articles = 0, threads = 0;
		for (String tracker: sortByKeys(trackerArticles)) {
			articles += trackerArticles.get(tracker);
			threads += trackerNewThreads.get(tracker);
		}
		stringBuffer.append("The newsgroups of the project contain ");
		stringBuffer.append(threads);
		stringBuffer.append(" threads and ");
		stringBuffer.append(articles);
		stringBuffer.append(" articles, in total.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

	private int getCumulativeNumberOfArticles(List<Pongo> newArticlesList, Map<String, Integer> trackerArticles) {
		int sum = 0;
		if ( newArticlesList.size() > 0 ) {
			NewsgroupsArticlesHistoricMetric newArticlesPongo = 
					(NewsgroupsArticlesHistoricMetric) newArticlesList.get(newArticlesList.size()-1);
			for (DailyNewsgroupData newsgroupData: newArticlesPongo.getNewsgroups()) {
				int articles = newsgroupData.getCumulativeNumberOfArticles();
				trackerArticles.put(newsgroupData.getNewsgroupName(), articles);
				sum += articles;
			}
		}
		return sum;
	}
	
	private int getCumulativeNumberOfThreads(List<Pongo> newThreadsList, Map<String, Integer> trackerNewThreads) {
		int sum = 0;
		for (Pongo pongo: newThreadsList) {
			NewsgroupsNewThreadsHistoricMetric commentsPongo = (NewsgroupsNewThreadsHistoricMetric) pongo;
			for (org.eclipse.scava.metricprovider.historic.newsgroups.newthreads.model.DailyNewsgroupData 
					newsgroupData: commentsPongo.getNewsgroups()) {
				int threads = newsgroupData.getCumulativeNumberOfNewThreads();
				trackerNewThreads.put(newsgroupData.getNewsgroupName(), threads);
				sum += threads;
			}
		}
		return sum;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<String> sortByKeys(Map<String, ?> map) {
		return new TreeSet(map.keySet());
	}

}
