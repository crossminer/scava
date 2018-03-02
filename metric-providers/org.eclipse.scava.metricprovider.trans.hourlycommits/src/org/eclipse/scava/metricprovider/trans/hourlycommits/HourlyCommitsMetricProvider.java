/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.hourlycommits;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.commits.CommitsTransientMetricProvider;
import org.eclipse.scava.metricprovider.trans.commits.model.CommitData;
import org.eclipse.scava.metricprovider.trans.commits.model.Commits;
import org.eclipse.scava.metricprovider.trans.commits.model.RepositoryData;
import org.eclipse.scava.metricprovider.trans.hourlycommits.model.Hour;
import org.eclipse.scava.metricprovider.trans.hourlycommits.model.HourlyCommits;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class HourlyCommitsMetricProvider implements ITransientMetricProvider<HourlyCommits> {
	
	protected List<IMetricProvider> uses;
	protected MetricProviderContext context;
	
	@Override
	public String getIdentifier() {
		return HourlyCommitsMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "hourlycommits";
	}

	@Override
	public String getFriendlyName() {
		return "Commits by hour";
	}

	@Override
	public String getSummaryInformation() {
		return "Commits group by the hour of the day in which they occurred.";
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
	public HourlyCommits adapt(DB db) {
		return new HourlyCommits(db);
	}

	/**
	 * FIXME: Currently this will calculate it for ALL repos.
	 */
	@Override
	public void measure(Project project, ProjectDelta delta, HourlyCommits db) {
		
		String[] hoursOfDay = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
		for (String h : hoursOfDay) {
			Hour hour = db.getHours().findOneByHour(h+":00");
			if (hour == null) {
				hour = new Hour();
				hour.setHour(h+":00");
				hour.setNumberOfCommits(0);
				db.getHours().add(hour);
				db.sync();
			}
		}
		
		List<RepositoryData> repos = new ArrayList<RepositoryData>();
		
		for (IMetricProvider used : uses) {
			Commits usedCommits =  ((CommitsTransientMetricProvider)used).adapt(context.getProjectDB(project));
			for (RepositoryData rd : usedCommits.getRepositories()) {
				repos.add(rd);
			}
		}
		// QUICKFIX: needed because COmmitsTransientMetricPRovider wno't clear 
		// old data if there are no vcs deltas that day and so this will count old data
		if (delta.getVcsDelta().getRepoDeltas().size() >0) { 
			for (RepositoryData rd : repos) {
				for (CommitData commit : rd.getCommits()){
					String hourName = commit.getTime().split(":")[0];
					
					Hour hour = db.getHours().findOneByHour(hourName + ":00");
					hour.setNumberOfCommits(hour.getNumberOfCommits()+1);
					db.sync();
				}
			}
		}
	}
}
