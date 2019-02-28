/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.newsgroups.articles;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.historic.newsgroups.articles.model.DailyNewsgroupData;
import org.eclipse.scava.metricprovider.historic.newsgroups.articles.model.NewsgroupsArticlesHistoricMetric;
import org.eclipse.scava.metricprovider.trans.newsgroups.articles.ArticlesTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.newsgroups.articles.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.articles.model.NewsgroupsArticlesTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.googlecode.pongo.runtime.Pongo;

public class ArticlesHistoricMetricProvider extends AbstractHistoricalMetricProvider {

	public final static String IDENTIFIER = ArticlesHistoricMetricProvider.class.getCanonicalName();

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
		for (CommunicationChannel communicationChannel: project.getCommunicationChannels()) {
			if (communicationChannel instanceof NntpNewsGroup) return true;
			if (communicationChannel instanceof Discussion) return true;
		}
		return false;
	}

	@Override
	public Pongo measure(Project project) {

		NewsgroupsArticlesHistoricMetric dailyNoa = new NewsgroupsArticlesHistoricMetric();
		if (uses.size()==1) {
			NewsgroupsArticlesTransMetric usedNoa = ((ArticlesTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			int sumOfArticles = 0,
				sumOfCumulativeArticles = 0;
			for (NewsgroupData newsgroup: usedNoa.getNewsgroups()) {
				int articles = newsgroup.getNumberOfArticles(),
					cumulativeArticles = newsgroup.getCumulativeNumberOfArticles();
				sumOfArticles += articles;
				sumOfCumulativeArticles += cumulativeArticles;
				if ( (articles > 0) || (cumulativeArticles > 0) ) {
					DailyNewsgroupData dailyNewsgroupData = new DailyNewsgroupData();
					dailyNewsgroupData.setNewsgroupName(newsgroup.getNewsgroupName());
					dailyNewsgroupData.setNumberOfArticles(articles);
					dailyNewsgroupData.setCumulativeNumberOfArticles(cumulativeArticles);
					dailyNoa.getNewsgroups().add(dailyNewsgroupData);
				}
			}
			dailyNoa.setNumberOfArticles(sumOfArticles);
			dailyNoa.setCumulativeNumberOfArticles(sumOfCumulativeArticles);
		}
		return dailyNoa;
	}
			
	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}
	
	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(ArticlesTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public String getShortIdentifier() {
		return "articlespernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Articles Per Day Per Newsgroup";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the number of articles per day for each newsgroup separately.";
	}
}
