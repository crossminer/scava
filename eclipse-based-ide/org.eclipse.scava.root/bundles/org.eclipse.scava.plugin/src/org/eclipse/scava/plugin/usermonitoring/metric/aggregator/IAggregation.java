package org.eclipse.scava.plugin.usermonitoring.metric.aggregator;

import java.util.List;

@FunctionalInterface
public interface IAggregation {

	double process(List<Double> values);

}
