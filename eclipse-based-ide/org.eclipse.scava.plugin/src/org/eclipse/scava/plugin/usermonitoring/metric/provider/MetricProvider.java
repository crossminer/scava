package org.eclipse.scava.plugin.usermonitoring.metric.provider;

import java.util.Date;
import java.util.List;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.metric.MetricManager;

public class MetricProvider {

	private static GraphTraversalSource graphTraversalSource;

	public MetricProvider(MetricManager metricManager, GraphTraversalSource graphTraversalSource) {
		MetricProvider.graphTraversalSource = graphTraversalSource;
		System.out.println(graphTraversalSource);
	}

	public static List<Vertex> getVertexListByType(String vertexType) {

		List<Vertex> vertexList = graphTraversalSource.V().has("VertexType", vertexType).order().by("TimeStamp",Order.incr).toList();
		return vertexList;
	}
	
	public static List<Vertex> getVertexListByType(String vertexType,String typeProperty) {

		List<Vertex> vertexList = graphTraversalSource.V().has("VertexType", vertexType).has("Type", typeProperty).order().by("TimeStamp",Order.incr).toList();
		return vertexList;
	}

	public static List<Vertex> getVertexTypeListTimeBased(String type, Date begin, Date end) {

		List<Vertex> list = graphTraversalSource.V().has("VertexType", type).where(__.has("TimeStamp", P.gte(begin)))
				.where(__.has("TimeStamp", P.lte(end))).order().by("TimeStamp",Order.incr).toList();

		return list;
	}
	
	public static List<Vertex> getVertexTypeListTimeBased(String vertexType,String typeProperty, Date begin, Date end) {

		List<Vertex> list = graphTraversalSource.V().has("VertexType", vertexType).has("Type", typeProperty).where(__.has("TimeStamp", P.gte(begin)))
				.where(__.has("TimeStamp", P.lte(end))).order().by("TimeStamp",Order.incr).toList();

		return list;
	}

	public static long getVertexTypeCount(String type) {

		Long count = graphTraversalSource.V().has("VertexType", type).count().next();
		return count;
	}
	
	public static long getVertexTypeCount(String vertexType,String typeProperty) {

		Long count = graphTraversalSource.V().has("VertexType", vertexType).has("Type", typeProperty).count().next();
		return count;
	}

	public static long getVertexTypeCount(String vertexType,String typeProperty, Date begin, Date end) {
		Long count = graphTraversalSource.V().has("VertexType", vertexType).has("Type", typeProperty).where(__.has("TimeStamp", P.gte(begin)))
				.where(__.has("TimeStamp", P.lte(end))).count().next();
		return count;
	}

	public static List<Vertex> getVertexListByTheirInEdges(String vertexLabel, String edgeLabel) {

		List<Vertex> list = graphTraversalSource.V().hasLabel(vertexLabel).order()
				.by(__.inE(edgeLabel).count(), Order.decr).toList();


		return list;
	}

}
