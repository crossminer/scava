/*******************************************************************************
 * Copyright (c) 2019 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.references;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.scava.metricprovider.trans.bugs.references.model.BugReferringTo;
import org.eclipse.scava.metricprovider.trans.bugs.references.model.BugsReferenceTransMetric;
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
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.Project;
import org.eclipse.scava.repository.model.github.GitHubBugTracker;
import org.eclipse.scava.repository.model.jira.JiraBugTrackingSystem;
import com.mongodb.DB;

public class BugsReferenceTransMetricProvider implements ITransientMetricProvider<BugsReferenceTransMetric> {
	
	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;
	
	@Override
	public String getIdentifier() {
		return BugsReferenceTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public String getShortIdentifier() {
		return "trans.bugs.references";
	}

	@Override
	public String getFriendlyName() {
		return "Bugs References";
	}

	@Override
	public String getSummaryInformation() {
		return "This metrics search for references of commits or bugs within comments comming from bugs comments.";
	}

	@Override
	public boolean appliesTo(Project project) {
		return !project.getBugTrackingSystems().isEmpty();	
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
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsReferenceTransMetric adapt(DB db) {
		return new BugsReferenceTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta delta, BugsReferenceTransMetric db)
	{
		clearDB(db);
		BugTrackingSystemProjectDelta btspDelta = delta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : btspDelta.getBugTrackingSystemDeltas())
		{
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			
			BTSParsedData btsParsedData= new BTSParsedData(bugTracker);
			
			for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments())
			{
				BugReferringTo bugReferringTo = findBugTrackerComment(db, comment);
				
				if(bugReferringTo==null)
				{
					bugReferringTo = new BugReferringTo();
					bugReferringTo.setBugTrackerId(bugTracker.getOSSMeterId());
					bugReferringTo.setBugId(comment.getBugId());
					bugReferringTo.setCommentId(comment.getCommentId());
					db.getBugsReferringTo().add(bugReferringTo);
				}
				
				NormalizedReferences references = new NormalizedReferences();
				
				switch(bugTracker.getBugTrackerType())
				{
					case "github":
						references=ReferencesInGitHub.findReferences(comment.getText(), btsParsedData.getOwner(), btsParsedData.getRepository());
						break;
					case "bitbucket":
						references=ReferencesInBitBucket.findReferences(comment.getText());
						break;
					case "jira":
						references=ReferencesInJira.findReferences(comment.getText(), btsParsedData.getHost(), btsParsedData.getRepository());
						break;
					case "bugzilla":
						references=ReferencesInBugzilla.findReferences(comment.getText(), btsParsedData.getHost());
						break;
					case "gitlab":
						references=ReferencesInGitLab.findReferences(comment.getText());
						break;
					case "redmine":
						references=ReferencesInRedmine.findReferences(comment.getText(), btsParsedData.getHost());
						break;
					case "sourceforge":
						references=ReferencesInSourceforge.findReferences(comment.getText());
						break;
					
				}
				
				bugReferringTo.getBugsReferred().addAll(references.getNormalizedBugsReferences());
				bugReferringTo.getCommitsReferred().addAll(references.getNormalizedCommitsReferences());
				
				db.sync();
			}
		}
	}
	
	private void clearDB(BugsReferenceTransMetric db)
	{
		db.getBugsReferringTo().getDbCollection().drop();
		db.sync();
	}

	private BugReferringTo findBugTrackerComment(BugsReferenceTransMetric db, BugTrackingSystemComment comment) {
		BugReferringTo bugTrackerCommentsData = null;
		Iterable<BugReferringTo> bugTrackerCommentsDataIt = 
				db.getBugsReferringTo().
						find(BugReferringTo.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()), 
								BugReferringTo.BUGID.eq(comment.getBugId()),
								BugReferringTo.COMMENTID.eq(comment.getCommentId()));
		for (BugReferringTo bcd:  bugTrackerCommentsDataIt) {
			bugTrackerCommentsData = bcd;
		}
		return bugTrackerCommentsData;
	}
	
	private class BTSParsedData
	{
		private String host;
		private String owner;
		private String repository;
		
		
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
				try {
					URI projectURI = new URI(bugTracker.getUrl());
					host = projectURI.getScheme()+projectURI.getHost();
				} catch (URISyntaxException | UnsupportedOperationException e) {
					e.printStackTrace();
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
		
	}
	
}
