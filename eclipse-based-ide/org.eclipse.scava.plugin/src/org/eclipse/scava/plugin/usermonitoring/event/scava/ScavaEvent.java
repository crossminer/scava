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
*    Gergő Balogh
**********************************************************************/
package org.eclipse.scava.plugin.usermonitoring.event.scava;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.commons.library.Library;
import org.eclipse.scava.plugin.usermonitoring.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OElement;
import com.orientechnologies.orient.core.record.OVertex;
import com.orientechnologies.orient.core.sql.executor.OResult;
import com.orientechnologies.orient.core.sql.executor.OResultSet;

public class ScavaEvent extends Event {

	private ScavaEventType type;
	private Library referencedLibrary;

	public ScavaEvent(Library referencedLibrary, ScavaEventType type) {
		this.referencedLibrary = referencedLibrary;
		this.type = type;
	}

	public ScavaEvent(ScavaEventType type) {
		this.type = type;
	}

	private ScavaEventType getType() {
		return type;
	}


	@Override
	public String toString() {
		return "Date: " +date + " VertexType: " + VertexType.SCAVA_EVENT + " EventType: " + getType();
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {

		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType", VertexType.SCAVA_EVENT);
		vertex.property("Type", getType());
		vertex.property("TimeStamp", date);

		if (getType().equals(ScavaEventType.LIBRARY_ADDED) ||getType().equals(ScavaEventType.LIBRARY_REMOVED)) {
			return vertex;
		}

		Vertex resource;
		
		if (findResource(graphTraversalSource, referencedLibrary.getId()) != null) {
			resource = findResource(graphTraversalSource, referencedLibrary.getId());
		} else {
			
			resource = graphTraversalSource.addV("resource").next();
			resource.property("VertexType",VertexType.LIBRARY);
			resource.property("Title", referencedLibrary.getId());
			
		}

		vertex.addEdge(EdgeType.SUBJECT_RESOURCE, resource);

		return vertex;
	}

	private Vertex findResource(GraphTraversalSource graphTraversalSource, String title) {

		List<Vertex> list = graphTraversalSource.V().hasLabel("resource").has("VertexType", VertexType.LIBRARY)
				.has("Title", title).toList();
		Vertex subject = null;

		if (!list.isEmpty()) {
			subject = list.get(0);
		}
		return subject;
	}

}
