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

public class EdgeDescriptor {

	private final String label;
	private final Vertex connectTo;
	private final boolean reverse;

	public EdgeDescriptor(String label, Vertex connectTo) {
		this(label, connectTo, false);
	}

	public EdgeDescriptor(String label, Vertex connectTo, boolean reverse) {
		super();
		this.label = label;
		this.connectTo = connectTo;
		this.reverse = reverse;
	}

	public String getLabel() {
		return label;
	}

	public Vertex getConnectTo() {
		return connectTo;
	}

	public boolean isReverse() {
		return reverse;
	}

}
