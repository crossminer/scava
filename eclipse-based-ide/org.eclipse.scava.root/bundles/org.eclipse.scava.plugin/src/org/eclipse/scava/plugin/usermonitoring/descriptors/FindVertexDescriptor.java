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

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.filters.IVertexFilter;

public class FindVertexDescriptor {
	private final Vertex parent;
	private final IVertexFilter[] filters;

	public FindVertexDescriptor(IVertexFilter... filters) {
		this(null, filters);
	}

	public FindVertexDescriptor(Vertex parent, IVertexFilter... filters) {
		super();
		this.parent = parent;
		this.filters = filters;
	}

	public Vertex getParent() {
		return parent;
	}

	public IVertexFilter[] getFilters() {
		return filters;
	}

}
