package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;

public class ScavaSearchSuccesBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Level of successfull free text serach using CROSSMINER plug-in";

	@Override
	public double process(GraphTraversal<Vertex, Vertex> vertices) {
		Long count = vertices.has("VertexType", VertexType.SCAVA_SEARCH_SUCCES_EVENT).count().next();
		return count;
	}

	@Override
	public String getDiscription() {
		return DESCRIPTION;
	}

}
