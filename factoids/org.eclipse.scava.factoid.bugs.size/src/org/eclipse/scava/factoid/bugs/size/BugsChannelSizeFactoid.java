/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.factoid.bugs.size;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

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

public class BugsChannelSizeFactoid extends AbstractFactoidMetricProvider{

	protected List<IMetricProvider> uses;
	
	@Override
	public String getShortIdentifier() {
		return "BugChannelSize";
	}

	@Override
	public String getFriendlyName() {
		return "Bug Tracker Size";
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
		Date start = (new Date()).addDays(-30);
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
		
//		if ( ( newBugsList == null ) || ( newBugsList.size() == 0 ) )
//			System.err.println("---===COULD NOT RETRIEVED PONGOLIST===---");
//		else 
//			System.err.println("---===RETRIEVED PONGOLIST FOR " + newBugsList.get(0) + " DAYS===---");
			
		Map<String, Integer> trackerBugs = getBugsMap(newBugsList);
		int numberOfBugs = sumValues(trackerBugs);

		Map<String, Integer> trackerComments = getCommentsMap(commentsList);
		int numberOfComments = sumValues(trackerComments);

		Map<String, Integer> trackerPatches = getCumulativePatchesMap(patchesList);
		int numberOfPatches = sumValues(trackerPatches);

		int threshold = 1000;
		
		if ( (numberOfBugs > threshold) || (numberOfComments > 10 * threshold) || (numberOfPatches > threshold) ) {
			factoid.setStars(StarRating.FOUR);
		} else if ( (2 * numberOfBugs > threshold) || (2 * numberOfComments > 10 * threshold) || (2 * numberOfPatches > threshold) ) {
			factoid.setStars(StarRating.THREE);
		} else if ( (4 * numberOfBugs > threshold) || (4 * numberOfComments > 10 * threshold) || (4 * numberOfPatches > threshold) ) {
			factoid.setStars(StarRating.TWO);
		} else
			factoid.setStars(StarRating.ONE);
		
		StringBuffer stringBuffer = new StringBuffer();
		
		int bugs = 0, comments = 0, patches = 0;
		for (String tracker: sortByKeys(trackerBugs)) {
			bugs += trackerBugs.get(tracker);
			comments += trackerComments.get(tracker);
			patches += trackerPatches.get(tracker);
		}
		
		stringBuffer.append("The bug trackers of the project contain ");
		stringBuffer.append(bugs);
		stringBuffer.append(" bugs, ");
		stringBuffer.append(comments);
		stringBuffer.append(" comments and ");
		stringBuffer.append(patches);
		stringBuffer.append(" patches, in total.\n");
		
		factoid.setFactoid(stringBuffer.toString());

	}

	private Map<String, Integer> getBugsMap(List<Pongo> newBugsList) {
		Map<String, Integer> trackerBugs = new HashMap<String, Integer>();
		if ( newBugsList.size() > 0 ) {
			BugsNewBugsHistoricMetric newBugsPongo = 
					(BugsNewBugsHistoricMetric) newBugsList.get(newBugsList.size()-1);
			for (DailyBugData bugData: newBugsPongo.getBugs()) {
				int bugs = bugData.getCumulativeNumberOfBugs();
				trackerBugs.put(bugData.getBugTrackerId(), bugs);
			}
		}
		return trackerBugs;
	}
	
	private int sumValues(Map<String, Integer> map) {
		int sum = 0;
		for (int value: map.values())
			sum += value;
		return sum;
	}
	
	private Map<String, Integer> getCommentsMap(List<Pongo> commentsList) {
		Map<String, Integer> trackerComments = new HashMap<String, Integer>();
		if ( commentsList.size() > 0 ) {
			BugsCommentsHistoricMetric commentsPongo = 
					(BugsCommentsHistoricMetric) commentsList.get(commentsList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.comments.model.DailyBugData 
					commentsData: commentsPongo.getBugs()) {
				int comments = commentsData.getCumulativeNumberOfComments();
				trackerComments.put(commentsData.getBugTrackerId(), comments);
			}
		}
		return trackerComments;
	}
	
	private Map<String, Integer> getCumulativePatchesMap(List<Pongo> patchesList) {
		Map<String, Integer> trackerPatches = new HashMap<String, Integer>();
		if ( patchesList.size() > 0 ) {
			BugsPatchesHistoricMetric patchesPongo = 
					(BugsPatchesHistoricMetric) patchesList.get(patchesList.size()-1);
			for (org.eclipse.scava.metricprovider.historic.bugs.patches.model.DailyBugData 
					patchesData: patchesPongo.getBugs()) {
				int patches = patchesData.getCumulativeNumberOfPatches();
				trackerPatches.put(patchesData.getBugTrackerId(), patches);
			}
		}
		return trackerPatches;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private SortedSet<String> sortByKeys(Map<String, ?> map) {
		return new TreeSet(map.keySet());
	}

}
