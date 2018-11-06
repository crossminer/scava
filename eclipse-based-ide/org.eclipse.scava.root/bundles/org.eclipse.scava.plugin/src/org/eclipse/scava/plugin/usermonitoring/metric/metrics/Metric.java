package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.UserMonitor;
import org.eclipse.scava.plugin.usermonitoring.metric.aggregator.IAggregation;
import org.eclipse.scava.plugin.usermonitoring.metric.basicmetrics.IBasicMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.term.ITerm;

public class Metric {

	private final IAggregation aggregation;
	private final ITerm term;
	private final IBasicMetric basicMetric;
	private final String ID;

	public Metric(IAggregation aggregation, ITerm term, IBasicMetric basicMetric, String ID) {
		super();
		this.aggregation = aggregation;
		this.term = term;
		this.basicMetric = basicMetric;
		this.ID = ID;
	}

	public String getID() {
		return ID;
	}
	
	@Override
	public String toString() {
		try {
			return "ID: "+getID() + "Value: "+getCurrentValue();
		} catch (MetricException e) {
			return "ID: "+getID() + "Value: Non expected behavior" ;
			
		} 
	}

	public double getCurrentValue() throws MetricException {
		
		List<GraphTraversal<Vertex, Vertex>> verticesList = UserMonitor.getGremlinAdapter().getVerticesList(term, new Date());
		List<Double> basicValues = verticesList.stream().map(list -> basicMetric.process(list)).collect(Collectors.toList());
		return aggregation.process(basicValues);
		
	}
	
	public String getDescription() {
		return basicMetric.getDiscription();
	}

}
