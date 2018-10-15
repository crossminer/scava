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
package org.crossmeter.plugin.usermonitoring.event.part;

import org.crossmeter.plugin.usermonitoring.database.DatabaseProperties;
import org.crossmeter.plugin.usermonitoring.event.Event;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.texteditor.ITextEditor;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

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

	public Node toNode(GraphDatabaseService service) {
		Node node;
		Node fileNode;

		node = service.createNode(DatabaseProperties.NodeTypes.part_event,DatabaseProperties.NodeTypes.event);
		node.setProperty("Timestamp", getTimestamp().toString());
		node.setProperty("Type", getType().toString());
		


		
		if (service.findNode(DatabaseProperties.NodeTypes.part, "Title", getTitle()) != null) {
			fileNode = service.findNode(DatabaseProperties.NodeTypes.part, "Title", getTitle());
		} else {
			fileNode = service.createNode(DatabaseProperties.NodeTypes.part);
			fileNode.setProperty("Title", getTitle());
		}

		node.createRelationshipTo(fileNode, DatabaseProperties.RelationTypes.subject_resource);

		return node;
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
		
		return timestamp + " [" + qualifiedName + "] " + "PART NAME: " + partRef.getPartName() + " TYPE: " + type;
	}

}
