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
package org.eclipse.scava.plugin.usermonitoring.event.events.projectstructure;

import java.io.IOException;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.swt.widgets.Display;

public class ProjectStructureBuilderEvent extends Event {

	private final ICompilationUnit compilationUnit;

	public ProjectStructureBuilderEvent(ICompilationUnit unit) {
		this.compilationUnit = unit;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) {
		Vertex findFileVertex = null;
		try {
			findFileVertex = allocator.findFileVertex(compilationUnit);
		} catch (IOException | CoreException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);
		}
		return findFileVertex;
	}

	@Override
	public String toString() {
		return compilationUnit.getPath().toOSString();
	}

}
