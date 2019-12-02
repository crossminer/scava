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
import java.util.Date;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.metric.RemainMetricSlice;

public interface IHasRemainder {

	public default TimeintervalGraphtraversal<Vertex, Vertex> getRemainders(Vertex lastOccurence, Date currentTime, GraphTraversal<Vertex, Vertex> graphTraversal, RemainMetricSlice remainMetricSlice) {

		String vertexType = lastOccurence.property("VertexType").value().toString();
		Date lastOccurenceDate = new Date((Long) lastOccurence.property("TimeStamp").value());
		TimeintervalGraphtraversal<Vertex, Vertex> remainder;

		GraphTraversal<Vertex, Vertex> remainders = graphTraversal.asAdmin().clone().where(__.has("TimeStamp", P.lt(currentTime.getTime()))).where(__.has("TimeStamp", P.gt(lastOccurenceDate.getTime())))
				.order().by("TimeStamp", Order.incr);

		if (vertexType.equals(VertexType.UPLOAD_EVENT)) {
			remainder = new TimeintervalGraphTraversalWithRemains<Vertex, Vertex>(remainders, lastOccurence, currentTime.toInstant().atOffset(ZoneOffset.UTC), remainMetricSlice, true);
			return remainder;
		} else {
			remainder = new TimeintervalGraphtraversal<Vertex, Vertex>(remainders, lastOccurenceDate.toInstant().atOffset(ZoneOffset.UTC), currentTime.toInstant().atOffset(ZoneOffset.UTC), true);
			return remainder;
		}

	}

}
