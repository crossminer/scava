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

import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.RemainMetricSlice;

public interface IBasicMilestone {

	public List<TimeintervalGraphtraversal<Vertex, Vertex>> getSubWindows();

	public String getDescription();

	public boolean isProjectSpecific();

	public default List<Vertex> getUploadEvent(GraphTraversal<Vertex, Vertex> graphTraversal) {
		List<Vertex> list = graphTraversal.asAdmin().clone().has("VertexType", VertexType.UPLOAD_EVENT).order().by("TimeStamp", Order.incr).toList();
		
		list.forEach(v->{
			System.out.println(v);
		});
		
		return list;
	}

	public default List<TimeintervalGraphtraversal<Vertex, Vertex>> getVerticesList(RemainMetricSlice remainMetricSlice, List<Vertex> milestoneOccurences,
			GraphTraversal<Vertex, Vertex> graphTraversal) {
		List<TimeintervalGraphtraversal<Vertex, Vertex>> verticesList = new ArrayList<>();
		for (int slice = 0; slice < milestoneOccurences.size() - 1; slice++) {

			Object beginDate = milestoneOccurences.get(slice).property("TimeStamp").value();
			Object endDate = milestoneOccurences.get(slice + 1).property("TimeStamp").value();
			String vertexType = milestoneOccurences.get(slice).property("VertexType").value().toString();

			GraphTraversal<Vertex, Vertex> vertexList = graphTraversal.asAdmin().clone().where(__.has("TimeStamp", P.lt(((Long) endDate).longValue())))
					.where(__.has("TimeStamp", P.gt(((Long) beginDate).longValue()))).order().by("TimeStamp", Order.incr);

			if (vertexType.equals(VertexType.UPLOAD_EVENT) && remainMetricSlice.hasRemainMilestone()) {
				verticesList.add(new TimeintervalGraphTraversalWithRemains<Vertex, Vertex>(vertexList, milestoneOccurences.get(slice),
						new Date(((Long) endDate).longValue()).toInstant().atOffset(ZoneOffset.UTC), remainMetricSlice));
			} else if (!vertexType.equals(VertexType.UPLOAD_EVENT)) {
				verticesList.add(new TimeintervalGraphtraversal<Vertex, Vertex>(vertexList, new Date(((Long) beginDate).longValue()).toInstant().atOffset(ZoneOffset.UTC),
						new Date(((Long) endDate).longValue()).toInstant().atOffset(ZoneOffset.UTC)));
			}
		}
		return verticesList;
	}

}
