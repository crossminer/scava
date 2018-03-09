/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.requestsreplies;

import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.BugsRequestsRepliesHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.requestsreplies.model.DailyBugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class RequestsRepliesHistoricMetricProvider extends AbstractHistoricalMetricProvider{
	public final static String IDENTIFIER = "org.eclipse.scava.metricprovider.historic.bugs.requestsreplies";

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
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public Pongo measure(Project project) {
		Date currentDate = context.getDate();
		BugsRequestsRepliesHistoricMetric dailyNorr = new BugsRequestsRepliesHistoricMetric();
		if (uses.size()==1) {
			int cumulativeRequestSum = 0, 
				cumulativeReplySum = 0,
				requestSum = 0, 
				replySum = 0;
			 BugsBugMetadataTransMetric usedRrc = 
					 ((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			Set<String> bugTrackerIds = new HashSet<String>();
			Map<String, Integer> cumulativeRequests = new HashMap<String, Integer>(), 
								 cumulativeReplies = new HashMap<String, Integer>(),
								 requests = new HashMap<String, Integer>(), 
								 replies = new HashMap<String, Integer>();
			for (CommentData comment: usedRrc.getComments()) {
				Map<String, Integer> crr = null, rr = null;
				Date naDate = null;
				try {
					naDate = new Date(comment.getCreationTime());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (comment.getRequestReplyPrediction().equals("Request")) {
					crr = cumulativeRequests;
					cumulativeRequestSum++;
					if (naDate.compareTo(currentDate)==0) {
						rr = requests;
						requestSum++;
					}
				} else if (comment.getRequestReplyPrediction().equals("Reply")) {
					crr = cumulativeReplies;
					cumulativeReplySum++;
					if (naDate.compareTo(currentDate)==0) {
						rr = replies;
						replySum++;
					}
				}
				if (crr!=null) {
					bugTrackerIds.add(comment.getBugTrackerId());
					if (crr.containsKey(comment.getBugTrackerId()))
						crr.put(comment.getBugTrackerId(), crr.get(comment.getBugTrackerId()) + 1);
					else
						crr.put(comment.getBugTrackerId(), 1);
				} else {
					System.err.println("Classification result ( " + 
							comment.getRequestReplyPrediction() + 
							" ) should be either Request or Reply!");
				}
				if (rr!=null) {
					if (rr.containsKey(comment.getBugTrackerId()))
						rr.put(comment.getBugTrackerId(), rr.get(comment.getBugTrackerId()) + 1);
					else
						rr.put(comment.getBugTrackerId(), 1);
				}
			}
			for (String bugTrackerId: bugTrackerIds) {
				DailyBugTrackerData dailyBugTrackerData = new DailyBugTrackerData();
				dailyBugTrackerData.setBugTrackerId(bugTrackerId);
				if (cumulativeRequests.containsKey(bugTrackerId))
					dailyBugTrackerData.setCumulativeNumberOfRequests(cumulativeRequests.get(bugTrackerId));
				if (cumulativeReplies.containsKey(bugTrackerId))
					dailyBugTrackerData.setCumulativeNumberOfReplies(cumulativeReplies.get(bugTrackerId));
				if (requests.containsKey(bugTrackerId))
					dailyBugTrackerData.setNumberOfRequests(requests.get(bugTrackerId));
				if (replies.containsKey(bugTrackerId))
					dailyBugTrackerData.setNumberOfReplies(replies.get(bugTrackerId));
				dailyNorr.getBugs().add(dailyBugTrackerData);
			}
			dailyNorr.setCumulativeNumberOfRequests(cumulativeRequestSum);
			dailyNorr.setCumulativeNumberOfReplies(cumulativeReplySum);
			dailyNorr.setNumberOfRequests(requestSum);
			dailyNorr.setNumberOfReplies(replySum);
		}
		
		return dailyNorr;
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
	}

	@Override
	public String getShortIdentifier() {
		return "requestsrepliespernewsgroup";
	}

	@Override
	public String getFriendlyName() {
		return "Requests and Replies Per Bug Tracker";
	}

	@Override
	public String getSummaryInformation() {
		return "This class computes the number of request and reply bug tracker comments " +
				"per day for each bug Tracker separately.";
	}

}
