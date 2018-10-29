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
package org.crossmeter.plugin.usermonitoring.event.window;

import org.crossmeter.plugin.usermonitoring.database.DatabaseProperties;
import org.crossmeter.plugin.usermonitoring.event.Event;
import org.eclipse.ui.IWorkbenchWindow;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

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
	
	
	public Node toNode(GraphDatabaseService service) {
		Node node;

		node = service.createNode(DatabaseProperties.NodeTypes.window_event,DatabaseProperties.NodeTypes.event);
		node.setProperty("Timestamp", getTimestamp().toString());
		node.setProperty("Type", getType().toString());

		Node fileNode;

		if (service.findNode(DatabaseProperties.NodeTypes.window, "Title", getTitle()) != null) {
			fileNode = service.findNode(DatabaseProperties.NodeTypes.window, "Title", getTitle());
		} else {
			fileNode = service.createNode(DatabaseProperties.NodeTypes.window);
			fileNode.setProperty("Title", getTitle());
		}

		node.createRelationshipTo(fileNode, DatabaseProperties.RelationTypes.subject);
		
		return node;
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

		return timestamp + " [" + qualifiedName + "] TYPE: " + type;
	}

}
