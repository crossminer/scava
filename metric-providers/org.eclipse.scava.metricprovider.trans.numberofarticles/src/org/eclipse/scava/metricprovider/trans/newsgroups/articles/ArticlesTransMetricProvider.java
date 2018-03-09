/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.newsgroups.articles;

import java.util.Collections;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.newsgroups.articles.model.NewsgroupData;
import org.eclipse.scava.metricprovider.trans.newsgroups.articles.model.NewsgroupsArticlesTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelDelta;
import org.eclipse.scava.platform.delta.communicationchannel.CommunicationChannelProjectDelta;
import org.eclipse.scava.platform.delta.communicationchannel.PlatformCommunicationChannelManager;
import org.eclipse.scava.repository.model.CommunicationChannel;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.cc.nntp.NntpNewsGroup;
import org.eclipse.scava.repository.model.sourceforge.Discussion;

import com.mongodb.DB;

public class ArticlesTransMetricProvider implements ITransientMetricProvider<NewsgroupsArticlesTransMetric>{

	protected PlatformCommunicationChannelManager communicationChannelManager;

	@Override
	public String getIdentifier() {
		return ArticlesTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "newsgrouparticles";
	}

	@Override
	public String getFriendlyName() {
		return "Number of articles";
	}

	@Override
	public String getSummaryInformation() {
		return "Holds the number of articles";
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
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.communicationChannelManager = context.getPlatformCommunicationChannelManager();
	}

	@Override
	public NewsgroupsArticlesTransMetric adapt(DB db) {
		return new NewsgroupsArticlesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, NewsgroupsArticlesTransMetric db) {
		System.err.println("ArticleTransMetric started!");
		CommunicationChannelProjectDelta delta = projectDelta.getCommunicationChannelDelta();
		for ( CommunicationChannelDelta communicationChannelDelta: delta.getCommunicationChannelSystemDeltas()) {
			CommunicationChannel communicationChannel = communicationChannelDelta.getCommunicationChannel();
			String communicationChannelName;
			if (!(communicationChannel instanceof NntpNewsGroup))
				communicationChannelName = communicationChannel.getUrl();
			else {
				NntpNewsGroup newsgroup = (NntpNewsGroup) communicationChannel;
				communicationChannelName = newsgroup.getNewsGroupName();
			}
			NewsgroupData newsgroupData = db.getNewsgroups().findOneByNewsgroupName(communicationChannelName);
			if (newsgroupData == null) {
				newsgroupData = new NewsgroupData();
				newsgroupData.setNewsgroupName(communicationChannelName);
				db.getNewsgroups().add(newsgroupData);
			} 
			int articles = communicationChannelDelta.getArticles().size();
			newsgroupData.setNumberOfArticles(articles);
			int cumulativeArticles = newsgroupData.getCumulativeNumberOfArticles();
			newsgroupData.setCumulativeNumberOfArticles(cumulativeArticles + articles);
			System.err.println("ArticleTransMetric just stored " + articles + " (" + cumulativeArticles +  ") articles");
			db.sync();
		}
	}
}
