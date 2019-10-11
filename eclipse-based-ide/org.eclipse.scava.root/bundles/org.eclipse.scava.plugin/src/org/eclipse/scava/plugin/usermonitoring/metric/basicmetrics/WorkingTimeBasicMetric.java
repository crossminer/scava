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
package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.document.DocumentEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

@BasedOn(DocumentEvent.class)
public class WorkingTimeBasicMetric implements IBasicMetric, IProjectRelatedBasicMetric, IFileRelatedBasicMetric {

	private static final String DESCRIPTION = "Working time for Java source files";
	private static final String NAME = "Working time";
	private final String projectID;
	private final String[] files;

	public WorkingTimeBasicMetric(String projectID, String... files) {
		this.projectID = projectID;
		this.files = files;
	}

	@Override
	public Map<String, Double> process(GraphTraversal<Vertex, Vertex> vertices) {

		Map<String, Double> valueMap = new HashMap<>();

		for (String file : files) {

			double subValue = 0;

			GraphTraversal<Vertex, Vertex> constantQuerry = vertices.asAdmin().clone();

			List<Object> documentVertices = vertices.asAdmin().clone().hasLabel("event").has("VertexType", VertexType.DOCUMENT_EVENT).as("event").out(EdgeType.RELATED_PROJECT).has("ProjectId", projectID)
					.select("event").out(EdgeType.SUBJECT_RESOURCE).has("Title", file).select("event").order().by("TimeStamp", Order.incr).toList();

			for (Object obj : documentVertices) {
				Vertex vertex = (Vertex) obj;

				Vertex last = constantQuerry.asAdmin().clone().V(vertex.id()).until(__.or(__.hasNot("Count"), __.not(__.outE("Next")))).repeat(__.out("Next")).next();

				Object first = vertex.property("TimeStamp").value();
				Object second = last.property("TimeStamp").value();

				long diffInMillies = ((Long) second).longValue() - ((Long) first).longValue();
				subValue += (diffInMillies / 1000);
			}

			valueMap.put(file, subValue);
		}

		return valueMap;
	}

	@Override
	public String getDiscription() {
		return DESCRIPTION;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
