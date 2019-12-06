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
package org.eclipse.scava.plugin.usermonitoring.event.events.search;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;

public class SearchEvent extends Event {

	private String searchString;

	public SearchEvent(String searchString) {
		this.searchString = searchString;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {
		Vertex projectVertex = null;

		projectVertex = allocator.findSearchStringVertex(searchString);

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.SEARCH_EVENT), new VertexProperty("TimeStamp", date));

		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.SUBJECT_RESOURCE, projectVertex));
		return eventVertex;
	}

	public String getSearchString() {
		return searchString;
	}

	@Override
	public String toString() {
		return "Date: " + date + " VertexType: " + VertexType.LAUNCH_EVENT + " SearchString: " + searchString;
	}

}
