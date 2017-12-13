/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    James Williams - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.historic.commitsovertime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.crossmeter.metricprovider.historic.commitsovertime.model.CommitsOverTime;
import org.eclipse.crossmeter.metricprovider.trans.commits.CommitsTransientMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.commits.model.Commits;
import org.eclipse.crossmeter.metricprovider.trans.commits.model.RepositoryData;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class CommitsOverTimeHistoricMetricProvider extends
		AbstractHistoricalMetricProvider {

	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return CommitsOverTimeHistoricMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "commitsovertime";
	}

	@Override
	public String getFriendlyName() {
		return "Commits over time";
	}

	@Override
	public String getSummaryInformation() {
		return "The total number of commits over time.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(CommitsTransientMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
	}

	@Override
	public Pongo measure(Project project) {
		
		List<RepositoryData> repos = new ArrayList<RepositoryData>();
		
		for (IMetricProvider used : uses) {
			Commits usedCommits =  ((CommitsTransientMetricProvider)used).adapt(context.getProjectDB(project));
			for (RepositoryData rd : usedCommits.getRepositories()) {
				repos.add(rd);
			}
		}
		
		CommitsOverTime cot = new CommitsOverTime();
		
		for (RepositoryData rd : repos) {
			org.eclipse.crossmeter.metricprovider.historic.commitsovertime.model.RepositoryData histRd = new org.eclipse.crossmeter.metricprovider.historic.commitsovertime.model.RepositoryData();
			histRd.setUrl(rd.getUrl());
			histRd.setNumberOfCommits(rd.getTotalCommits());
			cot.getRepositories().add(histRd);
		}
		
		return cot;
	}

}
