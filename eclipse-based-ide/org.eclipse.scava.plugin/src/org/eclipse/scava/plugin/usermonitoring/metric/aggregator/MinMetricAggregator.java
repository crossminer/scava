package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import java.util.List;

public abstract class MinMetricAggregator<T extends Comparable<? super T>> implements IMetricAggregator<T> {

	@Override
	public T aggregateToValue(List<T> items) {
		
			T min = items.get(0);
			for (T t : items) {
				
				
				if(min.compareTo(t) ==1) {
					min = t;
				}
			}
			return  min;

	}

	
		
	
 
}
