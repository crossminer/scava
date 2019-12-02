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
package org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.basicMilestones;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricRemainHandler;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.RemainMetricSlice;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.IBasicMilestone;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.IHasRemainder;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.TimeintervalGraphtraversal;

public class EclipseCloseMilestone implements IBasicMilestone, IHasRemainder {

	private final GraphTraversal<Vertex, Vertex> graphTraversal;
	private final Date currentTime;
	private final String project;
	private final MetricRemainHandler remainHandler;

	public EclipseCloseMilestone(GraphTraversal<Vertex, Vertex> graphTraversal, MetricRemainHandler remainHandler, String projectId, Date currentTime) {
		this.graphTraversal = graphTraversal;
		this.currentTime = currentTime;
		this.project = projectId;
		this.remainHandler = remainHandler;
	}

	@Override
	public List<TimeintervalGraphtraversal<Vertex, Vertex>> getSubWindows() {

		RemainMetricSlice remainMetricSlice = remainHandler.getRemainMetricSlice(project, getDescription());

		List<Vertex> milestoneOccurences = getUploadEvent(graphTraversal);
		milestoneOccurences
				.addAll((Collection<? extends Vertex>) graphTraversal.asAdmin().clone().hasLabel("event").has("VertexType", VertexType.ECLIPSE_CLOSE_EVENT).order().by("TimeStamp", Order.incr).toList());

		List<TimeintervalGraphtraversal<Vertex, Vertex>> verticesList = getVerticesList(remainMetricSlice, milestoneOccurences, graphTraversal);

		Vertex lastOccurence = milestoneOccurences.get(milestoneOccurences.size() - 1);
		verticesList.add(getRemainders(lastOccurence, currentTime, graphTraversal, remainMetricSlice));

		return verticesList;
	}

	@Override
	public String getDescription() {
		return "EventsBetweenEclipseCloses";
	}

	@Override
	public boolean isProjectSpecific() {
		return false;
	}

	@Override
	public String toString() {
		return "Eclipse Close Milestone";
	}

}
