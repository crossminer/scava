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

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.GremlinUtils;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.IBasicMilestone;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.milestones.TimeintervalGraphtraversal;

public class HourMilestone implements IBasicMilestone {

	private final GremlinUtils gremlinUtils;
	private List<TimeintervalGraphtraversal<Vertex, Vertex>> subwindows = null;
	private final Date currentTime;

	public HourMilestone(GremlinUtils gremlinUtils, Date currentTime) {
		this.gremlinUtils = gremlinUtils;
		this.currentTime = currentTime;
	}

	@Override
	public List<TimeintervalGraphtraversal<Vertex, Vertex>> getSubWindows() {

		if (subwindows != null) {
			return subwindows;
		}

		List<TimeintervalGraphtraversal<Vertex, Vertex>> verticesList = new ArrayList<>();
		Vertex firstEvent = gremlinUtils.getFirstEvent();
		if (firstEvent == null) {
			return null;
		}

		VertexProperty<Long> timeStampProperty = firstEvent.property("TimeStamp");

		Date firstEventTime = new Date(timeStampProperty.value().longValue());
		long difference = -1;

		difference = currentTime.getTime() - DateUtils.truncate(firstEventTime, Calendar.HOUR).getTime();
		difference = (((difference) / 1000) / 3600);

		for (int i = 0; i < difference; i++) {

			Date timeSliceBegin = DateUtils.addHours(DateUtils.truncate(firstEventTime, Calendar.HOUR), (int) (i * 1));
			Date timeSliceEnd = DateUtils.addHours(DateUtils.truncate(firstEventTime, Calendar.HOUR), (int) ((i * 1) + 1));
			GraphTraversal<Vertex, Vertex> vertexList = gremlinUtils.getEventsBetween(timeSliceBegin, timeSliceEnd);

			if (vertexList.asAdmin().clone().count().next().longValue() > 0) {
				TimeintervalGraphtraversal<Vertex, Vertex> timestampedGraphTraversal = new TimeintervalGraphtraversal<>(vertexList, timeSliceBegin.toInstant().atOffset(ZoneOffset.UTC),
						timeSliceEnd.toInstant().atOffset(ZoneOffset.UTC));
				verticesList.add(timestampedGraphTraversal);
			}

		}
		subwindows = verticesList;
		return verticesList;
	}

	@Override
	public String getDescription() {
		return "EventsBetweenHours";
	}

	@Override
	public boolean isProjectSpecific() {
		return false;
	}

}