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
package org.eclipse.scava.plugin.usermonitoring.event.events.resource;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;

@Deprecated
public class ResourceEvent extends Event {

	private final IResourceChangeEvent changeEvent;

	public ResourceEvent(IResourceChangeEvent changeEvent) {
		super();
		this.changeEvent = changeEvent;
	}

	@Override
	public String toString() {

		StringBuilder builder = new StringBuilder();

		try {
			changeEvent.getDelta().accept(new IResourceDeltaVisitor() {

				@Override
				public boolean visit(IResourceDelta delta) {

					switch (delta.getKind()) {
					case IResourceDelta.ADDED:
						builder.append("ADDED: " + delta.getResource() + "\n");
						break;
					case IResourceDelta.REMOVED:
						builder.append("REMOVED: " + delta.getResource() + "\n");
						break;
					case IResourceDelta.CHANGED:

						break;
					}
					builder.append(" + " + delta.getFullPath() + " + ");

					return true;
				}
			});
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return "Date: " + date + " VertexType: " + VertexType.RESOURCE_EVENT + " --- " + builder;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) {
		// TODO Auto-generated method stub
		return null;
	}

}
