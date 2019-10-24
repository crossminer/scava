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
package org.eclipse.scava.plugin.usermonitoring.event.events.scheduledUploadEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;

public class ScheduledUploadEvent extends Event {

	String remainderJson = "";

	public ScheduledUploadEvent() {

	}

	public void setRemainder(String remainderJson) {
		this.remainderJson = remainderJson;

	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {

		List<VertexProperty> vertexProperties = new ArrayList<>();
		vertexProperties.add(new VertexProperty("VertexType", VertexType.UPLOAD_EVENT));
		vertexProperties.add(new VertexProperty("TimeStamp", date));
		vertexProperties.add(new VertexProperty("Remains", remainderJson));

		VertexProperty[] properties = vertexProperties.toArray(new VertexProperty[0]);

		Vertex eventVertex = allocator.insertVertex("event", properties);
		return eventVertex;
	}

	public void setTimestamp(long date) {
		super.date = date;
	}

	@Override
	public String toString() {
		return "Upload";
	}

}
