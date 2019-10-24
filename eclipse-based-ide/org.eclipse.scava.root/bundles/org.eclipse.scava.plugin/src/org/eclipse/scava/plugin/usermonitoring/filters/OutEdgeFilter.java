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
package org.eclipse.scava.plugin.usermonitoring.filters;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public class OutEdgeFilter implements IVertexFilter {

	private String label;

	public OutEdgeFilter(String label) {
		super();
		this.label = label;
	}

	@Override
	public GraphTraversal<Vertex, Vertex> doFilter(GraphTraversal<Vertex, Vertex> graphTraversal) {
		return graphTraversal.out(label);
	}

}
