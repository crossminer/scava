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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.RemainMetricSlice;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import io.swagger.client.model.MetricMilestoneSlice;
import io.swagger.client.model.MetricsForProject;

public class MetricRemainHandler {

	List<MetricsForProject> remains;

	public MetricRemainHandler(String json) {
		Type listType = new TypeToken<ArrayList<MetricsForProject>>() {
		}.getType();
		remains = new Gson().fromJson(json, listType);
		if (remains == null) {
			remains = Collections.emptyList();
		}
	}

	public RemainMetricSlice getRemainMetricSlice(String project, String bounder) {

		for (MetricsForProject metricsForProject : remains) {
			if (metricsForProject.getProjectId().equals(project)) {
				List<MetricMilestoneSlice> metricMilestoneSlice = metricsForProject.getMetricMilestoneSlice();
				for (MetricMilestoneSlice slice : metricMilestoneSlice) {
					if (slice.getBounder().equals(bounder)) {
						return new RemainMetricSlice(bounder, slice.getBoundary());
					}
				}
			}
		}

		return new RemainMetricSlice(bounder, Collections.emptyList());
	}

}
