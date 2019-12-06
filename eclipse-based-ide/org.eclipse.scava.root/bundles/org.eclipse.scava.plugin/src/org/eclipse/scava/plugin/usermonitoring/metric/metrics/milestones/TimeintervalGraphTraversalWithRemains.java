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
package org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones;

import java.time.OffsetDateTime;
import java.util.Date;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.RemainMetricSlice;

import io.swagger.client.model.MetricMilestoneSlice;

public class TimeintervalGraphTraversalWithRemains<S, E> extends TimeintervalGraphtraversal<S, E> {

	private final MetricMilestoneSlice remainMetricMilestoneSlices;
	private final Vertex startVertex;

	public TimeintervalGraphTraversalWithRemains(GraphTraversal<S, E> graphTraversal, Vertex startVertex, OffsetDateTime endDate, RemainMetricSlice remainMetricMilestoneSlices) {
		super(graphTraversal, remainMetricMilestoneSlices.getLastBounderTimestamps(), endDate);
		this.remainMetricMilestoneSlices = remainMetricMilestoneSlices;
		this.startVertex = startVertex;
	}
	
	public TimeintervalGraphTraversalWithRemains(GraphTraversal<S, E> graphTraversal, Vertex startVertex, OffsetDateTime endDate, RemainMetricSlice remainMetricMilestoneSlices,boolean remain) {
		super(graphTraversal, remainMetricMilestoneSlices.getLastBounderTimestamps(), endDate, remain);
		this.remainMetricMilestoneSlices = remainMetricMilestoneSlices;
		this.startVertex = startVertex;
	}

	public MetricMilestoneSlice getRemainMetricMilestoneSlices() {
		return remainMetricMilestoneSlices;
	}

	public Vertex getStartVertex() {
		return startVertex;
	}

}
