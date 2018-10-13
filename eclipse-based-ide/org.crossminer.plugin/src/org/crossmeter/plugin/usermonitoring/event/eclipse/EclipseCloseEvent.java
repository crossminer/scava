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
package org.crossmeter.plugin.usermonitoring.event.eclipse;

import org.crossmeter.plugin.usermonitoring.database.DatabaseProperties;
import org.crossmeter.plugin.usermonitoring.event.Event;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;

public class EclipseCloseEvent extends Event{

	
	
	
	
	public EclipseCloseEvent() {
		
	}

	@Override
	public Node toNode(GraphDatabaseService service) {
		Node node;

		node = service.createNode(DatabaseProperties.NodeTypes.eclipse_close_event,DatabaseProperties.NodeTypes.event);
		node.setProperty("Timestamp", getTimestamp().toString());

		return node;
	}

	@Override
	public String toString() {
		return timestamp + " [EclipseCloseEvent]";
	}

	
	
}
