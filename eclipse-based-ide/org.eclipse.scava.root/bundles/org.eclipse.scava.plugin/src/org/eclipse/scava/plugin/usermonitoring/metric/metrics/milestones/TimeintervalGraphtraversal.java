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
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class TimeintervalGraphtraversal<S,E> {
	private final GraphTraversal<S,E> graphTraversal;
	private final OffsetDateTime beginDate;
	private final OffsetDateTime endDate;
	private boolean remainder = false;

	public TimeintervalGraphtraversal(GraphTraversal<S,E> graphTraversal, OffsetDateTime beginDate, OffsetDateTime endDate) {
		this.graphTraversal = graphTraversal;
		this.beginDate = beginDate;
		this.endDate = endDate;
	}
	
	public TimeintervalGraphtraversal(GraphTraversal<S,E> graphTraversal, OffsetDateTime beginDate, OffsetDateTime endDate, boolean remainder) {
		
		this.graphTraversal = graphTraversal;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.remainder = remainder;
	}
	
	public boolean isRemainder() {
		return remainder;
	}

	public GraphTraversal<S,E> getGraphtraversal() {
		return graphTraversal;
	}

	public OffsetDateTime getBeginDate() {
		return beginDate;
	}

	public OffsetDateTime getEndDate() {
		return endDate;
	}
	
	@Override
	public String toString() {
		System.out.println(beginDate);
		List<Vertex> list = (List<Vertex>) graphTraversal.asAdmin().clone().toList();
		System.out.println(list.size());
		
		for (Vertex vertex : list) {
			System.out.println(vertex.property("VertexType"));
		}
		System.out.println(endDate);
		return "";
	}
}
