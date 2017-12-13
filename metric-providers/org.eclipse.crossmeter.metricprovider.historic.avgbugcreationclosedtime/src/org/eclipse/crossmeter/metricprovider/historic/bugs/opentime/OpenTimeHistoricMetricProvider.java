/*******************************************************************************
 * Copyright (c) 2014 CROSSMETER Partners.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Yannis Korkontzelos - Implementation.
 *******************************************************************************/
package org.eclipse.crossmeter.metricprovider.historic.bugs.opentime;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.eclipse.crossmeter.metricprovider.historic.bugs.opentime.model.BugsOpenTimeHistoricMetric;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.BugMetadataTransMetricProvider;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model.BugData;
import org.eclipse.crossmeter.metricprovider.trans.bugs.bugmetadata.model.BugsBugMetadataTransMetric;
import org.eclipse.crossmeter.platform.AbstractHistoricalMetricProvider;
import org.eclipse.crossmeter.platform.Date;
import org.eclipse.crossmeter.platform.IMetricProvider;
import org.eclipse.crossmeter.platform.MetricProviderContext;
import org.eclipse.crossmeter.platform.communicationchannel.nntp.NntpUtil;
import org.eclipse.crossmeter.repository.model.Project;

import com.googlecode.pongo.runtime.Pongo;

public class OpenTimeHistoricMetricProvider extends AbstractHistoricalMetricProvider {

	public final static String IDENTIFIER = "org.eclipse.crossmeter.metricprovider.historic.bugs.opentime";

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
				if (!bugData.getLastClosedTime().equals("null")) {
					java.util.Date javaOpenTime = NntpUtil.parseDate(bugData.getCreationTime());
					java.util.Date javaCloseTime = NntpUtil.parseDate(bugData.getLastClosedTime());
					seconds += ( Date.duration(javaOpenTime, javaCloseTime) / 1000);
					durations++;
				}
			}
			long avgDuration = 0;
			if (durations>0)
				avgDuration = seconds / durations;
			double daysReal = ( (double) avgDuration ) / SECONDS_DAY;
			avgBugOpenTime.setAvgBugOpenTimeInDays(daysReal);
			int days = (int) daysReal;
			long lessThanDay = (avgDuration % SECONDS_DAY);
			String formatted = DurationFormatUtils.formatDuration(lessThanDay*1000, "HH:mm:ss:SS");
			avgBugOpenTime.setAvgBugOpenTime(days+":"+formatted);
//			System.out.println(days + ":" + formatted);
			avgBugOpenTime.setBugsConsidered(durations);

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
		return "Average Bug Duration";
	}

	@Override
	public String getSummaryInformation() {
		return "This metric computes the average time between creating and closing bugs. " +
				"Format: dd:HH:mm:ss:SS, where dd=days, HH:hours, mm=minutes, ss:seconds, SS=milliseconds.";
	}
}
