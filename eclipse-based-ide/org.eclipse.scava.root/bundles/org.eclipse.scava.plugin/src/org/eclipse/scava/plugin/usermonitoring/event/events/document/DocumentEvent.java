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
package org.eclipse.scava.plugin.usermonitoring.event.events.document;

import java.io.IOException;
import java.util.List;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.event.IFlushableEvent;
import org.eclipse.scava.plugin.usermonitoring.event.IProjectRelated;
import org.eclipse.scava.plugin.usermonitoring.event.events.Event;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexProperty;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.swt.widgets.Display;

public class DocumentEvent extends Event implements IFlushableEvent<DocumentEvent>, IProjectRelated {

	private String resourceTitle;
	private int count = 1;
	private ICompilationUnit unit;

	public DocumentEvent(ICompilationUnit compilationUnit) {
		this.unit = compilationUnit;

	}

	private DocumentEvent(ICompilationUnit compilationUnit, long timeStamp) {
		this.unit = compilationUnit;
		setTimestamp(timeStamp);
	}

	public final String getTitle() {
		return resourceTitle;
	}

	@Override
	public Vertex toNode(VertexAllocator allocator) throws MetricException {

		isProjectUnderObservation(unit);

		Vertex resourceVertex = null;
		Vertex projectVertex = null;
		try {
			resourceVertex = allocator.findFileVertex(unit);
			projectVertex = allocator.findProjectVertex(unit);
		} catch (IOException | CoreException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.DATABASE_ERROR);
		}

		Vertex eventVertex = allocator.insertVertex("event", new VertexProperty("VertexType", VertexType.DOCUMENT_EVENT), new VertexProperty("Type", "DocumentChanges"),
				new VertexProperty("TimeStamp", date), new VertexProperty("Count", count));

		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.SUBJECT_RESOURCE, resourceVertex));
		allocator.connectVertex(eventVertex, new EdgeDescriptor(EdgeType.RELATED_PROJECT, projectVertex));
		return eventVertex;
	}

	@Override
	public String toString() {

		return "Date: " + date + " VertexType: " + VertexType.DOCUMENT_EVENT + " EventType: Keypressed Count: " + count;

	}

	@Override
	public DocumentEvent aggregate(List<DocumentEvent> events) {

		DocumentEvent middleEvent = events.get((int) events.size() / 2);

		DocumentEvent documentEvent = new DocumentEvent(unit, middleEvent.getTimestamp());
		documentEvent.count = events.size();
		return documentEvent;
	}

	@Override
	public int getMaxBufferedLength() {
		return 10;
	}

	@Override
	public long getMaxTimeUntilEventInsertion() {
		return 5000;
	}

}
