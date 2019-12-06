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
package org.eclipse.scava.plugin.usermonitoring.event.events.idle;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

public class IdleEvent extends Event {

	public IdleEvent() {

	}

	@Override
	public String toString() {
		return "Date: " + date + " VertexType: " + VertexType.IDLE_EVENT;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) {

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.IDLE_EVENT), new VertexProperty("TimeStamp", date));

		return eventVertex;
	}

}
