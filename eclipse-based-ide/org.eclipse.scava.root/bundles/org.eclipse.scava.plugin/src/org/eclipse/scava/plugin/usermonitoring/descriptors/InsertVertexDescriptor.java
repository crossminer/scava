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
package org.eclipse.scava.plugin.usermonitoring.descriptors;

import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;

public class InsertVertexDescriptor {

	private final String label;
	private final VertexProperty[] properties;
	private final EdgeDescriptor[] edges;

	public InsertVertexDescriptor(String label, VertexProperty... properties) {
		this(label, properties, new EdgeDescriptor[0]);
	}

	public InsertVertexDescriptor(String label, VertexProperty[] properties, EdgeDescriptor... edges) {
		super();
		this.label = label;
		this.properties = properties;
		this.edges = edges;
	}

	public String getLabel() {
		return label;
	}

	public VertexProperty[] getProperties() {
		return properties;
	}

	public EdgeDescriptor[] getEdges() {
		return edges;
	}

}
