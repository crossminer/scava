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
*    Zsolt J�nos Szamosv�lgyi
*    Endre Tam�s V�radi
*    Gerg� Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event.eclipse;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;

public class EclipseCloseEvent extends Event{

	

	public EclipseCloseEvent() {
		
	}

	@Override
	public String toString() {
		return "Date: " +date + " VertexType: " + VertexType.ECLIPSE_CLOSE_EVENT;
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {
		
		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType",VertexType.ECLIPSE_CLOSE_EVENT);
		vertex.property("TimeStamp", date);
	
		return vertex;
	}

	
	
}
