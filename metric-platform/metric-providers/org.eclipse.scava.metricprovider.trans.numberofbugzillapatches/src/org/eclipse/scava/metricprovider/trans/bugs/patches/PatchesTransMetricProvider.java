/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.trans.bugs.patches;

import java.util.Collections;
import java.util.List;

import org.eclipse.scava.metricprovider.trans.bugs.patches.model.BugTrackerData;
import org.eclipse.scava.metricprovider.trans.bugs.patches.model.BugsPatchesTransMetric;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.ITransientMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.platform.delta.ProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemAttachment;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.BugTrackingSystemProjectDelta;
import org.eclipse.scava.platform.delta.bugtrackingsystem.PlatformBugTrackingSystemManager;
import org.eclipse.scava.repository.model.BugTrackingSystem;
import org.eclipse.scava.repository.model.Project;

import com.mongodb.DB;

public class PatchesTransMetricProvider implements ITransientMetricProvider<BugsPatchesTransMetric>{

	protected PlatformBugTrackingSystemManager platformBugTrackingSystemManager;

	@Override
	public String getIdentifier() {
		return PatchesTransMetricProvider.class.getCanonicalName();
	}

	@Override
	public boolean appliesTo(Project project) {
	    return !project.getBugTrackingSystems().isEmpty();	   
	}

	@Override
	public void setUses(List<IMetricProvider> uses) {
		// DO NOTHING -- we don't use anything
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
	public BugsPatchesTransMetric adapt(DB db) {
		return new BugsPatchesTransMetric(db);
	}

	@Override
	public void measure(Project project, ProjectDelta projectDelta, BugsPatchesTransMetric db) {
		BugTrackingSystemProjectDelta delta = projectDelta.getBugTrackingSystemDelta();
		
		for (BugTrackingSystemDelta bugTrackingSystemDelta : delta.getBugTrackingSystemDeltas()) {
			BugTrackingSystem bugTracker = bugTrackingSystemDelta.getBugTrackingSystem();
			Iterable<BugTrackerData> bugzillaDataIt = db.getBugTrackerData().
					find(BugTrackerData.BUGTRACKERID.eq(bugTracker.getOSSMeterId()));
			BugTrackerData bugTrackerData = null;
			for (BugTrackerData btd:  bugzillaDataIt) {
				bugTrackerData = btd;
			}
			if (bugTrackerData == null) {
				bugTrackerData = new BugTrackerData();
				bugTrackerData.setBugTrackerId(bugTracker.getOSSMeterId());
				db.getBugTrackerData().add(bugTrackerData);
			} 
			int patches = 0;
			for (BugTrackingSystemAttachment attachment :bugTrackingSystemDelta.getAttachments()) {
//				System.out.println("bugId: " + attachment.getBugId() 
//						+ "\tattachmentId: " + attachment.getAttachmentId() 
//						+ "\tattachmentFiletypeType: " + attachment.getFilename() 
//						+ "\tattachmentMIMEtype: " + attachment.getMimeType());
				if ((attachment.getFilename().contains("patch"))
						||(attachment.getMimeType().contains("patch"))) {
					patches++;
				}
			}
			System.err.println(patches + " patches captured");
			bugTrackerData.setNumberOfPatches(patches);
			int cumulativePatches = bugTrackerData.getCumulativeNumberOfPatches();
			bugTrackerData.setCumulativeNumberOfPatches(cumulativePatches + patches);
			db.sync();
		}
	}

	@Override
	public String getShortIdentifier() {
		return "bugpatches";
	}

	@Override
	public String getFriendlyName() {
		return "Number Of Patches";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric keeps the number of patches submitted for each bug.";
	}

}
