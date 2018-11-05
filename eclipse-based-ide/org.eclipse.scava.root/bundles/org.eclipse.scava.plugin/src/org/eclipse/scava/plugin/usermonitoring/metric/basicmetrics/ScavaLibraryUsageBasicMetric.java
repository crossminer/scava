package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;

public class ScavaLibraryUsageBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Level of using CROSSMINER library change function";

	@Override
	public double process(GraphTraversal<Vertex, Vertex> vertices) {
		Long scavaLibraryChange = vertices.has("VertexType", VertexType.SCAVA_LIBRARY_USAGE_EVENT).count().next();
		return scavaLibraryChange;

	}

	@Override
	public String getDiscription() {
		return DESCRIPTION;
	}

}
