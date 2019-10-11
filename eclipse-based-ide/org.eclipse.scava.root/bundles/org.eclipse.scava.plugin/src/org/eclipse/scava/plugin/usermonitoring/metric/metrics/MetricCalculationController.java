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

import org.apache.commons.lang.time.DateUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;
import org.eclipse.swt.widgets.Display;

import io.swagger.client.model.MetricsForProject;

public class MetricCalculationController {
	private final GremlinUtils gremlinUtils;
	private final MetricProvider metricProvider;

	public MetricCalculationController(GremlinUtils gremlinUtils) {
		this.gremlinUtils = gremlinUtils;
		this.metricProvider = new MetricProvider(gremlinUtils);

	}

	public List<MetricsForProject> calculateMetrics(IEvent event) {
		List<MetricsForProject> calculateMilestoneBasedMetrics = null;
		Date currentTime = DateUtils.truncate(new Date(), Calendar.HOUR);

		System.out.println("Start metric calculation.");

		try {
			calculateMilestoneBasedMetrics = metricProvider.calculateMetrics(getRelevantEventChunk(currentTime), currentTime, event);

		} catch (CoreException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.METRIC_CALCULATION_ERROR);
		}

		System.out.println("Metric calculation is done.");

		return calculateMilestoneBasedMetrics;
	}

	private GraphTraversal<Vertex, Vertex> getRelevantEventChunk(Date currentTime) {
		Vertex firstEvent = gremlinUtils.getFirstEvent();
		if (firstEvent == null) {
			return null;
		}
		VertexProperty<Long> timeStampProperty = firstEvent.property("TimeStamp");
		Date firstEventTime = new Date(timeStampProperty.value().longValue());
		Date truncate = DateUtils.truncate(firstEventTime, Calendar.HOUR);

		GraphTraversal<Vertex, Vertex> eventsBetween = gremlinUtils.getEventsBetween(truncate, currentTime);

		return eventsBetween;
	}

}
