/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.commits.messagereferences;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.metricprovider.trans.commits.messagereferences.model.CommitMessageReferringTo;
import org.eclipse.scava.metricprovider.trans.commits.messagereferences.model.CommitsMessageReferenceTransMetric;
import org.eclipse.scava.nlp.tools.references.NormalizedReferences;
import org.eclipse.scava.nlp.tools.references.bitbucket.ReferencesInBitBucket;
import org.eclipse.scava.nlp.tools.references.bugzilla.ReferencesInBugzilla;
import org.eclipse.scava.nlp.tools.references.github.ReferencesInGitHub;
import org.eclipse.scava.nlp.tools.references.gitlab.ReferencesInGitLab;
import org.eclipse.scava.nlp.tools.references.jira.ReferencesInJira;
import org.eclipse.scava.nlp.tools.references.redmine.ReferencesInRedmine;
import org.eclipse.scava.nlp.tools.references.sourceforge.ReferencesInSourceforge;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.vcs.PlatformVcsManager;
import org.eclipse.scava.platform.delta.vcs.VcsCommit;
import org.eclipse.scava.platform.delta.vcs.VcsProjectDelta;
import org.eclipse.scava.platform.delta.vcs.VcsRepositoryDelta;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.VcsRepository;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;
import org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem;

import com.mongodb.DB;

public class CommitsMessageReferencesTransMetricProvider implements ITransientMetricProvider<CommitsMessageReferenceTransMetric>
{

	protected PlatformVcsManager vcsManager;
	
	@Override
	public String getIdentifier() {
		return CommitsMessageReferencesTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.commits.messagereferences";
	}

	@Override
	public String getFriendlyName() {
		return "Commits Messages References";
	}

	@Override
	public String getSummaryInformation() {
		return "This metrics search for references of commits or bugs within the messages of commits. In order to work, it is necessary to "
				+ "use at the same time one Bug Tracker, as the retrieval of references are based on patterns defined by bug trackers. "
				+ "If multiple or zero Bug Trackers are defined in the project, then this metric do not run.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return project.getVcsRepositories().size() > 0 && project.getBugTrackingSystems().size()==1;
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
	public CommitsMessageReferenceTransMetric adapt(DB db) {
		return new CommitsMessageReferenceTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, CommitsMessageReferenceTransMetric db)
	{
		if(project.getBugTrackingSystems().size()!=1)
			return;
		
		clearDB(db);
		
		VcsProjectDelta vcsd = delta.getVcsDelta();
				
		BugTrackingSystem bugTracker = project.getBugTrackingSystems().get(0);
		BTSParsedData btsParsedData= new BTSParsedData(bugTracker);
		
		for (VcsRepositoryDelta vcsRepositoryDelta : vcsd.getRepoDeltas())
		{
			
			VcsRepository repository = vcsRepositoryDelta.getRepository();
			
			for (VcsCommit commit : vcsRepositoryDelta.getCommits())
			{
				
				CommitMessageReferringTo commitMessageReferringTo = findCommitMessage(db, commit, repository.getUrl());
				if(commitMessageReferringTo==null)
				{
					commitMessageReferringTo = new CommitMessageReferringTo();
					commitMessageReferringTo.setRepository(repository.getUrl());
					commitMessageReferringTo.setRevision(commit.getRevision());
					db.getCommitsMessagesReferringTo().add(commitMessageReferringTo);
				}
				
				NormalizedReferences references = new NormalizedReferences();
				
				
				switch(bugTracker.getBugTrackerType())
				{
					case "github":
						references=ReferencesInGitHub.findReferences(commit.getMessage(), btsParsedData.getOwner(), btsParsedData.getRepository());
						break;
					case "bitbucket":
						references=ReferencesInBitBucket.findReferences(commit.getMessage());
						break;
					case "jira":
						references=ReferencesInJira.findReferences(commit.getMessage(), btsParsedData.getUrl(), btsParsedData.getRepository());
						break;
					case "bugzilla":
						references=ReferencesInBugzilla.findReferences(commit.getMessage(), btsParsedData.getUrl());
						break;
					case "gitlab":
						references=ReferencesInGitLab.findReferences(commit.getMessage(), btsParsedData.getUrl());
						break;
					case "redmine":
						references=ReferencesInRedmine.findReferences(commit.getMessage(), btsParsedData.getUrl());
						break;
					case "sourceforge":
						references=ReferencesInSourceforge.findReferences(commit.getMessage());
						break;
					
				}
				
				commitMessageReferringTo.getBugsReferred().addAll(references.getNormalizedBugsReferences());
				commitMessageReferringTo.getCommitsReferred().addAll(references.getNormalizedCommitsReferences());
				
				db.sync();
				
			}
		}
		
		
	}
	
	private void clearDB(CommitsMessageReferenceTransMetric db)
	{
		db.getCommitsMessagesReferringTo().getDbCollection().drop();
		db.sync();
	}
	
	private CommitMessageReferringTo findCommitMessage(CommitsMessageReferenceTransMetric db, VcsCommit commit, String repositoryURL)
	{
		CommitMessageReferringTo commitMessageReferringTo = null;
		Iterable<CommitMessageReferringTo> commitMessageReferringToIt = db.getCommitsMessagesReferringTo().
																			find(CommitMessageReferringTo.REPOSITORY.eq(repositoryURL),
																					CommitMessageReferringTo.REVISION.eq(commit.getRevision()));
		for(CommitMessageReferringTo cmrt : commitMessageReferringToIt)
			commitMessageReferringTo = cmrt;
		return commitMessageReferringTo;
	}

	private class BTSParsedData
	{
		private String host;
		private String owner;
		private String repository;
		private String url;
		
		
		public BTSParsedData(BugTrackingSystem bugTracker)
		{
			switch(bugTracker.getBugTrackerType())
			{
				case "github":
					GitHubBugTracker ght = (GitHubBugTracker) bugTracker;
					owner=ght.getOwner();
					repository=ght.getRepository();
					break;
				case "jira": //JIRA needs as well the host
					repository=((JiraBugTrackingSystem) bugTracker).getProject();
				case "bugzilla":
				case "redmine":
					url=bugTracker.getUrl();
				break;
				case "gitlab":
					Pattern correctURL = Pattern.compile("^(.+)/api/v4/projects/(.+)$");
					Matcher m = correctURL.matcher(bugTracker.getUrl());
					if(m.find())
					{
						url=m.group(0)+"/"+m.group(1);
					}
				break;
			}
		}

		public String getHost() {
			return host;
		}

		public String getOwner() {
			return owner;
		}

		public String getRepository() {
			return repository;
		}
		
		public String getUrl() {
			return url;
		}
		
	}

}
