/*********************************************************************
* Copyright (c) 2017 FrontEndART Software Ltd.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
*
* Contributors:
*    Zsolt János Szamosvölgyi
*    Endre Tamás Váradi
*    Gergõ Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.time.DateUtils;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.scheduledUploadEvent.ScheduledUploadEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;

import com.google.common.eventbus.EventBus;

import io.swagger.client.model.MetricsForProject;

public class MetricManager {

	private final GremlinUtils gremlinUtils;
	private final MetricCalculationController metricCalculationController;

	public MetricManager(GremlinUtils gremlinUtils, EventBus eventBus) {
		this.gremlinUtils = gremlinUtils;
		this.metricCalculationController = new MetricCalculationController(gremlinUtils);

		startupCheck();

	}

	public List<MetricsForProject> startMetricCalculation(IEvent event) {

		return metricCalculationController.calculateMetrics(event);
	}

	private void startupCheck() {

		long ellapsedTimeFromLastUpload = gremlinUtils.getTimeInMinuteFromFirstEvent();
		System.out.println("Ellapsed time from last upload: " + ellapsedTimeFromLastUpload + "minutes");

		if (ellapsedTimeFromLastUpload >= 60) {
			directUpload();
		}
		scheduledUpload();

	}

	private void scheduledUpload() {

		Date date = DateUtils.truncate(new Date(), Calendar.HOUR);
		date = DateUtils.addMinutes(date, 60);
		System.out.println("Next scheduled upload will be executed at: " + date);

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				System.out.println("Scheduled");
				Activator.getDefault().getEventBus().post(new ScheduledUploadEvent());

			}
		}, date, 3600000);

	}

	private void directUpload() {
		System.out.println("Direct");
		Activator.getDefault().getEventBus().post(new ScheduledUploadEvent());
	}

}
