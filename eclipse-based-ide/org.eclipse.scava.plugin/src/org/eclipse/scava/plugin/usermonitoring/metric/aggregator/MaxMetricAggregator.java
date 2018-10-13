package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import java.util.List;

public abstract class MaxMetricAggregator<T extends Comparable<? super T>> implements IMetricAggregator<T> {
	public T aggregateToValue(List<T> items) {
		T max = items.get(0);
		for (T t : items) {
			if (max.compareTo(t) == -1) {
				max = t;
			}
		}
		return max;

	}
}
