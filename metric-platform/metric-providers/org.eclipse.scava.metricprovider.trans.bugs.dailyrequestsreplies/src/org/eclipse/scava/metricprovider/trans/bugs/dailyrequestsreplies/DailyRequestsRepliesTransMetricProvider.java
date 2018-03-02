/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.model.BugsDailyRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.dailyrequestsreplies.model.DayComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.platform.Date;
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

import com.mongodb.DB;

public class DailyRequestsRepliesTransMetricProvider implements ITransientMetricProvider<
BugsDailyRequestsRepliesTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return DailyRequestsRepliesTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(RequestReplyClassificationTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsDailyRequestsRepliesTransMetric adapt(DB db) {
		return new BugsDailyRequestsRepliesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsDailyRequestsRepliesTransMetric db) {
		
		String[] daysOfWeek = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		RequestReplyClassificationTransMetric usedClassifier = 
				((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			
			for (String dayOfWeek : daysOfWeek) {
				
				Iterable<DayComments> dayCommentsIt = db.getDayComments().
						find(DayComments.BUGTRACKERID.eq(bugTrackingSystem.getOSSMeterId()),
								DayComments.NAME.eq(dayOfWeek));

				DayComments dayComments = null;
				for (DayComments btd:  dayCommentsIt) {
					dayComments = btd;
				}

				if (dayComments == null) {
					dayComments = new DayComments();
					dayComments.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
					dayComments.setName(dayOfWeek);
					dayComments.setNumberOfComments(0);
					dayComments.setNumberOfRequests(0);
					dayComments.setNumberOfReplies(0);
					db.getDayComments().add(dayComments);
					db.sync();
				}
			}
			
			
			List<BugTrackingSystemComment> comments = bugTrackingSystemDelta.getComments();
			for (BugTrackingSystemComment comment: comments) {
				
				Date date = new Date(comment.getCreationTime());
				Calendar cal = Calendar.getInstance();
				cal.setTime(date.toJavaDate());
				int dow = cal.get(Calendar.DAY_OF_WEEK) - 1;
				String dayName = daysOfWeek[dow];
				
			Iterable<DayComments> dayCommentsIt = db.getDayComments().
														find(DayComments.BUGTRACKERID.eq(bugTrackingSystem.getOSSMeterId()),
															 DayComments.NAME.eq(dayName));
				DayComments dayComments = null;
				for (DayComments btd:  dayCommentsIt)
					dayComments = btd;
				
				if (dayComments == null) {
					System.err.println("Error! DailyRequestsRepliesTransMetricProvider");
//					dayComments = new DayComments();
//					dayComments.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
//					dayComments.setName(dayName);
//					db.getDayComments().add(dayComments);
				} 

				dayComments.setNumberOfComments(dayComments.getNumberOfComments()+1);
				String requestReplyClass = getRequestReplyClass(usedClassifier, bugTrackingSystem, comment);
				if (requestReplyClass.equals("Request"))
					dayComments.setNumberOfRequests(dayComments.getNumberOfRequests()+1);
				else if (requestReplyClass.equals("Reply"))
					dayComments.setNumberOfReplies(dayComments.getNumberOfReplies()+1);
				db.sync();
			}
		}

		int sumOfComments = 0,
			sumOfRequests = 0,
			sumOfReplies = 0;

		for (DayComments dayComments: db.getDayComments()) {
			sumOfComments += dayComments.getNumberOfComments();
			sumOfRequests += dayComments.getNumberOfRequests();
			sumOfReplies += dayComments.getNumberOfReplies();
		}

		for (DayComments dayComments: db.getDayComments()) {
			
			float percentageOfComments;
			if (sumOfComments == 0)
				percentageOfComments = ( (float) 100 ) / 7;
			else
				percentageOfComments = ( (float) 100 * dayComments.getNumberOfComments() ) / sumOfComments;
			dayComments.setPercentageOfComments(percentageOfComments);
			
			float percentageOfRequests;
			if (sumOfRequests == 0)
				percentageOfRequests = ( (float) 100 ) / 7;
			else
				percentageOfRequests = ( (float) 100 * dayComments.getNumberOfRequests() ) / sumOfRequests;
			dayComments.setPercentageOfRequests(percentageOfRequests);
			
			float percentageOfReplies;
			if (sumOfReplies == 0)
				percentageOfReplies = ( (float) 100 ) / 7;
			else
				percentageOfReplies = ( (float) 100 * dayComments.getNumberOfReplies() ) / sumOfReplies;
			dayComments.setPercentageOfReplies(percentageOfReplies);
		}

		db.sync();
		
	}

	private String getRequestReplyClass(RequestReplyClassificationTransMetric usedClassifier, 
			BugTrackingSystem tracker, BugTrackingSystemComment comment) {
		Iterable<BugTrackerComments> bugTrackerCommentsIt = usedClassifier.getBugTrackerComments().
				find(BugTrackerComments.BUGTRACKERID.eq(tracker.getOSSMeterId()), 
						BugTrackerComments.COMMENTID.eq(comment.getCommentId()));
		BugTrackerComments bugTrackerComments = null;
		for (BugTrackerComments btr: bugTrackerCommentsIt) {
			bugTrackerComments = btr;
		}
		if (bugTrackerComments == null) {
			System.err.println("Bugs - Daily Requests Replies -\t" + 
					"there is no classification for comment: " + comment.getCommentId() +
					"\t of bug tracker: " + tracker.getUrl());
//			System.exit(-1);
		} else
			return bugTrackerComments.getClassificationResult();
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "dailyrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Comments, Requests and Replies per Day of the Week";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric stores the number of comments, " +
				"requests and replies for each day of the week.";
	}

}
