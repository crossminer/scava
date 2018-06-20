package org.eclipse.scava.plugin.usermonitoring.metric;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.eclipse.scava.plugin.usermonitoring.database.DatabaseManager;
import org.eclipse.scava.plugin.usermonitoring.metric.provider.MetricProvider;

public class MetricManager{


	private GraphTraversalSource graphTraversalSource;
	private MetricProvider metricProvider;
	
	
	public MetricManager(DatabaseManager manager, GraphTraversalSource graphTraversalSource) {
		
		this.graphTraversalSource = graphTraversalSource;
		metricProvider = new MetricProvider(this, graphTraversalSource);
		
	}
	
	public MetricProvider getMetricProvider() {
		return metricProvider;
	}

	
}
