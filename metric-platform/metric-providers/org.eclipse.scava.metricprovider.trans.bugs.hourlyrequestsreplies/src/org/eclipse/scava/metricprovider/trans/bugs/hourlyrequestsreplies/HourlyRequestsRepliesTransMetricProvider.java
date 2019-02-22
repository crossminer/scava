/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.BugsHourlyRequestsRepliesTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.hourlyrequestsreplies.model.HourComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
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

public class HourlyRequestsRepliesTransMetricProvider implements ITransientMetricProvider<BugsHourlyRequestsRepliesTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return HourlyRequestsRepliesTransMetricProvider.class.getCanonicalName();
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
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
		this.context = context;
	}

	@Override
	public BugsHourlyRequestsRepliesTransMetric adapt(DB db) {
		return new BugsHourlyRequestsRepliesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsHourlyRequestsRepliesTransMetric db) {
		String[] hoursOfDay = new String[]{"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};

		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		RequestReplyClassificationTransMetric usedClassifier = 
				((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTrackingSystem = bugTrackingSystemDelta.getBugTrackingSystem();
			
			for (String hour : hoursOfDay) {
				Iterable<HourComments> hourCommentsIt = db.getHourComments().
						find(HourComments.BUGTRACKERID.eq(bugTrackingSystem.getOSSMeterId()),
								HourComments.HOUR.eq(hour+":00"));

				HourComments hourComments = null;
				for (HourComments btd:  hourCommentsIt) {
					hourComments = btd;
				}

				if (hourComments == null) {
					hourComments = new HourComments();
					hourComments.setHour(hour+":00");
					hourComments.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
					hourComments.setNumberOfComments(0);
					hourComments.setNumberOfRequests(0);
					hourComments.setNumberOfReplies(0);
					db.getHourComments().add(hourComments);
					db.sync();
				}
			}
			
			List<BugTrackingSystemComment> comments = bugTrackingSystemDelta.getComments();
			for (BugTrackingSystemComment comment: comments) {

				@SuppressWarnings("deprecation")
				String hourNumber = String.format("%02d", comment.getCreationTime().getHours());
				
				Iterable<HourComments> hourCommentsIt = db.getHourComments().
						find(HourComments.BUGTRACKERID.eq(bugTrackingSystem.getOSSMeterId()),
								HourComments.HOUR.eq(hourNumber + ":00"));
				
				HourComments hourComments = null;
				for (HourComments btd:  hourCommentsIt) {
					hourComments = btd;
				}
				
				if (hourComments == null) {
					System.err.println("Error! HourlyRequestsRepliesTransMetricProvider");
//					dayComments = new DayComments();
//					dayComments.setBugTrackerId(bugTrackingSystem.getOSSMeterId());
//					dayComments.setName(dayName);
//					db.getDayComments().add(dayComments);
				} 

				hourComments.setNumberOfComments(hourComments.getNumberOfComments()+1);
				String requestReplyClass = getRequestReplyClass(usedClassifier, bugTrackingSystem, comment);
				if (requestReplyClass.equals("__label__Request"))
					hourComments.setNumberOfRequests(hourComments.getNumberOfRequests()+1);
				else if (requestReplyClass.equals("__label__Reply"))
					hourComments.setNumberOfReplies(hourComments.getNumberOfReplies()+1);
				db.sync();
			}
		}

		int sumOfComments = 0,
				sumOfRequests = 0,
				sumOfReplies = 0;

			for (HourComments hourComments: db.getHourComments()) {
				sumOfComments += hourComments.getNumberOfComments();
				sumOfRequests += hourComments.getNumberOfRequests();
				sumOfReplies += hourComments.getNumberOfReplies();
			}

			for (HourComments hourComments: db.getHourComments()) {
				
				float percentageOfComments;
				if (sumOfComments == 0)
					percentageOfComments = ( (float) 100 ) / 24;
				else
					percentageOfComments = ( (float) 100 * hourComments.getNumberOfComments() ) / sumOfComments;
				hourComments.setPercentageOfComments(percentageOfComments);
				
				float percentageOfRequests;
				if (sumOfRequests == 0)
					percentageOfRequests = ( (float) 100 ) / 24;
				else
					percentageOfRequests = ( (float) 100 * hourComments.getNumberOfRequests() ) / sumOfRequests;
				hourComments.setPercentageOfRequests(percentageOfRequests);
				
				float percentageOfReplies;
				if (sumOfReplies == 0)
					percentageOfReplies = ( (float) 100 ) / 24;
				else
					percentageOfReplies = ( (float) 100 * hourComments.getNumberOfReplies() ) / sumOfReplies;
				hourComments.setPercentageOfReplies(percentageOfReplies);
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
			System.err.println("Bugs - Hourly Requests Replies -\t" + 
					"there is no classification for comment: " + comment.getCommentId() +
					"\t of bug tracker: " + tracker.getUrl());
//			System.exit(-1);
		} else
			return bugTrackerComments.getClassificationResult();
		return "";
	}

	@Override
	public String getShortIdentifier() {
		return "hourlyrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Number of Articles, Requests and Replies per Hour of the Day";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric stores the number of articles, " +
				"requests and replies for each hour of the day.";
	}

}
