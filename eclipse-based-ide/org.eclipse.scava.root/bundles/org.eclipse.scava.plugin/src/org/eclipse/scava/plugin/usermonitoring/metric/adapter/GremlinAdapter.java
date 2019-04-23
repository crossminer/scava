package org.eclipse.scava.plugin.usermonitoring.metric.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.apache.tinkerpop.gremlin.process.traversal.Order;
import org.apache.tinkerpop.gremlin.process.traversal.P;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.scava.plugin.usermonitoring.database.DatabaseManager;
import org.eclipse.scava.plugin.usermonitoring.database.EdgeType;
import org.eclipse.scava.plugin.usermonitoring.event.IEvent;
import org.eclipse.scava.plugin.usermonitoring.metric.metrics.MetricException;
import org.eclipse.scava.plugin.usermonitoring.metric.term.Duration;
import org.eclipse.scava.plugin.usermonitoring.metric.term.ITerm;
import org.eclipse.scava.plugin.usermonitoring.metric.term.Milestone;

public class GremlinAdapter {

	private final GraphTraversalSource graphTraversalSource;
	private final DatabaseManager databaseManager;
	private Vertex lastVertex;
	private static final String ORIENTDB_PATH = "plocal:" + ResourcesPlugin.getWorkspace().getRoot().getLocation().toOSString() + File.separator + "OrientDataBase";
	
	public GremlinAdapter() {

		
		this.databaseManager = new DatabaseManager(ORIENTDB_PATH);
		this.graphTraversalSource = databaseManager.getGraphTraversalSource();
		
		lastVertex = getLastVertex();

	}

	public void insertVertex(IEvent event) {

		System.out.println("Insert event to database: " + event);

		Vertex vertex = event.toNode(graphTraversalSource);
		if (lastVertex != null) {
			lastVertex.addEdge(EdgeType.NEXT, vertex);
		}
		lastVertex = vertex;

	}
	
	public void dropDatabase() throws Exception {
		databaseManager.erase();
	}
	
	public void closeDatabaseConnection() throws Exception {
		databaseManager.close();
		graphTraversalSource.close();
	}

	public long getVertexCount() {

		return graphTraversalSource.V().count().next();
	}

	public long getEdgeCount() {

		return graphTraversalSource.E().count().next();
	}

	private Vertex getLastVertex() {
		List<Vertex> list = graphTraversalSource.V().hasLabel("event").order().by("TimeStamp", Order.decr).limit(1).toList();

		return list.isEmpty() ? null : list.get(0);

	}

	public List<GraphTraversal<Vertex, Vertex>> getVerticesList(ITerm term, Date currentTime) throws MetricException {

		List<GraphTraversal<Vertex, Vertex>> verticesList = new ArrayList<>();

		if (term instanceof Duration) {
			verticesList = getFramedValuesByDuration((Duration) term, currentTime);
		} else if (term instanceof Milestone) {
			try {
				verticesList = getFramedValuesByMilestones((Milestone) term);
			} catch (Exception e) {
				throw new MetricException();
			}
		}

		return verticesList;

	}

	private List<GraphTraversal<Vertex, Vertex>> getFramedValuesByDuration(Duration duration, Date currentTime) throws MetricException {
		List<GraphTraversal<Vertex, Vertex>> verticesList = new ArrayList<>();

		for (int i = 0; i < duration.getCount(); i++) {

			Date timeSliceBegin = DateUtils.addHours(currentTime, (-i * duration.getDuration()));
			Date timeSliceEnd = DateUtils.addHours(currentTime, (-i * duration.getDuration()) - duration.getDuration());

			GraphTraversal<Vertex, Vertex> vertexList = graphTraversalSource.V().where(__.has("TimeStamp", P.lte(timeSliceBegin))).where(__.has("TimeStamp", P.gte(timeSliceEnd))).order()
					.by("TimeStamp", Order.incr);

			verticesList.add(vertexList);

		}

		return verticesList;
	}

	private List<GraphTraversal<Vertex, Vertex>> getFramedValuesByMilestones(Milestone milestone) throws Exception {

		List<GraphTraversal<Vertex, Vertex>> verticesList = new ArrayList<>();

		if (milestone.getProperties().isEmpty()) {
			List<Object> list = graphTraversalSource.V().has("VertexType", milestone.getMilestone()).order().by("TimeStamp", Order.decr).values("TimeStamp").toList();

			for (int i = 0; i <= milestone.getCount() - 1; i++) {
				GraphTraversal<Vertex, Vertex> vertexList = graphTraversalSource.V().where(__.has("TimeStamp", P.gte(list.get(i + 1)))).where(__.has("TimeStamp", P.lte(list.get(i)))).order()
						.by("TimeStamp", Order.incr);
				verticesList.add(vertexList);
			}
		} else {
			List<Object> list = graphTraversalSource.V().has("VertexType", milestone.getMilestone()).has("Type", milestone.getProperties().get("Type")).order().by("TimeStamp", Order.decr)
					.values("TimeStamp").toList();

			for (int i = 0; i <= milestone.getCount() - 1; i++) {
				GraphTraversal<Vertex, Vertex> vertexList = graphTraversalSource.V().where(__.has("TimeStamp", P.gte(list.get(i + 1)))).where(__.has("TimeStamp", P.lte(list.get(i)))).order()
						.by("TimeStamp", Order.incr);
				verticesList.add(vertexList);
			}
		}

		return verticesList;

	}

}
