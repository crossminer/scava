/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.bugs.channelusage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.scava.metricprovider.historic.bugs.comments.CommentsHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.comments.model.BugsCommentsHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.newbugs.NewBugsHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.BugsNewBugsHistoricMetric;
import org.eclipse.scava.metricprovider.historic.bugs.newbugs.model.DailyBugData;
import org.eclipse.scava.metricprovider.historic.bugs.patches.PatchesHistoricMetricProvider;
import org.eclipse.scava.metricprovider.historic.bugs.patches.model.BugsPatchesHistoricMetric;
import org.eclipse.scava.platform.AbstractFactoidMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.factoids.Factoid;
import org.eclipse.scava.platform.factoids.StarRating;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class BugsChannelUsageFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelUsage";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Tracker Usage";
		// This method will NOT be removed in a later version.
	}

	@Override
	public String getSummaryInformation() {
		return "summaryblah"; // This method will NOT be removed in a later version.
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public List<String> getIdentifiersOfUses() {
		return Arrays.asList(NewBugsHistoricMetricProvider.IDENTIFIER,
							 CommentsHistoricMetricProvider.IDENTIFIER,
							 PatchesHistoricMetricProvider.IDENTIFIER);
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		this.uses = uses;
	}

	@Override
	public void measureImpl(Project project, ProjectDelta delta, Factoid factoid) {
//		factoid.setCategory(FactoidCategory.BUGS);
		factoid.setName(getFriendlyName());

		NewBugsHistoricMetricProvider newBugsProvider = null;
		CommentsHistoricMetricProvider commentsProvider = null;
		PatchesHistoricMetricProvider patchesProvider = null;
		
		
		for (IMetricProvider m : this.uses) {
			if (m instanceof NewBugsHistoricMetricProvider) {
				newBugsProvider = (NewBugsHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof CommentsHistoricMetricProvider) {
				commentsProvider = (CommentsHistoricMetricProvider) m;
				continue;
			}
			if (m instanceof PatchesHistoricMetricProvider) {
				patchesProvider = (PatchesHistoricMetricProvider) m;
				continue;
			}
		}
		
		Date end = new Date();
		Date start = (new Date()).addDays(-365);
//		Date start=null, end=null;
//		try {
//			start = new Date("20050301");
//			end = new Date("20060301");
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<Pongo> newBugsList = newBugsProvider.getHistoricalMeasurements(context, project, start, end),
					commentsList = commentsProvider.getHistoricalMeasurements(context, project, start, end),
					patchesList = patchesProvider.getHistoricalMeasurements(context, project, start, end);
		
		Map<String, Integer> trackerBugs = new HashMap<String, Integer>();
		int numberOfBugs = parseNewBugsPongos(newBugsList, trackerBugs);

		Map<String, Integer> trackerComments = new HashMap<String, Integer>();
		int numberOfComments = parseCommentsPongos(commentsList, trackerComments);

		Map<String, Integer> trackerPatches = new HashMap<String, Integer>();
		int numberOfPatches = parsePatchesPongos(patchesList, trackerPatches);

		int workingDaysInAYear = 250;
		if ( (numberOfBugs > workingDaysInAYear) || (numberOfComments > workingDaysInAYear) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfBugs > workingDaysInAYear) || (2 * numberOfComments > workingDaysInAYear) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfBugs > workingDaysInAYear) || (4 * numberOfComments > workingDaysInAYear) ) {
			factoid.setStars(StarRating.TWO);
		} else {
			factoid.setStars(StarRating.ONE);
		}
		

		StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append("Over the last year, ");
		stringBuffer.append(numberOfBugs);
		stringBuffer.append(" new bugs, ");
		stringBuffer.append(numberOfComments);
		stringBuffer.append(" new comments and ");
		stringBuffer.append(numberOfPatches);
		stringBuffer.append(" new patches have been posted, in total.\n");

		end = new Date();
		start = (new Date()).addDays(-30);
		newBugsList = newBugsProvider.getHistoricalMeasurements(context, project, start, end);
		commentsList = commentsProvider.getHistoricalMeasurements(context, project, start, end);
		patchesList = patchesProvider.getHistoricalMeasurements(context, project, start, end);
		
		trackerBugs = new HashMap<String, Integer>();
		numberOfBugs = parseNewBugsPongos(newBugsList, trackerBugs);

		trackerComments = new HashMap<String, Integer>();
		numberOfComments = parseCommentsPongos(commentsList, trackerComments);

		trackerPatches = new HashMap<String, Integer>();
		numberOfPatches = parsePatchesPongos(patchesList, trackerPatches);

		
		stringBuffer.append("Over the last month, ");
		stringBuffer.append(numberOfBugs);
		stringBuffer.append(" new bugs, ");
		stringBuffer.append(numberOfComments);
		stringBuffer.append(" new comments and ");
		stringBuffer.append(numberOfPatches);
		stringBuffer.append(" new patches have been posted.\n");

		factoid.setFactoid(stringBuffer.toString());

	}

	private int parseNewBugsPongos(List<Pongo> newBugsList, Map<String, Integer> trackerBugs) {
		int sumOfBugs = 0;
		for (Pongo pongo: newBugsList) {
			BugsNewBugsHistoricMetric newBugsPongo = (BugsNewBugsHistoricMetric) pongo;
			for (DailyBugData bugData: newBugsPongo.getBugs()) {
				int numberOfBugs = bugData.getNumberOfBugs();
				sumOfBugs += numberOfBugs;
				if (trackerBugs.containsKey(bugData.getBugTrackerId()))
					trackerBugs.put(bugData.getBugTrackerId(), 
							trackerBugs.get(bugData.getBugTrackerId()) + numberOfBugs);
				else
					trackerBugs.put(bugData.getBugTrackerId(), numberOfBugs);
			}
		}
		return sumOfBugs;
	}
		
	private int parseCommentsPongos(List<Pongo> commentsList, Map<String, Integer> trackerComments) {
		int sumOfComments = 0;
		for (Pongo pongo: commentsList) {
			BugsCommentsHistoricMetric commentsPongo = (BugsCommentsHistoricMetric) pongo;
			for (org.eclipse.scava.metricprovider.historic.bugs.comments.model.DailyBugData 
					bugData: commentsPongo.getBugs()) {
				int numberOfComments = bugData.getNumberOfComments();
				sumOfComments += numberOfComments;
				if (trackerComments.containsKey(bugData.getBugTrackerId()))
					trackerComments.put(bugData.getBugTrackerId(), 
										trackerComments.get(bugData.getBugTrackerId()) + 
										numberOfComments);
				else
					trackerComments.put(bugData.getBugTrackerId(), numberOfComments);
			}
		}
		return sumOfComments;
	}
	
	private int parsePatchesPongos(List<Pongo> patchesList, Map<String, Integer> trackerPatches) {
		int sumOfPatches = 0;
		for (Pongo pongo: patchesList) {
			BugsPatchesHistoricMetric patchesPongo = (BugsPatchesHistoricMetric) pongo;
			for (org.eclipse.scava.metricprovider.historic.bugs.patches.model.DailyBugData 
					bugData: patchesPongo.getBugs()) {
				int numberOfPatches = bugData.getNumberOfPatches();;
				sumOfPatches += numberOfPatches;
				if (trackerPatches.containsKey(bugData.getBugTrackerId()))
					trackerPatches.put(bugData.getBugTrackerId(), 
							trackerPatches.get(bugData.getBugTrackerId()) + numberOfPatches);
				else
					trackerPatches.put(bugData.getBugTrackerId(), numberOfPatches);
			}
		}
		return sumOfPatches;
	}

}
