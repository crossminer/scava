package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import java.util.List;

public interface IMetricAggregator<T> {
	
	public T aggregateToValue(List<T> items);

	

} 
