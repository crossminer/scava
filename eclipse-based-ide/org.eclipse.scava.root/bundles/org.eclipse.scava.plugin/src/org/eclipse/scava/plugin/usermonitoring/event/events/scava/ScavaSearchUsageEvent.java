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
package org.eclipse.scava.plugin.usermonitoring.event.events.scava;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.event.IProjectRelated;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

public class ScavaSearchUsageEvent extends Event implements IProjectRelated{

	public ScavaSearchUsageEvent() {

	}

	@Override
	public String toString() {
		return "Date: " + date + " VertexType: " + VertexType.SCAVA_SEARCH_USAGE_EVENT;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) {
		/*
		 * Vertex vertex = graphTraversalSource.addV("event").next();
		 * vertex.property("VertexType", VertexType.SCAVA_SEARCH_USAGE_EVENT);
		 * vertex.property("TimeStamp", date);
		 */
		return null;
	}



}
