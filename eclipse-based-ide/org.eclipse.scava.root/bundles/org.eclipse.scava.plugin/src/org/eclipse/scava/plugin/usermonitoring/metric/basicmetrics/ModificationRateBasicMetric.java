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

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.document.DocumentEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

@BasedOn(DocumentEvent.class)
public class ModificationRateBasicMetric implements IBasicMetric, IProjectRelatedBasicMetric {

	private static final String DESCRIPTION = "Rate of changes applied to document";
	private static final String NAME = "Modification rate";
	private final String projectID;

	public ModificationRateBasicMetric(String projectID) {
		this.projectID = projectID;
	}

	@Override
	public Map<String, Double> process(GraphTraversal<Vertex, Vertex> vertices) {
		Map<String, Double> valueMap = new HashMap<>();

		Long count = vertices.asAdmin().clone().hasLabel("event").has("VertexType", VertexType.DOCUMENT_EVENT).as("event").out(EdgeType.RELATED_PROJECT)
				.has("ProjectId", projectID).select("event").values("Count").sum().next().longValue();

		valueMap.put("general", (double) count);

		return valueMap;

	}

	@Override
	public String getDiscription() {
		return projectID + ": " + DESCRIPTION;
	}

	@Override
	public String getName() {
		return NAME;
	}

}
