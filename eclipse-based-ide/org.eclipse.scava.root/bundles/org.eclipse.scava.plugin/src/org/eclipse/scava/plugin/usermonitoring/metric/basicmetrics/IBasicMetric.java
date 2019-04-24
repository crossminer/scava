package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

public interface IBasicMetric {

	public double process(GraphTraversal<Vertex, Vertex> vertices);

	public String getDiscription();

}
