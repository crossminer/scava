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
import java.util.Map;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.document.DocumentEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.part.PartEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.part.PartEventType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

@BasedOn(PartEvent.class)
public class FileAccesRateBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Average rate of file access";
	private static final String NAME = "File acces rate";
	
	@Override
	public Map<String, Double> process(GraphTraversal<Vertex, Vertex> vertices) {

		Map<String, Double> valueMap = new HashMap<>();

		Long count = vertices.asAdmin().clone().has("VertexType", VertexType.PART_EVENT)
				.has("Type", P.within(PartEventType.OPENED.toString(), PartEventType.BROUGHT_TO_TOP.toString())).count().next();
		valueMap.put("general", (double) count);

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
