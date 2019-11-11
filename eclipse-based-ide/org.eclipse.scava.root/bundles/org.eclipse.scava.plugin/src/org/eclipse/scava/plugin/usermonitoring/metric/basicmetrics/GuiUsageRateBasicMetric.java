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
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal.Admin;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.part.PartEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.window.WindowEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

@BasedOn({ PartEvent.class, WindowEvent.class })
public class GuiUsageRateBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Rate of activly using the GUI of Eclipse";
	private static final String NAME = "Gui usage rate";

	@Override
	public Map<String, Double> process(GraphTraversal<Vertex, Vertex> vertices) {

		Map<String, Double> valueMap = new HashMap<>();

		Long count = vertices.asAdmin().clone().has("VertexType", P.within(VertexType.PART_EVENT, VertexType.WINDOW_EVENT)).count().next();
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
