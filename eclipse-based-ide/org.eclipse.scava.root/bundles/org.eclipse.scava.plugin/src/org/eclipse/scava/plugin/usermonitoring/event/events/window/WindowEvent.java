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
package org.eclipse.scava.plugin.usermonitoring.event.events.window;

import java.io.IOException;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchWindow;

public class WindowEvent extends Event {

	IWorkbenchWindow window;
	WindowEventType type;
	String title;

	public WindowEvent(IWorkbenchWindow window, WindowEventType type) {
		this.window = window;
		this.type = type;
		this.title = "Workspace";
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
	public Vertex toNode(VertexAllocator allocator) {
		Vertex resourceVertex = null;
		try {
			resourceVertex = allocator.findWindowVertex(title);
		} catch (IOException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);
		}

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.WINDOW_EVENT), new VertexProperty("Type", getType()),
				new VertexProperty("TimeStamp", date));

		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.SUBJECT_RESOURCE, resourceVertex));
		return eventVertex;
	}

}
