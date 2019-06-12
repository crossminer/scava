/*******************************************************************************
 * Copyright (c) 2017 University of Manchester
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.scava.metricprovider.historic.bugs.opentime;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.eclipse.scava.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.scava.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.scava.platform.AbstractHistoricalMetricProvider;
import org.eclipse.scava.platform.Date;
import org.eclipse.scava.platform.IMetricProvider;
import org.eclipse.scava.platform.MetricProviderContext;
import org.eclipse.scava.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class OpenTimeHistoricMetricProvider extends AbstractHistoricalMetricProvider {

	public final static String IDENTIFIER = OpenTimeHistoricMetricProvider.class.getCanonicalName();

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

	private static final long SECONDS_DAY = 24 * 60 * 60;

	@Override
	public Pongo measure(Project project) {
		BugsOpenTimeHistoricMetric avgBugOpenTime = new BugsOpenTimeHistoricMetric();
		if (uses.size() == 1) {
			BugsBugMetadataTransMetric usedBhm = ((BugMetadataTransMetricProvider)uses.get(0)).adapt(context.getProjectDB(project));
			long seconds = 0;
			int durations = 0;
			for (BugData bugData: usedBhm.getBugData()) {
				if (bugData.getLastClosedTime()!=null) {
					java.util.Date javaOpenTime = bugData.getCreationTime();
					java.util.Date javaCloseTime = bugData.getLastClosedTime();
					seconds += ( Date.duration(javaOpenTime, javaCloseTime) / 1000);
					durations++;
				}
			}
			
			if (durations>0)
			{
				long avgDuration = seconds / durations;
				double daysReal = ( (double) avgDuration ) / SECONDS_DAY;
				avgBugOpenTime.setAvgBugOpenTimeInDays(daysReal);
				int days = (int) daysReal;
				long lessThanDay = (avgDuration % SECONDS_DAY);
				String formatted = DurationFormatUtils.formatDuration(lessThanDay*1000, "HH:mm:ss:SS");
				avgBugOpenTime.setAvgBugOpenTime(days+":"+formatted);
				System.out.println(days + ":" + formatted);
				avgBugOpenTime.setBugsConsidered(durations);
			}

		}
		return avgBugOpenTime;
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
		return "bugopentime";
	}

	@Override
	public String getFriendlyName() {
		return "Average duration to close an open bug";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average duration between creating and closing bugs. " +
				"Format: dd:HH:mm:ss:SS, where dd=days, HH:hours, mm=minutes, ss:seconds, SS=milliseconds.";
	}
}
