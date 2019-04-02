/*******************************************************************************
 * Copyright (c) 2018 Edge Hill University
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/

package org.eclipse.scava.metricprovider.trans.bugs.bugmetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.ClassificationInstance;
import org.eclipse.scava.contentclassifier.opennlptartarus.libsvm.Classifier;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.eclipse.scava.metricprovider.trans.detectingcode.DetectingCodeTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.BugTrackerCommentDetectingCode;
import org.eclipse.scava.metricprovider.trans.detectingcode.model.DetectingCodeTransMetric;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.RequestReplyClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.BugTrackerComments;
import org.eclipse.scava.metricprovider.trans.requestreplyclassification.model.RequestReplyClassificationTransMetric;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.SentimentClassificationTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.BugTrackerCommentsSentimentClassification;
import org.eclipse.scava.metricprovider.trans.sentimentclassification.model.SentimentClassificationTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.bugtrackingsystem.bugzilla.BugzillaBug;
import org.eclipse.scava.platform.bugtrackingsystem.github.GitHubIssue;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemBug;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemComment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class BugMetadataTransMetricProvider implements ITransientMetricProvider<BugsBugMetadataTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;
	
	@Override
	public String getIdentifier() {
		return BugMetadataTransMetricProvider.class.getCanonicalName();
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
		return Arrays.asList(RequestReplyClassificationTransMetricProvider.class.getCanonicalName(),
								SentimentClassificationTransMetricProvider.class.getCanonicalName(),
								DetectingCodeTransMetricProvider.class.getCanonicalName());
	}

	@Override
	public void setMetricProviderContext(MetricProviderContext context) {
		this.context = context;
		this.platformBugTrackingSystemManager = context.getPlatformBugTrackingSystemManager();
	}

	@Override
	public BugsBugMetadataTransMetric adapt(DB db) {
		return new BugsBugMetadataTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsBugMetadataTransMetric db) {
		
		
		BugTrackingSystemProjectDelta systemDelta = projectDelta.getBugTrackingSystemDelta();
		
		if (uses.size()!=getIdentifiersOfUses().size())
		{
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metrics it needs!");
			System.exit(-1);
		}

		RequestReplyClassificationTransMetric requestReplyClassifier = ((RequestReplyClassificationTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		Map<String, String> commentReplyRequest = new HashMap<String, String>();
		for (BugTrackerComments comment: requestReplyClassifier.getBugTrackerComments())
		{
			commentReplyRequest.put(comment.getBugTrackerId() + comment.getBugId() + comment.getCommentId(), comment.getClassificationResult());
		}

		SentimentClassificationTransMetric sentimentClassifier = ((SentimentClassificationTransMetricProvider)uses.get(1)).adapt(context.getProjectDB(project));
		
		DetectingCodeTransMetric detectingCodeMetric = ((DetectingCodeTransMetricProvider)uses.get(2)).adapt(context.getProjectDB(project));

		for (BugTrackingSystemDelta delta: systemDelta.getBugTrackingSystemDeltas())
		{
			Map<String, List<String>> newBugsComments = new HashMap<String, List<String>>();
			for (BugTrackingSystemComment comment: delta.getComments())
			{
				List<String> newComments;
				if (newBugsComments.containsKey(comment.getBugId())) {
					newComments = newBugsComments.get(comment.getBugId());
				} else {
					newComments = new ArrayList<String>();
					newBugsComments.put(comment.getBugId(), newComments);
				}
				newComments.add(comment.getCommentId());
			}

			Classifier classifier = new Classifier();
			Map<String, ClassificationInstance> classificationInstanceIndex = new HashMap<String, ClassificationInstance>();
			
			for (BugTrackingSystemComment comment: delta.getComments()) {
				Iterable<CommentData> commentIt = 
						db.getComments().find(CommentData.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()), 
											  CommentData.BUGID.eq(comment.getBugId()));
				int numberOfStoredComments = 0;
				for (Iterator<CommentData> it = commentIt.iterator(); it.hasNext();) {
					it.next();
					numberOfStoredComments++;
				}
				List<String> commentList = newBugsComments.get(comment.getBugId());
				int positionFromThreadBeginning = commentList.indexOf(comment.getCommentId());
				positionFromThreadBeginning += numberOfStoredComments;	
				ClassificationInstance instance = prepareClassificationInstance(comment, positionFromThreadBeginning, detectingCodeMetric);
				
				
				classifier.add(instance);
				classificationInstanceIndex.put(comment.getBugId()+"_"+comment.getCommentId(), instance);
			}
			
			classifier.classify();

			storeComments(db, classifier, delta, classificationInstanceIndex, commentReplyRequest);
			db.sync(); 

			for (BugTrackingSystemBug bug: delta.getNewBugs()) {
				storeBug(sentimentClassifier, db, bug);
			}
			db.sync();
			for (BugTrackingSystemBug bug: delta.getUpdatedBugs()) {
				storeBug(sentimentClassifier, db, bug);
			}
			db.sync();
		}
	}
	
	private String getNaturalLanguage(BugTrackingSystemComment comment, DetectingCodeTransMetric db)
	{
		BugTrackerCommentDetectingCode bugtrackerCommentInDetectionCode = null;
		Iterable<BugTrackerCommentDetectingCode> bugtrackerCommentIt = db.getBugTrackerComments().
				find(BugTrackerCommentDetectingCode.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()),
						BugTrackerCommentDetectingCode.BUGID.eq(comment.getBugId()),
						BugTrackerCommentDetectingCode.COMMENTID.eq(comment.getCommentId()));
		for (BugTrackerCommentDetectingCode btcdc:  bugtrackerCommentIt) {
			bugtrackerCommentInDetectionCode = btcdc;
		}
		if(bugtrackerCommentInDetectionCode.getNaturalLanguage() != null)
			return bugtrackerCommentInDetectionCode.getNaturalLanguage();
		else
			return "";
	}
	
	private ClassificationInstance prepareClassificationInstance(BugTrackingSystemComment comment, int positionFromThreadBeginning, DetectingCodeTransMetric db) {
		ClassificationInstance instance = new ClassificationInstance(); 
		instance.setBugTrackerId(comment.getBugTrackingSystem().getOSSMeterId());
		instance.setBugId(comment.getBugId());
		instance.setCommentId(comment.getCommentId());
		instance.setPositionFromThreadBeginning(positionFromThreadBeginning);
		instance.setText(getNaturalLanguage(comment, db));
		return instance;
	}

	private BugData storeBug(SentimentClassificationTransMetric sentimentClassifier, BugsBugMetadataTransMetric db, BugTrackingSystemBug bug) {
		Iterable<BugData> bugDataIt = db.getBugData().find(BugData.BUGTRACKERID.eq(bug.getBugTrackingSystem().getOSSMeterId()), 
											BugData.BUGID.eq(bug.getBugId()));
		BugData bugData = null;
		for (BugData bd:  bugDataIt) bugData = bd;
		if (bugData == null) {
			bugData = new BugData();
			bugData.setBugTrackerId(bug.getBugTrackingSystem().getOSSMeterId());
			bugData.setBugId(bug.getBugId());
			bugData.setCreationTime(bug.getCreationTime().toString());
			db.getBugData().add(bugData);
		}
		bugData.setOperatingSystem(bug.getOperatingSystem());
		bugData.setPriority(bug.getPriority());
		bugData.setResolution(bug.getResolution());
		bugData.setStatus(bug.getStatus());
		if (bug instanceof BugzillaBug) {
			BugzillaBug bugzillaBug = (BugzillaBug) bug; 
			if (bugzillaBug.getLastClosed()!=null)
				bugData.setLastClosedTime(bugzillaBug.getLastClosed().toString());
		} else if (bug instanceof GitHubIssue) {
			GitHubIssue issue = (GitHubIssue) bug; 
			if (issue.getClosedTime()!=null)
				bugData.setLastClosedTime(issue.getClosedTime().toString());
		} else {
			System.err.println("Error! Bug is not covered!");
		}
		updateSentimentPerThread(sentimentClassifier, db, bugData);
		return bugData;
	}

	private void updateSentimentPerThread(SentimentClassificationTransMetric sentimentClassifier, 
											BugsBugMetadataTransMetric db, BugData bugData) {
		Iterable<BugTrackerCommentsSentimentClassification> commentIt = sentimentClassifier.getBugTrackerComments().find(
																			BugTrackerCommentsSentimentClassification.BUGTRACKERID.eq(bugData.getBugTrackerId()), 
																			BugTrackerCommentsSentimentClassification.BUGID.eq(bugData.getBugId()));
		int earliestCommentId=0, latestCommentId=0,
			totalSentiment=0, commentSum = 0;
		String startSentiment = "__label__neutral", 
			   endSentiment = "__label__neutral";
		boolean first = true;
		for (BugTrackerCommentsSentimentClassification bcd:  commentIt) {
			int cid = Integer.parseInt(bcd.getCommentId());
			String sentimentClass = bcd.getPolarity();
			if (first) {
				earliestCommentId = cid;
				startSentiment = sentimentClass;
				latestCommentId = cid;
				endSentiment = sentimentClass;
			}
			if (cid > latestCommentId) {
				latestCommentId = cid;
				endSentiment = sentimentClass;
			}
			if (cid < earliestCommentId) {
				earliestCommentId = cid;
				startSentiment = sentimentClass;
			}
			if (sentimentClass.equals("__label__positive")) 
				totalSentiment += 1;
			else if(sentimentClass.equals("__label__negative")) 
				totalSentiment -= 1;
			commentSum++;
			first = false;
		}
		if(commentSum>0)
			bugData.setAverageSentiment(((float)totalSentiment)/commentSum);
		else
			bugData.setAverageSentiment((float)totalSentiment);
		bugData.setStartSentiment(startSentiment);
		bugData.setEndSentiment(endSentiment);
		db.sync();
	}

	private void storeComments(BugsBugMetadataTransMetric db, Classifier classifier,
							   BugTrackingSystemDelta bugTrackingSystemDelta, 
							   Map<String, ClassificationInstance> classificationInstanceIndex,
							   Map<String, String> commentReplyRequest) {
		for (BugTrackingSystemComment comment: bugTrackingSystemDelta.getComments()) {
			Iterable<CommentData> commentIt = 
					db.getComments().find(CommentData.BUGTRACKERID.eq(comment.getBugTrackingSystem().getOSSMeterId()), 
										  CommentData.BUGID.eq(comment.getBugId()),
										  CommentData.COMMENTID.eq(comment.getCommentId()));
			CommentData commentData = null;
			for (CommentData cd:  commentIt)
				commentData = cd;
			if (commentData == null) {
				commentData = new CommentData();
				commentData.setBugTrackerId(comment.getBugTrackingSystem().getOSSMeterId());
				commentData.setBugId(comment.getBugId());
				commentData.setCommentId(comment.getCommentId());
				commentData.setCreationTime(comment.getCreationTime().toString());
				commentData.setCreator(comment.getCreator());
				ClassificationInstance classificationInstance = 
						classificationInstanceIndex.get(comment.getBugId()+"_"+comment.getCommentId());
				commentData.setContentClass(classifier.getClassificationResult(classificationInstance));
				String requestReplyPrediction = 
						commentReplyRequest.get(comment.getBugTrackingSystem().getOSSMeterId() + comment.getBugId() + comment.getCommentId());
				commentData.setRequestReplyPrediction(requestReplyPrediction);
				
				db.getComments().add(commentData);
				db.sync();
			}
		}
	}

	@Override
	public String getShortIdentifier() {
		return "bugheadermetadata";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Header Metadata";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps various metadata of bug header, " +
				"i.e. priority, status, operation system and resolution.";
	}

}
