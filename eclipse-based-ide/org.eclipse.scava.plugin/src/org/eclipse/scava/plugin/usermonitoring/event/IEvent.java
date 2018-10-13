package org.eclipse.scava.plugin.usermonitoring.event;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import com.orientechnologies.orient.core.db.ODatabaseSession;
import com.orientechnologies.orient.core.record.OVertex;

public interface IEvent {

	Vertex toNode(GraphTraversalSource graphTraversalSource);
}
