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
package org.crossmeter.plugin.usermonitoring.database;

import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;

public class DatabaseProperties {
	public static enum RelationTypes implements RelationshipType {
		next, subject_resource, subject
	}

	public static enum NodeTypes implements Label {
		event, resource_event, document_event, part_event, window_event, file, part, window, element_event, eclipse_close_event
	}
}
