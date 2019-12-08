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
package org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric;

import java.time.OffsetDateTime;
import java.util.List;

import io.swagger.client.model.MetricBoundary;
import io.swagger.client.model.MetricMilestoneSlice;

public class RemainMetricSlice extends MetricMilestoneSlice {

	public RemainMetricSlice(String bounder, List<MetricBoundary> boundary) {
		super.bounder(bounder);
		super.boundary(boundary);

	}

	public boolean hasRemainMilestone() {

		return !getBoundary().isEmpty();
	}

	public OffsetDateTime getLastBounderTimestamps() {
		OffsetDateTime endDate = null;

		if (!getBoundary().isEmpty()) {
			endDate = getBoundary().get(getBoundary().size() - 1).getEndDate();
		}

		return endDate;
	}

}
