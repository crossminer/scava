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
package org.crossmeter.plugin.usermonitoring.event.document;

import org.crossmeter.plugin.usermonitoring.database.DatabaseProperties;
import org.crossmeter.plugin.usermonitoring.event.Event;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class DocumentEvent extends Event {

	private org.eclipse.jface.text.DocumentEvent event;
	private String title;

	public DocumentEvent(org.eclipse.jface.text.DocumentEvent event, String title) {
		this.event = event;
		this.title = title;
	}

	public final String getTitle() {
		return title;
	}

	@Override
	public String toString() {
		return timestamp + " ->" + event.getDocument()
				+ " [org.eclipse.jface.text.IDocumentListener.documentAboutToBeChanged(DocumentEvent)] : OFFSET: "
				+ event.getOffset() + " LENGTH: " + event.fLength + " TEXT: " + event.getText();
	}

	public Node toNode(GraphDatabaseService service) {

		Node node = service.createNode(DatabaseProperties.NodeTypes.document_event,DatabaseProperties.NodeTypes.event);
		node.setProperty("Kind", "KeyPressed");
		node.setProperty("Timestamp", timestamp.toString());

		Node fileNode;

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
}
