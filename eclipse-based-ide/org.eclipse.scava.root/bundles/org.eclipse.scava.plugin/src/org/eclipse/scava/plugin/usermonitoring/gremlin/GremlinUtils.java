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
package org.eclipse.scava.plugin.usermonitoring.gremlin;

import java.util.Date;
import java.util.List;

import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.apache.tinkerpop.gremlin.structure.VertexProperty;
import org.eclipse.scava.plugin.Activator;
import org.eclipse.scava.plugin.preferences.Preferences;
import org.eclipse.scava.plugin.ui.errorhandler.ErrorHandler;
import org.eclipse.scava.plugin.usermonitoring.ErrorType;
import org.eclipse.scava.plugin.usermonitoring.descriptors.EdgeDescriptor;
import org.eclipse.scava.plugin.usermonitoring.event.events.IEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.projectstructure.ProjectStructureBuilderEvent;
import org.eclipse.scava.plugin.usermonitoring.event.events.scheduledUploadEvent.ScheduledUploadEvent;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexAllocator;
import org.eclipse.scava.plugin.usermonitoring.gremlin.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.swt.widgets.Display;

public class GremlinUtils implements IGremlinUtils {

	private GraphTraversalSource graphTraversalSource;
	private VertexAllocator vertexAllocator;
	private Vertex lastEvent;
	
	public GremlinUtils(GraphTraversalSource graphTraversalSource) {
		this.graphTraversalSource = graphTraversalSource;
		this.vertexAllocator = new VertexAllocator(graphTraversalSource);

		lastEvent = getLastEvent();
	}

	public GraphTraversalSource getGraphTraversalSource() {
		return graphTraversalSource;
	}

	@Override
	public void insertVertex(IEvent event) {
		try {
			if (event instanceof ProjectStructureBuilderEvent) {

				event.toNode(vertexAllocator);

				return;
			}

			if (event instanceof ScheduledUploadEvent) {
				Vertex firstEvent = getFirstEvent();
				System.out.println("First event: " + firstEvent);
				Vertex vertex;
				vertex = event.toNode(vertexAllocator);

				if (firstEvent != null) {
					vertexAllocator.connectVertex(firstEvent, new EdgeDescriptor(EdgeType.NEXT, vertex, true));
				}else {
					lastEvent = vertex;
				}

				return;
			}

			if (event != null) {
				Vertex vertex;
				vertex = event.toNode(vertexAllocator);
				if (lastEvent != null) {
					vertexAllocator.connectVertex(vertex, new EdgeDescriptor(EdgeType.NEXT, lastEvent, true));
				}
				lastEvent = vertex;
			}
		} catch (MetricException e) {
			e.printStackTrace();
			ErrorHandler.handle(Display.getDefault().getActiveShell(), e, ErrorType.EVENT_INSERTION_ERROR);
		}
	}

	@Override
	public Vertex getFirstEvent() {
		List<Vertex> list = graphTraversalSource.V().hasLabel("event").order().by("TimeStamp", Order.incr).limit(1).toList();

		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Vertex getLastEvent() {
		List<Vertex> list = graphTraversalSource.V().hasLabel("event").order().by("TimeStamp", Order.decr).limit(1).toList();

		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public long getTimeInMinuteFromFirstEvent() {
		Vertex firstVertex = getFirstEvent();
		long difference = -1;

		if (firstVertex != null) {
			VertexProperty<Long> timeStampProperty = firstVertex.property("TimeStamp");
			Date firstEventTime = new Date(timeStampProperty.value().longValue());

			Date currentTime = new Date();
			difference = currentTime.getTime() - firstEventTime.getTime();
			difference = (((difference) / 1000) / 60);
		}
		return difference;

	}

	@Override
	public GraphTraversal<Vertex, Vertex> getEventsBetween(Date greaterThanEqual, Date lesserThan) {

		return graphTraversalSource.V().where(__.has("TimeStamp", P.gte(greaterThanEqual.getTime()))).where(__.has("TimeStamp", P.lt(lesserThan.getTime()))).order().by("TimeStamp", Order.incr);
	}

	@Override
	public Vertex getLastUploadEvent() {

		List<Vertex> list = graphTraversalSource.V().hasLabel("event").has("VertexType", VertexType.UPLOAD_EVENT).order().by("TimeStamp", Order.decr).limit(1).toList();

		return list.isEmpty() ? null : list.get(0);

	}

	@Override
	public void eraseEventsBefore(Long timeStamp) {
		
		graphTraversalSource.V().hasLabel("event").where(__.has("TimeStamp", P.lt(timeStamp))).drop().iterate();
		System.out.println("The non-related events has been deleted from the database.");
	}

}
