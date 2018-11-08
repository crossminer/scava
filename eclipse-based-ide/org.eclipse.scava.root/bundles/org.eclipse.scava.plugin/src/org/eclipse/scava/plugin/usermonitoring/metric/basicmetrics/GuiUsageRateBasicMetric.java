package org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics;

import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;

public class GuiUsageRateBasicMetric implements IBasicMetric {

	private static final String DESCRIPTION = "Rate of activly using the GUI of Eclipse";

	@Override
	public double process(GraphTraversal<Vertex, Vertex> vertices) {

		Long count = vertices.has("VertexType", P.within(VertexType.PART_EVENT, VertexType.WINDOW_EVENT)).count().next();

		return count;

	}

	@Override
	public String getDiscription() {
		return DESCRIPTION;
	}

}
