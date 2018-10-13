package org.eclipse.scava.plugin.usermonitoring.metric.metrics;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.eclipse.scava.plugin.usermonitoring.database.VertexType;
import org.eclipse.scava.plugin.usermonitoring.event.element.ResourceElementStateType;
import org.eclipse.scava.plugin.usermonitoring.metric.MilestoneBasedMetric;
import org.eclipse.scava.plugin.usermonitoring.metric.aggregator.AverageMetricAggregator;
import org.eclipse.scava.plugin.usermonitoring.metric.provider.MetricProvider;

public class averageTimeBetweenSaveEvents extends MilestoneBasedMetric<Double>{

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getValue() {
		List<Vertex> vertexListByType = MetricProvider.getVertexListByType(VertexType.ELEMENT_EVENT,ResourceElementStateType.SAVED.toString());
		List<Double> times = new ArrayList<>();
		
		for (int i = 0; i < vertexListByType.size()-1; i++) {
			 Long value = ((Date) vertexListByType.get(i).property("TimeStamp").value()).getTime();
			 Long value2 = ((Date) vertexListByType.get(i+1).property("TimeStamp").value()).getTime();
			 times.add(value2-(value*1.0));
		}
		
		Double aggregateToValue = new AverageMetricAggregator<Double>() {}.aggregateToValue(times);
				
		return aggregateToValue;
	}

}
