/*******************************************************************************
 * Copyright (c) 2018 University of York
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.commits;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.commits.model.CommitData;
import org.eclipse.scava.metricprovider.trans.commits.model.Commits;
import org.eclipse.scava.metricprovider.trans.commits.model.RepositoryData;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;

import com.mongodb.DB;

public class CommitsTransientMetricProvider  implements ITransientMetricProvider<Commits> {

	protected PlatformVcsManager vcsManager;
	
	@Override
	public String getIdentifier() {
		return CommitsTransientMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "commits";
	}

	@Override
	public String getFriendlyName() {
		return "Commits";
	}

	@Override
	public String getSummaryInformation() {
		return "Transient metric provider for commits.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0;
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Collections.emptyList();
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.vcsManager = context.getPlatformVcsManager();
	}

	@Override
	public Commits adapt(DB db) {
		return new Commits(db);
	}

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		System.err.println(dateFormat.format(new Date()));
	}
	
	@Override
	public void measure(Project project, ProjectDelta delta, Commits db) {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
				
		VcsProjectDelta vcsd = delta.getVcsDelta();
		
		for (VcsRepositoryDelta vcsRepositoryDelta : vcsd.getRepoDeltas()) {
			VcsRepository repo = vcsRepositoryDelta.getRepository();
			
			RepositoryData rd = db.getRepositories().findOneByUrl(repo.getUrl());
			
			if (rd == null) {
				rd = new RepositoryData();
				rd.setUrl(repo.getUrl());
				rd.setTotalCommits(0);
				db.getRepositories().add(rd);
			}
			
			// Clear commit data - only want to store latest day in db
			rd.getCommits().clear(); // This doesn't clear the commits from the Commits.commits collection 
			db.sync();
			
			// Now add the new ones
			for (VcsCommit commit : vcsRepositoryDelta.getCommits()) {
				if (commit.getJavaDate() == null) {
					System.err.println("Null java date!");
				}
				String time = dateFormat.format(commit.getJavaDate());

				CommitData c = new CommitData();
				c.setDate(delta.getDate().toString());
				c.setTime(time);
				db.getCommits().add(c);
				rd.getCommits().add(c);
			}
			rd.setTotalCommits(rd.getTotalCommits() + vcsRepositoryDelta.getCommits().size());
			db.sync();
		}
	}

}
