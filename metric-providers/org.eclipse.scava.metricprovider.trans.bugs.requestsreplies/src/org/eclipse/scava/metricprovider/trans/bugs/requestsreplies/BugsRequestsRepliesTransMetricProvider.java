/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.requestsreplies;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model.BugStatistics;
import org.eclipse.scava.metricprovider.trans.bugs.requestsreplies.model.BugsRequestsRepliesTransMetric;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.communicationchannel.nntp.NntpUtil;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class BugsRequestsRepliesTransMetricProvider  implements 
		ITransientMetricProvider<BugsRequestsRepliesTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return BugsRequestsRepliesTransMetricProvider.class.getCanonicalName();
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
		return Arrays.asList(BugMetadataTransMetricProvider.class.getCanonicalName());

	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsRequestsRepliesTransMetric adapt(DB db) {
		return new BugsRequestsRepliesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, 
						BugsRequestsRepliesTransMetric db) {
//		final long startTime = System.currentTimeMillis();
		db.getBugs().getDbCollection().drop();
		db.sync();

		if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		BugsBugMetadataTransMetric usedBugMetadata = 
				((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
		
		for (BugData bugData: usedBugMetadata.getBugData()) {
			
//		System.err.println("usedBugMetadata.getBugTrackerData(): " + usedBugMetadata.getBugTrackerData().size());
			Iterable<CommentData> commentDataIt = usedBugMetadata.getComments().
					find(CommentData.BUGTRACKERID.eq(bugData.getBugTrackerId()),
							CommentData.BUGID.eq(bugData.getBugId()));
			
			Map<String, CommentData> commentIdComment = new HashMap<String, CommentData>();
			for (CommentData commentData: commentDataIt) 
				commentIdComment.put(commentData.getCommentId(), commentData);

			SortedSet<String> sortedCommentSet = new TreeSet<String>(commentIdComment.keySet());
			sortedCommentSet.addAll(commentIdComment.keySet());
			
//			System.err.println("sortedCommentSet: " + sortedCommentSet.size());
			
			String firstMessageTime = null;
//		Iterator<CommentData> iterator = sortedCommentSet.iterator();
			boolean first=true,
					noReplyFound=true,
					isFirstRequest=true;
			
			String lastBugTrackerId = "";
			for (String commentId: sortedCommentSet) {
				CommentData comment = commentIdComment.get(commentId);
				lastBugTrackerId = comment.getBugTrackerId();
				String responseReply = comment.getRequestReplyPrediction();
				if (first)
					firstMessageTime = comment.getCreationTime();
				if ((first)&&(responseReply.equals("Reply"))) isFirstRequest=false;
				if ( ( !first ) && ( noReplyFound ) && ( responseReply.equals("Reply") ) ) {
					BugStatistics bugStats = new BugStatistics();
					bugStats.setBugTrackerId(lastBugTrackerId);
					bugStats.setFirstRequest(isFirstRequest);
					bugStats.setBugId(comment.getBugId());
					bugStats.setAnswered(true);
					bugStats.setResponseDate(comment.getCreationTime());
					long duration = computeDurationInSeconds(firstMessageTime, comment.getCreationTime());
					bugStats.setResponseDurationSec(duration);
					db.getBugs().add(bugStats);
					db.sync();
					
//					System.err.println("bugData.getBugId(): " + bugData.getBugId() + "\t" +
//							"firstMessageTime: " + firstMessageTime + "\t" +
//							"firstResponseTime: " + comment.getCreationTime() + "\t" + 
//							"duration: " + duration);
				}
				if (responseReply.equals("Reply")) noReplyFound=false;
				first=false;
			}
			
			if (noReplyFound&&(!first)) {
				BugStatistics bugStats = new BugStatistics();
				bugStats = new BugStatistics();
				bugStats.setBugTrackerId(lastBugTrackerId);
				bugStats.setFirstRequest(isFirstRequest);
				bugStats.setBugId(bugData.getBugId());
				bugStats.setAnswered(false);
				db.getBugs().add(bugStats);
				db.sync();
				
//				System.err.println("bugData.getBugId(): " + bugData.getBugId() + "\t" +
//						"firstMessageTime: " + firstMessageTime + "\t" +
//						"unanswered");
			}
		}
		
		db.sync();
		
	}

	private long computeDurationInSeconds(String firstMessageTimeString, String firstResponseTimeString) {
		java.util.Date javaFirstMessageTime = NntpUtil.parseDate(firstMessageTimeString);
		java.util.Date javaFirstResponseTime = NntpUtil.parseDate(firstResponseTimeString);
//		System.err.println(" --> firstMessageTime: "+ javaFirstMessageTime + "\t" + 
//							"firstResponseTime: " + javaFirstResponseTime);
		return Date.duration(javaFirstMessageTime, javaFirstResponseTime) / 1000;
	}

	@Override
	public String getShortIdentifier() {
		return "bugsrequestsreplies";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Statistics (answered?, answeredDuration)";
	}

	@Override
	public String getSummaryInformation() {
		return "The metric computed for each bug whether it is answered." +
				"If yes, it also computes the response time";
	}

}
