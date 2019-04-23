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
package org.eclipse.scava.plugin.usermonitoring.event.part;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;
import org.eclipse.ui.IWorkbenchPartReference;

public class PartEvent extends Event {

	private IWorkbenchPartReference partRef;
	private PartEventType type;
	private String title;

	public PartEvent(IWorkbenchPartReference partRef, PartEventType type) {
		this.partRef = partRef;
		this.type = type;
		title = partRef.getPartName();

	}

	public final String getTitle() {
		return title;
	}

	public final PartEventType getType() {
		return type;
	}

	@Override
	public String toString() {
		String qualifiedName = "";

		switch (type) {
		case ACTIVATED:
			qualifiedName = "org.eclipse.ui.IPartListener2.partActivated(IWorkbenchPartReference)";
			break;
		case BROUGHT_TO_TOP:
			qualifiedName = "org.eclipse.ui.IPartListener2.partBroughtToTop(IWorkbenchPartReference)";
			break;
		case CLOSED:
			qualifiedName = "org.eclipse.ui.IPartListener2.partClosed(IWorkbenchPartReference)";
			break;
		case DEACTIVATED:
			qualifiedName = "org.eclipse.ui.IPartListener2.partDeactivated(IWorkbenchPartReference)";
			break;
		case HIDDEN:
			qualifiedName = "org.eclipse.ui.IPartListener2.partHidden(IWorkbenchPartReference)";
			break;
		case INPUT_CHANGED:
			qualifiedName = "org.eclipse.ui.IPartListener2.partInputChanged(IWorkbenchPartReference)";
			break;
		case OPENED:
			qualifiedName = "org.eclipse.ui.IPartListener2.partOpened(IWorkbenchPartReference)";
			break;
		case VISIBLE:
			qualifiedName = "org.eclipse.ui.IPartListener2.partVisible(IWorkbenchPartReference)";
			break;

		default:
			break;
		}

		return "Date: " + date + " VertexType: " + VertexType.PART_EVENT + " EventType: " + getType() + " ReferencedResource: " + getTitle();
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {

		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType", VertexType.PART_EVENT);
		vertex.property("Type", getType());
		vertex.property("TimeStamp", date);

		Vertex resource;
		if (findResource(graphTraversalSource, getTitle()) != null) {
			resource = findResource(graphTraversalSource, getTitle());
		} else {
			resource = graphTraversalSource.addV("resource").next();
			resource.property("VertexType", VertexType.PART);
			resource.property("Title", getTitle());
		}

		vertex.addEdge(EdgeType.SUBJECT_RESOURCE, resource);

		return vertex;
	}

	private Vertex findResource(GraphTraversalSource graphTraversalSource, String title) {

		List<Vertex> list = graphTraversalSource.V().hasLabel("resource").has("VertexType", VertexType.PART).has("Title", title).toList();
		Vertex subject = null;

		if (!list.isEmpty()) {
			subject = list.get(0);
		}
		return subject;
	}

}
