package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;

public class TestingRateBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Rate of launch in debug mode";

	@Override
	public double process(GraphTraversal<Vertex, Vertex> vertices) {
		Long count = vertices.has("VertexType", VertexType.LAUNCH_EVENT).has("Type", "debug").count().next();
		return count;
	}

	@Override
	public String getDiscription() {
		return DESCRIPTION;
	}

}
