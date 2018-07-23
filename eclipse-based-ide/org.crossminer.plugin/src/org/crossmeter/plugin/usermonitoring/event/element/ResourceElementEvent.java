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
package org.crossmeter.plugin.usermonitoring.event.element;

import org.crossmeter.plugin.usermonitoring.database.DatabaseProperties;
import org.crossmeter.plugin.usermonitoring.event.Event;
import org.eclipse.ui.IEditorInput;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.unsafe.impl.batchimport.input.Input;

public class ResourceElementEvent extends Event{

	String title;
	ResourceElementStateType type;
	
	
	public ResourceElementEvent(IEditorInput input, ResourceElementStateType type) {
		title = input.getName();
		this.type = type;
	}
	
	

	protected final ResourceElementStateType getType() {
		return type;
	}

	

	protected final String getTitle() {
		return title;
	}



	@Override
	public Node toNode(GraphDatabaseService service) {
		Node node;
		Node fileNode;

		node = service.createNode(DatabaseProperties.NodeTypes.element_event,DatabaseProperties.NodeTypes.event);
		node.setProperty("Timestamp", getTimestamp().toString());
		node.setProperty("Type", getType().toString());


		
		if (service.findNode(DatabaseProperties.NodeTypes.part, "Title", getTitle()) != null) {
			fileNode = service.findNode(DatabaseProperties.NodeTypes.part, "Title", getTitle());
			fileNode.addLabel(DatabaseProperties.NodeTypes.file);
		} else {
			fileNode = service.createNode(DatabaseProperties.NodeTypes.file,DatabaseProperties.NodeTypes.part);
			fileNode.setProperty("Title", getTitle());
		}

		node.createRelationshipTo(fileNode, DatabaseProperties.RelationTypes.subject_resource);

		return node;
	}

	@Override
	public String toString() {
		return "ResourceElementEvent [title=" + title + ", type=" + type + "]";
	}

	
	
}
