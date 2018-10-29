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
package org.eclipse.scava.plugin.usermonitoring.event.window;

import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;
import org.eclipse.ui.IWorkbenchWindow;

public class WindowEvent extends Event {

	IWorkbenchWindow window;
	WindowEventType type;
	String title;

	public WindowEvent(IWorkbenchWindow window, WindowEventType type) {
		this.window = window;
		this.type = type;
		title = window.getActivePage().getLabel();
	}

	public final WindowEventType getType() {
		return type;
	}

	public final String getTitle() {
		return title;
	}

	@Override
	public String toString() {

		String qualifiedName = "";

		switch (type) {
		case ACTIVATED:
			qualifiedName = "org.eclipse.ui.IWindowListener.windowActivated(IWorkbenchWindow)";
			break;
		case CLOSED:
			qualifiedName = "org.eclipse.ui.IWindowListener.windowClosed(IWorkbenchWindow)";
			break;
		case DEACTIVATED:
			qualifiedName = "org.eclipse.ui.IWindowListener.windowDeactivated(IWorkbenchWindow)";
			break;
		case OPENED:
			qualifiedName = "org.eclipse.ui.IWindowListener.windowOpened(IWorkbenchWindow)";
			break;
		default:
			break;
		}

		return "Date: " + date + " VertexType: " + VertexType.WINDOW_EVENT + " EventType: " + getType() + " ReferencedResource: " + getTitle();
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {
		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType", VertexType.WINDOW_EVENT);
		vertex.property("Type", getType());
		vertex.property("TimeStamp", date);

		Vertex resource;

		if (findResource(graphTraversalSource, getTitle()) != null) {
			resource = findResource(graphTraversalSource, getTitle());
		} else {
			resource = graphTraversalSource.addV("resource").next();
			resource.property("VertexType", VertexType.WINDOW);
			resource.property("Title", getTitle());
		}

		vertex.addEdge(EdgeType.SUBJECT_RESOURCE, resource);

		return vertex;
	}

	private Vertex findResource(GraphTraversalSource graphTraversalSource, String title) {

		List<Vertex> list = graphTraversalSource.V().hasLabel("resource").has("VertexType", VertexType.WINDOW).has("Title", title).toList();
		Vertex subject = null;

		if (!list.isEmpty()) {
			subject = list.get(0);
		}
		return subject;
	}

}
