/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.contentclasses;

import java.util.Arrays;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.CommentData;
import org.eclipse.scava.metricprovider.trans.bugs.contentclasses.model.BugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.contentclasses.model.BugsContentClassesTransMetric;
import org.eclipse.scava.metricprovider.trans.bugs.contentclasses.model.ContentClass;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class ContentClassesTransMetricProvider implements ITransientMetricProvider<BugsContentClassesTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	protected MetricProviderContext context;
	
	protected List<IMetricProvider> uses;

	@Override
	public String getIdentifier() {
		return ContentClassesTransMetricProvider.class.getCanonicalName();
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
	public BugsContentClassesTransMetric adapt(DB db) {
		return new BugsContentClassesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsContentClassesTransMetric db) {

		if (uses.size()!=1) {
			System.err.println("Metric: " + getIdentifier() + " failed to retrieve " + 
								"the transient metric it needs!");
			System.exit(-1);
		}

		BugsBugMetadataTransMetric bugHeaderMetadata = 
				((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));

		db.getBugTrackerData().getDbCollection().drop();
		db.getContentClasses().getDbCollection().drop();
		db.sync();

		for (CommentData commentData: bugHeaderMetadata.getComments()) {
			Iterable<BugTrackerData> bugTrackerDataIt = 
					db.getBugTrackerData().find(BugTrackerData.BUGTRACKERID.eq(commentData.getBugTrackerId()));
			BugTrackerData bugTrackerData = null;
			for (BugTrackerData bd:  bugTrackerDataIt) bugTrackerData = bd;
			if (bugTrackerData == null) {
				bugTrackerData = new BugTrackerData();
				bugTrackerData.setBugTrackerId(commentData.getBugTrackerId());
				bugTrackerData.setNumberOfComments(0);
				db.getBugTrackerData().add(bugTrackerData);
			}
			bugTrackerData.setNumberOfComments(bugTrackerData.getNumberOfComments() + 1);
			
			Iterable<ContentClass> contentClassIt = 
					db.getContentClasses().find(ContentClass.BUGTRACKERID.eq(commentData.getBugTrackerId()),
												ContentClass.CLASSLABEL.eq(commentData.getContentClass()));
			ContentClass contentClass = null;
			for (ContentClass cc:  contentClassIt) contentClass = cc;
			if (contentClass == null) {
				contentClass = new ContentClass();
				contentClass.setBugTrackerId(commentData.getBugTrackerId());
				contentClass.setClassLabel(commentData.getContentClass());
				contentClass.setNumberOfComments(0);
				db.getContentClasses().add(contentClass);
			}
			contentClass.setNumberOfComments(contentClass.getNumberOfComments() + 1);
			
			db.sync();
		}

		for (BugTrackerData bugTrackerData: db.getBugTrackerData()) {
			Iterable<ContentClass> contentClassIt = 
					db.getContentClasses().find(ContentClass.BUGTRACKERID.eq(bugTrackerData.getBugTrackerId()));
			for (ContentClass contentClass:  contentClassIt)
				contentClass.setPercentage( ((float) 100 * contentClass.getNumberOfComments()) / 
														bugTrackerData.getNumberOfComments());
		}

		db.sync();
	}

	@Override
	public String getShortIdentifier() {
		return "BugcontentClasses";
	}

	@Override
	public String getFriendlyName() {
		return "Content Classes in Bug Comments";
	}

	@Override
	public String getSummaryInformation() {
		return "Content Classes in Bug Comments";
	}

}
