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
package org.eclipse.scava.plugin.usermonitoring.event.events.part;

import java.io.IOException;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaCore;
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
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.ITextEditor;

public class PartEvent extends Event implements IProjectRelated {

	private PartEventType type;
	private String title;
	IWorkbenchPartReference partRef;

	public PartEvent(IWorkbenchPartReference partRef, PartEventType type) {
		this.type = type;
		this.partRef = partRef;
		title = partRef.getPartName();

	}

	public final String getTitle() {
		return title;
	}

	public final PartEventType getType() {
		return type;
	}

	@Override
	public String toString() {

		return "Date: " + date + " VertexType: " + VertexType.PART_EVENT + " EventType: " + getType() + " ReferencedResource: " + getTitle();
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {
		ICompilationUnit unit = getRelatedProject(partRef);

		if (unit != null) {
			isProjectUnderObservation(unit);
		}

		Vertex resourceVertex = null;
		Vertex projectVertex = null;

		try {
			resourceVertex = allocator.findPartVertex(getTitle());
		} catch (IOException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);
		}

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.PART_EVENT), new VertexProperty("Title", getTitle()), new VertexProperty("Type", getType()),
				new VertexProperty("TimeStamp", date));

		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.SUBJECT_RESOURCE, resourceVertex));

		if (unit != null) {
			try {
				projectVertex = allocator.findProjectVertex(unit);
				allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.RELATED_PROJECT, projectVertex));
			} catch (IOException | CoreException e) {
				e.printStackTrace();
				ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);

			}
		}

		return eventVertex;
	}

	private ICompilationUnit getRelatedProject(IWorkbenchPartReference partRef) {
		ICompilationUnit compilationUnit = null;
		IWorkbenchPart part = partRef.getPart(false);
		if (part instanceof IEditorPart) {
			IEditorPart editor = (IEditorPart) part;
			IEditorInput input = editor.getEditorInput();

			if (editor instanceof ITextEditor && input instanceof FileEditorInput) {
				ITextEditor textEditor = (ITextEditor) editor;
				IEditorInput editorInput = textEditor.getEditorInput();
				IFile file = ((IFileEditorInput) editorInput).getFile();

				if (file.getFileExtension().toLowerCase().equals("java")) {

					compilationUnit = JavaCore.createCompilationUnitFrom(file);

				}

			}
		}
		return compilationUnit;
	}

}
