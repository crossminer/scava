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
import java.util.Optional;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.event.events.search.SearchEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.swt.widgets.Display;

@BasedOn(SearchEvent.class)
public class EclipseSearchUsageBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Level of using Eclipse search function";
	private static final String NAME = "Eclipse search usage";

	@Override
	public Map<String, Double> process(GraphTraversal<Vertex, Vertex> vertices) {

		Map<String, Double> valueMap = new HashMap<>();

		Optional<Vertex> searchResource = vertices.asAdmin().clone().has("VertexType", VertexType.SEARCH_EVENT).out(EdgeType.SUBJECT_RESOURCE).tryNext();
		if(searchResource.isPresent()) {
			try {
				String searchString = searchResource.get().property("SearchString").value().toString();
				valueMap.merge(searchString, 1.0, Double::sum);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
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
