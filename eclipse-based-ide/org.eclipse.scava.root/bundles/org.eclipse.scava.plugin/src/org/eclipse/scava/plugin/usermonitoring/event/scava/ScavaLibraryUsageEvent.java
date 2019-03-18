package org.eclipse.scava.plugin.usermonitoring.event.scava;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.Event;
import org.eclipse.scava.plugin.usermonitoring.event.IEvent;

public class ScavaLibraryUsageEvent extends Event implements IEvent {

	public ScavaLibraryUsageEvent() {

	}

	@Override
	public String toString() {
		return "Date: " + date + " VertexType: " + VertexType.SCAVA_LIBRARY_USAGE_EVENT;
	}

	@Override
	public Vertex toNode(GraphTraversalSource graphTraversalSource) {

		Vertex vertex = graphTraversalSource.addV("event").next();
		vertex.property("VertexType", VertexType.SCAVA_LIBRARY_USAGE_EVENT);
		vertex.property("TimeStamp", date);

		return vertex;
	}

}
