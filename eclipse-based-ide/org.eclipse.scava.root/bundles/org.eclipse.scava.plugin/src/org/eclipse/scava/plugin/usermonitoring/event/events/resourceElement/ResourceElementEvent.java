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
package org.eclipse.scava.plugin.usermonitoring.event.events.resourceElement;

import java.io.IOException;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.event.IProjectRelated;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.swt.widgets.Display;

public class ResourceElementEvent extends Event implements IProjectRelated {

	private final ResourceElementStateType type;
	private final ICompilationUnit compilationUnit;

	public ResourceElementEvent(ICompilationUnit compilationUnit, ResourceElementStateType type) {
		this.compilationUnit = compilationUnit;
		this.type = type;
	}

	protected final ResourceElementStateType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "Date: " + date + " VertexType: " + VertexType.ELEMENT_EVENT + " EventType: " + getType() + " ReferencedResource: "
				+ compilationUnit.getElementName();
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {

		isProjectUnderObservation(compilationUnit);
		
		Vertex projectVertex = null;
		Vertex resourceVertex = null;
		try {
			resourceVertex = allocator.findFileVertex(compilationUnit);
			projectVertex = allocator.findProjectVertex(compilationUnit);
		} catch (IOException | CoreException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);
		}

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.ELEMENT_EVENT), new VertexProperty("Type", getType()),
				new VertexProperty("TimeStamp", date));

		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.SUBJECT_RESOURCE, resourceVertex));
		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.RELATED_PROJECT, projectVertex));

		return eventVertex;
	}

}
